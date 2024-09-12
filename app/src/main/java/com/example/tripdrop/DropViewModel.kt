package com.example.tripdrop

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.tripdrop.data.Event
import com.example.tripdrop.data.UserData
import com.example.tripdrop.ui.navigation.Route
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
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

    // State variables
    val inProcess = mutableStateOf(false)
    val eventMutableState = mutableStateOf<Event<String>?>(null)
    val signIn = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)
    var currentChatMessageListener: ListenerRegistration? = null

    init {
        // Initialize user data if already signed in
        auth.currentUser?.uid?.let {
            getUserData(it)
        }
    }

    fun signUp(email: String,password: String, navController: NavController) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign-up successful
//                    Toast.makeText(context, "Sign-Up Successful", Toast.LENGTH_LONG).show()
                    navController.navigate(Route.LoginScreen.route) // Navigate to Login Screen
                } else {
                    // Sign-up failed
//                    Toast.makeText(context, "Sign-Up Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    fun login(email: String, password: String, navController: NavController) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login successful
//                    Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show()
                    navController.navigate(Route.BottomNav.route) // Navigate to Home Screen
                } else {
                    // Login failed
//                    Toast.makeText(context, "Login Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }


    // Create or update user profile
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
            getUserData(uid)
        }.addOnFailureListener {
            handleException(it, "Cannot Retrieve User")
        }
    }

    // Retrieve user data from Fire store
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

    // Handle exceptions and update UI state
    fun handleException(exception: Exception? = null, customMessage: String = "") {
        Log.e("Tag", "ChatterBox exception!", exception)
        val errorMessage = exception?.localizedMessage ?: ""
        val message = customMessage.ifEmpty { errorMessage }
        eventMutableState.value = Event(message)
        inProcess.value = false
    }

    // Upload profile image and update user's profile with the image URL
    fun uploadProfileImage(uri: Uri) {
        uploadImage(uri) { imageUrl ->
            userData.value?.userId?.let { userId ->
                createOrUpdateProfile(imageUrl = imageUrl.toString())
            }
        }
    }

    // Upload image to Firebase Storage
    private fun uploadImage(uri: Uri, onSuccess: (Uri) -> Unit) {
        inProcess.value = true
        val storageRef = storage.reference
        val uuid = UUID.randomUUID().toString()
        val imageRef = storageRef.child("images/$uuid")
        imageRef.putFile(uri).addOnSuccessListener {
            it.metadata?.reference?.downloadUrl?.addOnSuccessListener(onSuccess)
            inProcess.value = false
        }.addOnFailureListener {
            handleException(it)
        }
    }

    // Log out the current user
    fun logout() {
        auth.signOut()
        signIn.value = false
        userData.value = null
        eventMutableState.value = Event("Logged Out")
        currentChatMessageListener = null
    }
}
