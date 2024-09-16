package com.example.tripdrop

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.tripdrop.data.Event
import com.example.tripdrop.data.Product
import com.example.tripdrop.data.UserData
import com.example.tripdrop.ui.navigation.Route
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DropViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ViewModel() {

    // Mutable state variables for UI updates
    private val inProcess = mutableStateOf(false)
    private val eventMutableState = mutableStateOf<Event<String>?>(null)
    val signIn = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)

    // LiveData to observe product details
    private val _productDetails = MutableLiveData<Product?>()
    val productDetails: MutableLiveData<Product?> = _productDetails

    private val firestore = FirebaseFirestore.getInstance()

    // StateFlow for profile update status
    private val _profileUpdateStatus = MutableStateFlow(ProfileUpdateStatus.IDLE)
    val profileUpdateStatus: StateFlow<ProfileUpdateStatus> = _profileUpdateStatus

    // Profile update status enum
    enum class ProfileUpdateStatus {
        IDLE, SUCCESS, FAILURE
    }

//    init {
//        // Check if the user is already signed in and fetch user data
//        auth.currentUser?.uid?.let {
//            getUserData(it)
//        }
//    }

    private fun checkUserData(navController: NavController) {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid
            if (userId == null) {
                // Handle null userId
                Log.e("CheckUserData", "User ID is null. User might not be signed in.")
                // Optionally, navigate to login screen or show an error message
                navController.navigate(Route.LoginScreen.name) {
                    popUpTo(Route.LoginScreen.name) { inclusive = true }
                }
                return@launch
            }

            try {
                val userDoc = db.collection("users").document(userId).get().await()
                if (userDoc.exists()) {
                    // User data exists, navigate to HomeScreen
                    navController.navigate(Route.BottomNav.name) {
                        popUpTo(Route.BottomNav.name) { inclusive = true }
                    }
                } else {
                    // User data does not exist, navigate to UserDataCollectionScreen
                    navController.navigate(Route.UserDataCollectionScreen.name) {
                        popUpTo(Route.UserDataCollectionScreen.name) { inclusive = true }
                    }
                }
            } catch (e: Exception) {
                handleException(e, "Error checking user data")
            }
        }
    }

    /**
     * Register a new user with the provided email and password.
     * Navigates to the login screen upon success.
     */
    fun signUp(email: String, password: String, navController: NavController, context: Context) {
        if (email.isEmpty() || password.isEmpty()) {
            showToast(context, "Email or password cannot be empty")
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showToast(context, "Sign-Up Successful")
                    navController.navigate(Route.UserDataCollectionScreen.name) // Navigate to UserDataCollection Screen
                } else {
                    showToast(context, "Sign-Up Failed: ${task.exception?.message}")
                }
            }
    }

    /**
     * Log in the user with the provided email and password.
     */
    fun login(email: String, password: String, context: Context, navController: NavController) {
        if (email.isEmpty() || password.isEmpty()) {
            showToast(context, "Email or password is empty")
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Login Success", "Successfully logged in")
                    checkUserData(navController) // Check user data and navigate accordingly
                    showToast(context, "Login Successful")
                } else {
                    showToast(context, "Login Failed: ${task.exception?.message}")
                }
            }
    }


    /**
     * Log out the current user and reset state.
     */
    fun logout() {
        auth.signOut()
        signIn.value = false
        userData.value = null
        eventMutableState.value = Event("Logged Out")
    }

    /**
     * Handle and log exceptions.
     */
    private fun handleException(exception: Exception? = null, customMessage: String = "") {
        val errorMessage = exception?.localizedMessage ?: customMessage
        Log.e("Tag", errorMessage, exception)
        eventMutableState.value = Event(errorMessage)
        inProcess.value = false
    }

    /**
     * Utility function to show toast messages.
     */
    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Create or update user profile in Fire store and upload profile image to Storage
     */
    fun createOrUpdateProfile(name: String, number: String, imageUri: String? = null) {
        viewModelScope.launch {
            _profileUpdateStatus.value = ProfileUpdateStatus.IDLE
            val userId = auth.currentUser?.uid ?: run {
                handleException(Exception("User ID is null"), "Failed to get user ID")
                _profileUpdateStatus.value = ProfileUpdateStatus.FAILURE
                return@launch
            }

            try {

                val uid = auth.currentUser?.uid

                val userProfile =
                    UserData(userId = uid, name = name, number = number, imageUrl = null)

                // Upload profile image if exists
                val imageUrl = imageUri?.let { Uri.parse(it)?.let { uri -> uploadProfileImage(uri) } }

                // Update Fire store user profile
                userProfile.copy(imageUrl = imageUrl).let { updatedProfile ->
                    db.collection("users").document(userId).set(updatedProfile)
                }

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
            Log.d("Upload", "Uploading image: $uri")
            imageRef.putFile(uri).await()
            val downloadUrl = imageRef.downloadUrl.await().toString()
            Log.d("Upload", "Upload successful, download URL: $downloadUrl")
            downloadUrl
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
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: run {
                handleException(Exception("User ID is null"), "Failed to get user ID")
                return@launch
            }

            try {
                // Upload image if provided
                val imageUrl = imageUri?.let { Uri.parse(it)?.let { uri -> uploadProductImage(uri) } }
                Log.d("UploadProductDetails", "Image URL: $imageUrl")

                // Create a Product object
                val product = Product(
                    title = title,
                    description = description,
                    imageUrl = imageUrl,
                    pickupPoint = pickupPoint,
                    deliveryPoint = deliveryPoint,
                    time = time,
                    date = date,
                    rewards = rewards,
                    userId = userId
                )
                Log.d("UploadProductDetails", "Product: $product")

                // Save product to Fire store
                db.collection("products").document(product.productId).set(product).await()
                Log.d("UploadProductDetails", "Product uploaded successfully")

                showToast(context, "Product uploaded successfully")

            } catch (e: Exception) {
                handleException(e, "Failed to upload product")
            }
        }
    }

    suspend fun uploadProductImage(uri: Uri): String? {
        val userId = auth.currentUser?.uid ?: return null
        val imageRef = storage.reference.child("product_images/$userId/${UUID.randomUUID()}")
        return try {
            Log.d("UploadImage", "Uploading image: $uri")
            imageRef.putFile(uri).await()
            val downloadUrl = imageRef.downloadUrl.await().toString()
            Log.d("UploadImage", "Upload successful, download URL: $downloadUrl")
            downloadUrl
        } catch (e: Exception) {
            handleException(e, "Failed to upload product image")
            null
        }
    }

    // Function to fetch product details from Firestore
    fun fetchProductDetails(productId: String) {
        firestore.collection("products")
            .document(productId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val product = document.toObject(Product::class.java)
                    _productDetails.value = product
                } else {
                    Log.e("Firestore", "Product not found")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error fetching product details", exception)
            }
    }

    // Function to fetch products from Firestore
    fun fetchProductsFromFirestore(onProductsFetched: (List<Product>) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("products").get()
            .addOnSuccessListener { result ->
                val productList = result.mapNotNull { document ->
                    document.toObject(Product::class.java).copy(productId = document.id)
                }
                onProductsFetched(productList)
            }
            .addOnFailureListener {
                // Handle failure
            }
    }
}
