package com.example.tripdrop

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.tripdrop.data.Event
import com.example.tripdrop.data.UserData
import com.example.tripdrop.ui.navigation.Route
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DropViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ViewModel() {

    // Mutable state variables for UI updates
    val inProcess = mutableStateOf(false)
    val eventMutableState = mutableStateOf<Event<String>?>(null)
    val signIn = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)

    init {
        // Check if the user is already signed in and fetch user data
        auth.currentUser?.uid?.let {
            getUserData(it)
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
                    navController.navigate(Route.LoginScreen.route) // Navigate to Login Screen
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
                    navController.navigate(Route.BottomNav.route)
                    showToast(context, "Login Successful")
                } else {
                    showToast(context, "Login Failed: ${task.exception?.message}")
                }
            }
    }

    /**
     * Create or update the user's profile in Firestore.
     */
    fun createOrUpdateProfile(
        name: String? = null,
        number: String? = null,
        imageUrl: String? = null
    ) {
        val uid = auth.currentUser?.uid ?: return
        val updatedUserData = UserData(
            userId = uid,
            name = name ?: userData.value?.name,
            number = number ?: userData.value?.number,
            imageUrl = imageUrl
        )

        inProcess.value = true
        db.collection(USER_NODE).document(uid).get().addOnSuccessListener { document ->
            if (document.exists()) {
                // Update existing user data
                db.collection(USER_NODE).document(uid).update(
                    mapOf(
                        "name" to updatedUserData.name,
                        "number" to updatedUserData.number,
                        "imageUrl" to updatedUserData.imageUrl
                    )
                )
            } else {
                // Create new user data
                db.collection(USER_NODE).document(uid).set(updatedUserData)
            }
            inProcess.value = false
            getUserData(uid) // Refresh user data
        }.addOnFailureListener {
            handleException(it, "Cannot Retrieve User")
        }
    }

    /**
     * Retrieve the user's data from Firestore in real-time.
     */
    private fun getUserData(uid: String) {
        db.collection(USER_NODE).document(uid).addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                handleException(exception, "Cannot Retrieve User")
                return@addSnapshotListener
            }

            snapshot?.toObject<UserData>()?.let {
                userData.value = it
                inProcess.value = false
            }
        }
    }

    /**
     * Upload a profile image to Firebase Storage and update the user's profile with the image URL.
     */
    fun uploadProfileImage(uri: Uri) {
        uploadImage(uri) { imageUrl ->
            userData.value?.userId?.let { userId ->
                createOrUpdateProfile(imageUrl = imageUrl.toString())
            }
        }
    }

    /**
     * Upload an image to Firebase Storage.
     */
    private fun uploadImage(uri: Uri, onSuccess: (Uri) -> Unit) {
        inProcess.value = true
        val storageRef = storage.reference.child("images/${UUID.randomUUID()}")
        storageRef.putFile(uri).addOnSuccessListener {
            it.metadata?.reference?.downloadUrl?.addOnSuccessListener(onSuccess)
            inProcess.value = false
        }.addOnFailureListener {
            handleException(it)
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
    fun handleException(exception: Exception? = null, customMessage: String = "") {
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
}
