package com.example.tripdrop

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.tripdrop.data.model.Event
import com.example.tripdrop.data.model.Product
import com.example.tripdrop.data.model.UserData
import com.example.tripdrop.ui.navigation.Route
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DropViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ViewModel() {

    var isDialogShow by mutableStateOf(false)
        private set

    private val inProcess = mutableStateOf(false)
    private val eventMutableState = mutableStateOf<Event<String>?>(null)
    val signIn = mutableStateOf(false)

    private val _productDetails = MutableLiveData<Product?>()
    val productDetails: LiveData<Product?> get() = _productDetails

    private val _profileUpdateStatus = MutableStateFlow(ProfileUpdateStatus.IDLE)
    val profileUpdateStatus: StateFlow<ProfileUpdateStatus> get() = _profileUpdateStatus

    private val _userDetails = MutableStateFlow<UserData?>(null)
    val userDetails: StateFlow<UserData?> get() = _userDetails.asStateFlow()

    private val ioScope = viewModelScope + Dispatchers.IO // Optimized scope for I/O tasks

    enum class ProfileUpdateStatus {
        IDLE, SUCCESS, FAILURE
    }

    fun displayDialog() {
        isDialogShow = true
    }

    fun dismissDialog() {
        isDialogShow = false
    }

    fun getCurrentUserId(callback: (String) -> Unit) {
        auth.currentUser?.uid?.let(callback) ?: callback("") // Optimized to one-liner
    }

    private fun checkUserData(navController: NavController) {
        ioScope.launch {
            val userId = auth.currentUser?.uid
            if (userId == null) {
                Log.e("CheckUserData", "User ID is null.")
                withContext(Dispatchers.Main) {
                    navController.navigate(Route.LoginScreen.name) {
                        popUpTo(Route.LoginScreen.name) { inclusive = true }
                    }
                }
                return@launch
            }

            val userDoc = try {
                db.collection("users").document(userId).get().await()
            } catch (e: Exception) {
                handleException(e, "Error checking user data")
                return@launch
            }

            withContext(Dispatchers.Main) {
                if (userDoc.exists()) {
                    navController.navigate(Route.BottomNav.name) {
                        popUpTo(Route.BottomNav.name) { inclusive = true }
                    }
                } else {
                    navController.navigate(Route.UserDataCollectionScreen.name) {
                        popUpTo(Route.UserDataCollectionScreen.name) { inclusive = true }
                    }
                }
            }
        }
    }

    fun signUp(email: String, password: String, navController: NavController, context: Context) {
        if (email.isEmpty() || password.isEmpty()) {
            showToast(context, "Email or password cannot be empty")
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showToast(context, "Sign-Up Successful")
                    navController.navigate(Route.UserDataCollectionScreen.name)
                } else {
                    showToast(context, "Sign-Up Failed: ${task.exception?.message}")
                }
            }
    }

    fun resetPassword(email: String, context: Context) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                showToast(context, "A password reset email has been sent")
            } else {
                showToast(context, "Something went wrong - ${task.exception?.message}")
            }
        }
    }

    fun login(email: String, password: String, context: Context, navController: NavController) {
        if (email.isEmpty() || password.isEmpty()) {
            showToast(context, "Email or password is empty")
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    checkUserData(navController)
                    showToast(context, "Login Successful")
                } else {
                    showToast(context, "Login Failed: ${task.exception?.message}")
                }
            }
    }

    fun logout() {
        auth.signOut()
        signIn.value = false
        _userDetails.value = null
        eventMutableState.value = Event("Logged Out")
    }

    private fun handleException(exception: Exception? = null, customMessage: String = "") {
        val errorMessage = exception?.localizedMessage ?: customMessage
        Log.e("Tag", errorMessage, exception)
        eventMutableState.value = Event(errorMessage)
        inProcess.value = false
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun createOrUpdateProfile(name: String, number: String, imageUri: String? = null) {
        ioScope.launch {
            val userId = auth.currentUser?.uid ?: run {
                handleException(Exception("User ID is null"), "Failed to get user ID")
                _profileUpdateStatus.value = ProfileUpdateStatus.FAILURE
                return@launch
            }

            try {
                val imageUrl = imageUri?.let { uploadProfileImage(Uri.parse(it)) }
                val userProfile = UserData(userId, name, number, imageUrl)

                db.collection("users").document(userId).set(userProfile).await()

                _profileUpdateStatus.value = ProfileUpdateStatus.SUCCESS
            } catch (e: Exception) {
                handleException(e, "Failed to update profile")
                _profileUpdateStatus.value = ProfileUpdateStatus.FAILURE
            }
        }
    }

    suspend fun uploadProfileImage(uri: Uri): String? {
        val userId = auth.currentUser?.uid ?: return null
        val imageRef = storage.reference.child("profile_images/$userId/${UUID.randomUUID()}")
        return try {
            imageRef.putFile(uri).await()
            imageRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            handleException(e, "Failed to upload profile image")
            null
        }
    }

    fun uploadProductDetails(
        title: String,
        description: String,
        imageUri: String? = null,
        pickupPoint: String,
        deliveryPoint: String,
        time: String,
        date: String,
        rewards: String,
        context: Context
    ) {
        ioScope.launch {
            // Fetch user ID
            val userId = auth.currentUser?.uid ?: run {
                handleException(Exception("User ID is null"), "Failed to get user ID")
                return@launch
            }

            try {
                // Upload image if provided
                val imageUrl = imageUri?.let { Uri.parse(it)?.let { uri -> uploadProductImage(uri) } }
                Log.d("UploadProductDetails", "Image URL: $imageUrl")

                // Create the Product object
                val product = Product(
                    title = title,
                    description = description,
                    imageUrl = imageUrl,
                    pickupPoint = pickupPoint,
                    deliveryPoint = deliveryPoint,
                    time = time,
                    date = date,
                    rewards = rewards,
                    userId = userId // Ensuring userId is correctly assigned
                )
                Log.d("UploadProductDetails", "Product: $product")

                // Upload product to Firestore
                db.collection("products").document(product.productId).set(product).await()

                // Switch back to main thread to show toast
                withContext(Dispatchers.Main) {
                    showToast(context, "Product uploaded successfully")
                }

            } catch (e: Exception) {
                // Handle any errors during the upload process
                handleException(e, "Failed to upload product")
            }
        }
    }


    private suspend fun uploadProductImage(uri: Uri): String? {
        val userId = auth.currentUser?.uid ?: return null
        val imageRef = storage.reference.child("product_images/$userId/${UUID.randomUUID()}")
        return try {
            imageRef.putFile(uri).await()
            imageRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            handleException(e, "Failed to upload product image")
            null
        }
    }

    fun fetchProductDetails(productId: String) {
        ioScope.launch {
            try {
                val productDoc = db.collection("products").document(productId).get().await()
                _productDetails.postValue(productDoc.toObject(Product::class.java))
            } catch (e: Exception) {
                Log.e("Firestore", "Error fetching product details", e)
            }
        }
    }

    fun fetchProductsFromFirestore(onProductsFetched: (List<Product>) -> Unit) {
        ioScope.launch {
            try {
                val products = db.collection("products").get().await().mapNotNull { doc ->
                    doc.toObject(Product::class.java).copy(productId = doc.id)
                }
                withContext(Dispatchers.Main) {
                    onProductsFetched(products)
                }
            } catch (e: Exception) {
                Log.e("Firestore", "Error fetching products", e)
            }
        }
    }

    fun fetchUserDetails() {
        ioScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch
            try {
                val userDoc = db.collection("users").document(userId).get().await()
                _userDetails.value = userDoc.toObject(UserData::class.java)
            } catch (e: Exception) {
                Log.e("Firestore", "Error fetching user details", e)
            }
        }
    }

    fun saveUserDetails(updatedUser: UserData) {
        ioScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch
            try {
                db.collection("users").document(userId).set(updatedUser).await()
                fetchUserDetails()
            } catch (e: Exception) {
                Log.e("Firestore", "Error updating user details", e)
            }
        }
    }

    fun getProductOwner(productId: String, onOwnerFetched: (UserData?) -> Unit) {
        ioScope.launch {
            try {
                val productDoc = db.collection("products").document(productId).get().await()
                val product = productDoc.toObject(Product::class.java)
                product?.let {
                    val ownerDoc =
                        it.userId?.let { it1 -> db.collection("users").document(it1).get().await() }
                    withContext(Dispatchers.Main) {
                        if (ownerDoc != null) {
                            onOwnerFetched(ownerDoc.toObject(UserData::class.java))
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("Firestore", "Error fetching product owner", e)
            }
        }
    }
}
