package com.example.tripdrop

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripdrop.data.model.NotificationBody
import com.example.tripdrop.data.model.NotificationRequest
import com.example.tripdrop.data.network.FcmService
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val fcmService: FcmService,
    private val firebaseMessaging: FirebaseMessaging
) : ViewModel() {

    private val _fcmToken = MutableLiveData<String>()
    val fcmToken: LiveData<String> = _fcmToken

    init {
        getToken()
    }

    private fun getToken() {
        firebaseMessaging.token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _fcmToken.value = task.result
            } else {
                Log.e("FCM", "Error getting token", task.exception)
            }
        }
    }

    fun sendNotification(token: String, productName: String) {
        val notificationRequest = NotificationRequest(
            to = token,
            notification = NotificationBody(
                title = "Ready to Drop!",
                body = "Someone is ready to drop your product: $productName"
            ),
            data = mapOf("screen" to "notificationScreen", "productName" to productName)
        )

        viewModelScope.launch {
            try {
                val response = fcmService.sendNotification(notificationRequest)
                if (response.isSuccessful) {
                    Log.d("Notification", "Notification sent successfully")
                } else {
                    Log.e("Notification", "Failed to send notification: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("Notification", "Error sending notification", e)
            }
        }
    }
}
