package com.example.tripdrop.ui.presentation

import android.net.Uri
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tripdrop.DropViewModel
import com.example.tripdrop.R
import com.example.tripdrop.ui.navigation.Route


@Composable
fun UserDataCollectionScreen(
    navController: NavController,
    vm: DropViewModel
) {
    val context = LocalContext.current

    var name by rememberSaveable { mutableStateOf("") }
    var number by rememberSaveable { mutableStateOf("") }
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val chooseProfileImage =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let { imageUri = it }
        }

    val profileUpdateStatus by vm.profileUpdateStatus.collectAsState()

    LaunchedEffect(profileUpdateStatus) {
        if (profileUpdateStatus == DropViewModel.ProfileUpdateStatus.SUCCESS) {
            navController.navigate(Route.BottomNav.route) {
                popUpTo(Route.BottomNav.route) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 80.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Basic Info",
                fontSize = 24.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(36.dp))

            CommonImage(
                data = imageUri?.toString(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .clickable {
                        chooseProfileImage.launch("image/*")
                    }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Upload a Profile Picture",
                fontSize = 16.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(38.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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

                Spacer(modifier = Modifier.height(18.dp))

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

                Button(
                    onClick = {
                        vm.createOrUpdateProfile(name = name, number = number, imageUri = imageUri.toString())
                        Toast.makeText(context, "Details Updated Successfully", Toast.LENGTH_SHORT)
                            .show()
                    },
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color.Blue),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.black)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForwardIos,
                        contentDescription = "Forward",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
