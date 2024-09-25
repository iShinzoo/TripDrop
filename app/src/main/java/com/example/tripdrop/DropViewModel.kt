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

    /**
     * Checks the user's data and navigates to the appropriate screen based on whether the user's data exists in the database.
     *
     * This function retrieves the current user's ID from Firebase Authentication, and then checks if the user's data exists in the Firestore database. If the user's data exists, the function navigates to the main screen. If the user's data does not exist, the function navigates to the user data collection screen.
     *
     * @param navController The navigation controller to use for navigating to the appropriate screen.
     */
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

    /**
     * Registers a new user with the provided email and password, and navigates to the user data collection screen upon successful sign-up.
     *
     * @param email The email address to use for the new user account.
     * @param password The password to use for the new user account.
     * @param navController The navigation controller to use for navigating to the user data collection screen.
     * @param context The application context.
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
                    navController.navigate(Route.UserDataCollectionScreen.name)
                } else {
                    showToast(context, "Sign-Up Failed: ${task.exception?.message}")
                }
            }
    }

    /**
     * Sends a password reset email to the specified email address.
     *
     * @param email The email address to send the password reset email to.
     * @param context The application context.
     */
    fun resetPassword(email: String, context: Context) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                showToast(context, "A password reset email has been sent")
            } else {
                showToast(context, "Something went wrong - ${task.exception?.message}")
            }
        }
    }

    /**
     * Logs in a user with the provided email and password, and navigates to the appropriate screen based on the user's data.
     *
     * This function takes the user's email and password, and attempts to sign in the user using Firebase Authentication. If the sign-in is successful, it checks the user's data and navigates to the appropriate screen (either the main screen or the user data collection screen). If the sign-in fails, it displays a toast message with the error message.
     *
     * @param email The email address of the user to sign in.
     * @param password The password of the user to sign in.
     * @param context The application context.
     * @param navController The navigation controller to use for navigating to the appropriate screen.
     */
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

    /**
     * Creates or updates the user's profile in the Firestore database.
     *
     * This function takes the user's name, phone number, and an optional profile image URI, and updates the user's profile in the Firestore database. If the user's profile image is provided, it first uploads the image to Firebase Storage and then updates the user's profile with the download URL of the uploaded image.
     *
     * If the update is successful, the function sets the [ProfileUpdateStatus] to [ProfileUpdateStatus.SUCCESS]. If there is an error during the update process, the function logs the error and sets the [ProfileUpdateStatus] to [ProfileUpdateStatus.FAILURE].
     *
     * @param name The user's name.
     * @param number The user's phone number.
     * @param imageUri The URI of the user's profile image (optional).
     */
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

    /**
     * Uploads a profile image to Firebase Storage and returns the download URL.
     *
     * This function takes a Uri representing the profile image to be uploaded, and uploads it to Firebase Storage under the "profile_images" directory, using a unique filename based on the current user's ID. If the upload is successful, the function returns the download URL of the uploaded image. If there is an error during the upload process, the function logs the error and returns null.
     *
     * @param uri The Uri of the profile image to be uploaded.
     * @return The download URL of the uploaded profile image, or null if the upload failed.
     */
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

    /**
     * Uploads a new product to the Firestore database.
     *
     * This function is used to create a new product in the Firestore database. It takes in various product details such as title, description, image URI, pickup and delivery points, time, date, and rewards. It then uploads the product image to Firebase Storage (if provided) and creates a new [Product] object with the provided details. Finally, it uploads the product to the "products" collection in Firestore.
     *
     * If the upload is successful, a toast message is shown to the user indicating that the product was uploaded successfully. If there is an error during the upload process, the function logs the error and shows an error message to the user.
     *
     * @param title The title of the product.
     * @param description The description of the product.
     * @param imageUri The URI of the product image (optional).
     * @param pickupPoint The pickup point for the product.
     * @param deliveryPoint The delivery point for the product.
     * @param time The time for the product pickup/delivery.
     * @param date The date for the product pickup/delivery.
     * @param rewards The rewards associated with the product.
     * @param context The application context.
     */
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
                val imageUrl =
                    imageUri?.let { Uri.parse(it)?.let { uri -> uploadProductImage(uri) } }
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


    /**
     * Uploads a product image to the Firebase Storage and returns the download URL.
     *
     * This function is used to upload a product image to the Firebase Storage service. It generates a unique file name for the image based on the current user's ID and a random UUID, and then uploads the image to the "product_images" directory in the storage.
     *
     * If the upload is successful, the function returns the download URL of the uploaded image. If there is an error during the upload process, the function logs the error and returns `null`.
     *
     * @param uri The URI of the image to be uploaded.
     * @return The download URL of the uploaded image, or `null` if the upload fails.
     */
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

    /**
     * Fetches the details of a specific product from the Firestore database.
     *
     * This function is used to retrieve the details of a product with the given [productId] from the Firestore database. The product details are then stored in the `_productDetails` LiveData object, which can be observed by the UI layer to update the product information.
     *
     * If there is an error fetching the product details, an error message is logged to the console.
     *
     * @param productId The ID of the product to fetch.
     */
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

    /**
     * Fetches a list of [Product] objects from the Firestore database.
     *
     * This function is used to retrieve a list of all products stored in the Firestore database. The products are returned as a list of [Product] objects, with each product's ID assigned to the `productId` property.
     *
     * If there is an error fetching the products, an error message is logged to the console.
     *
     * @param onProductsFetched A callback function that will be called with the list of fetched products.
     */
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

    /**
     * Fetches the current user's details from the Firestore database.
     *
     * This function is used to retrieve the user's details, such as their name, email, and other profile information, from the Firestore database. The user's details are then stored in the `_userDetails` LiveData object, which can be observed by the UI layer to update the user's profile information.
     *
     * If there is an error fetching the user's details, an error message is logged to the console.
     */
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

    /**
     * Saves the updated user details to the Firestore database.
     *
     * @param updatedUser The updated [UserData] object to be saved.
     */
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

    /**
     * Fetches the owner details for the specified product.
     *
     * @param productId The ID of the product to fetch the owner for.
     * @param onOwnerFetched A callback function that will be called with the owner's [UserData] object, or `null` if the owner could not be fetched.
     */
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
