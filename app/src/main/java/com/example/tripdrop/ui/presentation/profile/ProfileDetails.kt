package com.example.tripdrop.ui.presentation.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.SaveAs
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tripdrop.DropViewModel
import com.example.tripdrop.R
import com.example.tripdrop.ui.navigation.Route
import com.example.tripdrop.ui.presentation.CommonImage

@Composable
fun ProfileDetailsScreen(navController: NavController, vm: DropViewModel) {
    // Get user data from the ViewModel
    val userData = vm.userData.value

    // State to manage user input
    var name by rememberSaveable { mutableStateOf(userData?.name ?: "") }
    var number by rememberSaveable { mutableStateOf(userData?.number ?: "") }
    val imageUrl = vm.userData.value?.imageUrl

    val context = LocalContext.current

    ProfileContent(
        navController = navController,
        imageUrl = imageUrl,
        vm = vm,
        onSave = {
            vm.createOrUpdateProfile(name = name, number = number, imageUri = imageUrl)
            Toast.makeText(context, "Details Updated Successfully", Toast.LENGTH_SHORT).show()
        },
        onLogout = {
            vm.logout()
            navController.navigate(Route.LoginScreen.name)
        },
        name = name,
        number = number,
        onNameChange = { name = it },
        onNumberChange = { number = it }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    navController: NavController,
    imageUrl: String?,
    vm: DropViewModel,
    onSave: () -> Unit,
    onLogout: () -> Unit,
    name: String,
    number: String,
    onNameChange: (String) -> Unit,
    onNumberChange: (String) -> Unit
) {
    val context = LocalContext.current

    // State to manage whether text fields are enabled
    var isNameFieldEnabled by remember { mutableStateOf(false) }
    var isNumberFieldEnabled by remember { mutableStateOf(false) }

    // Launcher for picking a profile image
//    val chooseProfileImage =
//        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
//            uri?.let {
//                // Launch a coroutine to call the suspend function
//                viewModelScope.launch {
//                    vm.uploadProfileImage(uri)
//                }
//            }
//        }


    Column(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "My Profile",
                        color = Color.White,
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigate(Route.ProfileScreen.name) }) {
                    Icon(
                        Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            actions = {
                IconButton(onClick = onSave) {
                    Icon(
                        Icons.Default.SaveAs,
                        contentDescription = "Save",
                        tint = Color.White
                    )
                }
                IconButton(onClick = {
                    onLogout()
                    Toast.makeText(context, "Logout Successfully", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(
                        Icons.Default.Logout,
                        contentDescription = "Logout",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(R.color.black))
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Profile Image Picker Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CommonImage(
                data = imageUrl,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clickable {
//                        chooseProfileImage.launch("image/*")
                    }
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Change Profile Picture",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500,
                    color = colorResource(id = R.color.black)
                )
            )
        }

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
                onValueChange = onNameChange,
                enabled = isNameFieldEnabled,
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = colorResource(id = R.color.black),
                    focusedBorderColor = colorResource(id = R.color.black),
                    cursorColor = colorResource(id = R.color.black),
                    disabledBorderColor = colorResource(id = R.color.black),
                    disabledTextColor = colorResource(id = R.color.black)
                ),
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color.Black) },
                trailingIcon = {
                    IconButton(onClick = { isNameFieldEnabled = !isNameFieldEnabled }) {
                        Icon(Icons.Default.Edit, contentDescription = null, tint = Color.Black)
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Phone Number TextField
            OutlinedTextField(
                modifier = Modifier.width(380.dp),
                value = number,
                onValueChange = onNumberChange,
                enabled = isNumberFieldEnabled,
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = colorResource(id = R.color.black),
                    focusedBorderColor = colorResource(id = R.color.black),
                    cursorColor = colorResource(id = R.color.black),
                    disabledBorderColor = colorResource(id = R.color.black),
                    disabledTextColor = colorResource(id = R.color.black)
                ),
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = Color.Black) },
                trailingIcon = {
                    IconButton(onClick = { isNumberFieldEnabled = !isNumberFieldEnabled }) {
                        Icon(Icons.Default.Edit, contentDescription = null, tint = Color.Black)
                    }
                }
            )
        }
    }
}
