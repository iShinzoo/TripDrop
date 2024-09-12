package com.example.tripdrop.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tripdrop.R

@Composable
fun NotificationScreen() {

    val notifications = listOf<String>()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding()
            .background(colorResource(id = R.color.white))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            // Notifications Header
            Text(
                text = "Notifications",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 8.dp),
                color = colorResource(id = R.color.black),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Scrollable Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 88.dp) // Adjust this to fit the space occupied by the header
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if (notifications.isEmpty()) {
                // Display default image if there are no notifications
                Image(
                    painter = painterResource(id = R.drawable.package_arrived), // Replace with your default image
                    contentDescription = "No Notifications",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(16.dp)
                )
                Text(
                    text = "No Notifications Yet",
                    color = colorResource(id = R.color.black),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                )
            } else {
                // Display list of notifications
                notifications.forEach { notification ->
                    Text(
                        text = notification,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        color = colorResource(id = R.color.black),
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun NotificationScreenPreview(){
    NotificationScreen()
}