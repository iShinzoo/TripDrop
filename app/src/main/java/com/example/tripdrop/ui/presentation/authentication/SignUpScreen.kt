package com.example.tripdrop.ui.presentation.authentication

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tripdrop.DropViewModel
import com.example.tripdrop.R
import com.example.tripdrop.ui.navigation.Route
import com.example.tripdrop.ui.presentation.CheckUserSignedIn

@Composable
fun SignUpScreen(navController: NavController, vm: DropViewModel) {
    val context = LocalContext.current

    // State variables for user input fields
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordHidden by remember { mutableStateOf(true) }

    BackHandler {
        navController.navigate(Route.WelcomeScreen.name)
    }

    CheckUserSignedIn(vm = vm, navController = navController)

    // Main container
    Box(
        modifier = Modifier
            .background(colorResource(id = R.color.white))
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp, start = 8.dp, end = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Header Text
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Let's Register",
                    color = colorResource(id = R.color.black),
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Account",
                    color = colorResource(id = R.color.black),
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "Hello Champ, hope you",
                    color = colorResource(id = R.color.black),
                    fontSize = 24.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "have a grateful journey",
                    color = colorResource(id = R.color.black),
                    fontSize = 24.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Email Input
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.white)),
                value = email,
                onValueChange = { email = it },
                leadingIcon = {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = null,
                        tint = colorResource(id = R.color.Gray)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                placeholder = { Text(text = "Email", color = colorResource(id = R.color.Gray)) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black,
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Input
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.white)),
                value = password,
                onValueChange = { password = it },
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = null,
                        tint = colorResource(id = R.color.Gray)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordHidden = !passwordHidden }) {
                        val visibilityIcon =
                            if (passwordHidden) Icons.Default.VisibilityOff else Icons.Default.Visibility
                        val description = if (passwordHidden) "Show Password" else "Hide Password"
                        Icon(
                            imageVector = visibilityIcon,
                            contentDescription = description,
                            tint = colorResource(id = R.color.Gray)
                        )
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                placeholder = { Text(text = "Password", color = colorResource(id = R.color.Gray)) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black,
                )
            )

            Spacer(modifier = Modifier.height(22.dp))

            // Sign Up Button
            androidx.compose.material3.Button(
                onClick = {
                    vm.signUp(
                        email = email,
                        password = password,
                        context = context,
                        navController = navController
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.black))
            ) {
                Text(
                    text = "Sign Up",
                    color = colorResource(id = R.color.white),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Sign In Navigation
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an Account?",
                    color = colorResource(id = R.color.Gray),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Sign In",
                    modifier = Modifier.clickable {
                        navController.navigate(route = Route.LoginScreen.name)
                    },
                    color = colorResource(id = R.color.black),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
