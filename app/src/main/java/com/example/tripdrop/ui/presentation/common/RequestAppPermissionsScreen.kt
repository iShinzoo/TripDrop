package com.example.tripdrop.ui.presentation.common

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestAppPermissionsScreen() {
    // Permissions to request based on Android version
    val permissionsList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        listOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.SEND_SMS,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.POST_NOTIFICATIONS // For Android 13 and above
        )
    } else {
        listOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.SEND_SMS,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    // Remember permission states
    val permissionsState = rememberMultiplePermissionsState(permissionsList)

    // Launch permissions request when the composable is displayed
    LaunchedEffect(Unit) {
        permissionsState.launchMultiplePermissionRequest()
    }

    // Handle different permission states
    when {
        permissionsState.allPermissionsGranted -> {
            Text("All permissions are granted. The app can proceed.")
        }
        permissionsState.shouldShowRationale -> {
            Column {
                Text("Permissions are required for the app to function correctly.")
                Button(onClick = { permissionsState.launchMultiplePermissionRequest() }) {
                    Text("Request Permissions")
                }
            }
        }
        else -> {
            Text("Permissions are denied. Please grant them in settings.")
        }
    }
}

@Preview
@Composable
fun PreviewPermissionScreen() {
    RequestAppPermissionsScreen()
}
