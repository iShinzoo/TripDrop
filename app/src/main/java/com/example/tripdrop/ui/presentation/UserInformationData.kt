package com.example.tripdrop.ui.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tripdrop.DropViewModel
import com.example.tripdrop.R


@Composable
fun UserDataCollection(vm : DropViewModel) {

    var name by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }

    // Launcher for picking a profile image
    val chooseProfileImage = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { vm.uploadProfileImage(uri) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(start = 16.dp, top = 80.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Title Text
            Text(
                text = "Basic Info",
                fontSize = 24.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))



            Spacer(modifier = Modifier.height(8.dp))

            // Upload Profile Picture Text
            Text(
                text = "Upload a Profile Picture",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Name TextField
            Spacer(modifier = Modifier.height(18.dp))

            // User Information Updating Section
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Name TextField
                OutlinedTextField(
                    modifier = Modifier.width(380.dp),
                    value = name,
                    onValueChange = { name = it },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = colorResource(id = R.color.black),
                        focusedBorderColor = colorResource(id = R.color.black),
                        cursorColor = colorResource(id = R.color.black),
                        disabledBorderColor = colorResource(id = R.color.black),
                        disabledTextColor = colorResource(id = R.color.black)
                    ),
                    leadingIcon = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Phone Number TextField
                OutlinedTextField(
                    modifier = Modifier.width(380.dp),
                    value = number,
                    onValueChange = { number = it },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = colorResource(id = R.color.black),
                        focusedBorderColor = colorResource(id = R.color.black),
                        cursorColor = colorResource(id = R.color.black),
                        disabledBorderColor = colorResource(id = R.color.black),
                        disabledTextColor = colorResource(id = R.color.black)
                    ),
                    leadingIcon = {
                        Icon(
                            Icons.Default.Phone,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Forward Button
                Button(
                    onClick = {
                        // Navigate to the next screen
//                navController.navigate("nextScreen")
                    },
                    modifier = Modifier
                        .size(60.dp) // Size of the circular button
                        .clip(CircleShape)
                        .background(Color.Blue),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.black)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForwardIos, // Forward icon resource
                        contentDescription = "Forward",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
