package com.example.tripdrop.ui.presentation.profile.child

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.tripdrop.data.model.UserData
import com.example.tripdrop.ui.navigation.Route
import com.example.tripdrop.ui.presentation.common.CommonImage
import com.example.tripdrop.ui.presentation.common.LoaderScreen
import com.example.tripdrop.ui.presentation.common.standardPadding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@Composable
fun ProfileDetailsScreen(navController: NavController, vm: DropViewModel) {
    LaunchedEffect(Unit) {
        vm.fetchUserDetails()
    }

    val userData by vm.userDetails.collectAsState()
    var name by rememberSaveable { mutableStateOf(userData?.name ?: "") }
    var number by rememberSaveable { mutableStateOf(userData?.number ?: "") }
    val imageUrl by remember { mutableStateOf(userData?.imageUrl) }

    if (userData == null) {
        LoaderScreen()
    } else {
        val context = LocalContext.current
        ProfileContent(
            navController = navController,
            imageUrl = imageUrl,
            vm = vm,
            onSave = {
                val updatedUser = UserData(
                    userId = userData!!.userId,
                    name = name,
                    number = number,
                    imageUrl = imageUrl
                )
                vm.saveUserDetails(updatedUser)
                Toast.makeText(
                    context,
                    "Profile update initiated",
                    Toast.LENGTH_SHORT
                ).show()
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
    var isNameFieldEnabled by remember { mutableStateOf(false) }
    var isNumberFieldEnabled by remember { mutableStateOf(false) }

    val chooseProfileImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        MainScope().launch {
            uri?.let {
                vm.uploadProfileImage(uri)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopAppBar(
            title = { AppBarTitle() },
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
                    Icon(Icons.Default.SaveAs, contentDescription = "Save", tint = Color.White)
                }
                IconButton(onClick = {
                    onLogout()
                    Toast.makeText(context, "Logout Successfully", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(Icons.Default.Logout, contentDescription = "Logout", tint = Color.White)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(R.color.black))
        )

        Spacer(modifier = Modifier.height(18.dp))

        ProfileImagePicker(imageUrl = imageUrl, chooseProfileImage = chooseProfileImage)

        Spacer(modifier = Modifier.height(18.dp))

        UserInfoFields(
            name = name,
            number = number,
            onNameChange = onNameChange,
            onNumberChange = onNumberChange,
            isNameFieldEnabled = isNameFieldEnabled,
            isNumberFieldEnabled = isNumberFieldEnabled,
            onNameFieldToggle = { isNameFieldEnabled = !isNameFieldEnabled },
            onNumberFieldToggle = { isNumberFieldEnabled = !isNumberFieldEnabled }
        )
    }
}

@Composable
fun AppBarTitle() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "My Profile",
            color = Color.White,
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ProfileImagePicker(
    imageUrl: String?,
    chooseProfileImage: ManagedActivityResultLauncher<String, Uri?>
) {
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
                .clickable { chooseProfileImage.launch("image/*") }
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
}

@Composable
fun UserInfoFields(
    name: String,
    number: String,
    onNameChange: (String) -> Unit,
    onNumberChange: (String) -> Unit,
    isNameFieldEnabled: Boolean,
    isNumberFieldEnabled: Boolean,
    onNameFieldToggle: () -> Unit,
    onNumberFieldToggle: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = standardPadding, end = standardPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier.width(380.dp),
            value = name,
            onValueChange = onNameChange,
            enabled = isNameFieldEnabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colorResource(id = R.color.black),
                unfocusedTextColor = Color.Black,
                focusedBorderColor = colorResource(id = R.color.black),
                cursorColor = colorResource(id = R.color.black),
                disabledBorderColor = colorResource(id = R.color.black),
                disabledTextColor = colorResource(id = R.color.black)
            ),
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = null, tint = Color.Black)
            },
            trailingIcon = {
                IconButton(onClick = onNameFieldToggle) {
                    Icon(Icons.Default.Edit, contentDescription = null, tint = Color.Black)
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            modifier = Modifier.width(380.dp),
            value = number,
            onValueChange = onNumberChange,
            enabled = isNumberFieldEnabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colorResource(id = R.color.black),
                unfocusedTextColor = Color.Black,
                focusedBorderColor = colorResource(id = R.color.black),
                cursorColor = colorResource(id = R.color.black),
                disabledBorderColor = colorResource(id = R.color.black),
                disabledTextColor = colorResource(id = R.color.black)
            ),
            leadingIcon = {
                Icon(Icons.Default.Phone, contentDescription = null, tint = Color.Black)
            },
            trailingIcon = {
                IconButton(onClick = onNumberFieldToggle) {
                    Icon(Icons.Default.Edit, contentDescription = null, tint = Color.Black)
                }
            }
        )
    }
}
