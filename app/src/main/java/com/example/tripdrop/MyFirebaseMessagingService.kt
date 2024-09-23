package com.example.tripdrop

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.HiltAndroidApp


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle the notification received
        remoteMessage.notification?.let {
            showNotification(it.title, it.body)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Refreshed token: $token")
        // Save or send the token to your app server or cloud Firestore.
        saveTokenToFirestore(token)
    }

    private fun showNotification(title: String?, body: String?) {
        val deepLinkIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("myapp://notification")
        )
        deepLinkIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            deepLinkIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, "default")
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.delivery)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(1001, notificationBuilder.build())
    }



    private fun saveTokenToFirestore(token: String) {
        // This is where you would save the token to Firestore or elsewhere
        // Assuming you have a Firestore setup:
        val firestore = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid // Example, replace with actual user id

        if (userId != null) {
            val tokenData = mapOf("fcmToken" to token)
            firestore.collection("users").document(userId)
                .set(tokenData, SetOptions.merge())
                .addOnSuccessListener {
                    Log.d("FCM", "Token saved successfully to Firestore")
                }
                .addOnFailureListener { e ->
                    Log.e("FCM", "Failed to save token to Firestore", e)
                }
        }
    }
}
