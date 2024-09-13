package com.example.tripdrop.ui.presentation.authentication

import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.painterResource
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

@Composable
fun LoginScreen(navController: NavController,vm : DropViewModel) {
    // State variables for email, password, and password visibility
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordHidden by remember { mutableStateOf(true) }
    val context = LocalContext.current

    val signIn by remember(vm.signIn) { vm.signIn }
    if (signIn) {
        navController.navigate(Route.BottomNav.route)
    }

    BackHandler {
        navController.navigate(Route.WelcomeScreen.route)
    }

    // Root container
    Box(
        modifier = Modifier
            .background(colorResource(id = R.color.white)) // Background color
            .fillMaxSize() // Fill the screen
            .padding(16.dp) // Padding for spacing
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp, start = 8.dp, end = 8.dp), // Padding for top and sides
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Header Text
            Column(
                modifier = Modifier.fillMaxWidth() // Responsive to screen width
            ) {
                Text(
                    text = "Let's Sign you in",
                    color = colorResource(id = R.color.black),
                    fontSize = 42.sp, // Large font size for readability
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Start) // Align text to start
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Welcome Back, ",
                    color = colorResource(id = R.color.black),
                    fontSize = 24.sp,
                    modifier = Modifier.align(Alignment.Start) // Align text to start
                )
                Text(
                    text = "You have been missed",
                    color = colorResource(id = R.color.black),
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(top = 4.dp) // Spacing between texts
                        .align(Alignment.Start) // Align text to start
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Email Input Field
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.white)), // Background color

                value = email,
                onValueChange = { email = it }, // Update email

                leadingIcon = {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = null,
                        tint = colorResource(id = R.color.Gray)
                    )
                },

                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                ),

                placeholder = {
                    Text(
                        text = "Email", color = colorResource(id = R.color.Gray)
                    )
                },

                singleLine = true,

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black,
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Input Field
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.white)), // Background color

                value = password,
                onValueChange = { password = it }, // Update password

                placeholder = {
                    Text(
                        text = "Password", color = colorResource(id = R.color.Gray)
                    )
                },
                singleLine = true,

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
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black,
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Forgot Password Text
            Text(
                text = "Forgot Password ?",
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 8.dp), // Padding for alignment
                color = colorResource(id = R.color.black),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Sign In Button
            Button(
                onClick = {
                    // Move to Home Screen
                    vm.login(email = email, password = password, context, navController = navController)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.black))
            ) {
                Text(
                    text = "Sign In",
                    color = colorResource(id = R.color.white),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // OR Divider Text
            Text(
                text = "------------------ OR --------------------",
                color = colorResource(id = R.color.Gray),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Social Media Icons
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = Color.Unspecified // Prevent tinting
                )

                Spacer(modifier = Modifier.width(10.dp))

                Icon(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = Color.Unspecified // Prevent tinting
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sign Up Text
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an Account ?",
                    color = colorResource(id = R.color.Gray),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.width(8.dp))

                Text(
                    text = "Sign Up",
                    modifier = Modifier.clickable {
                        navController.navigate(route = Route.SignUpScreen.route)
                    },
                    color = colorResource(id = R.color.black),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

