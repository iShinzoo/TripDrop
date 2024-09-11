package com.example.tripdrop.presentation

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tripdrop.R
import com.example.tripdrop.navigation.Route


@Composable
fun LoginScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordHidden by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .background(colorResource(id = R.color.white))
            .fillMaxSize() // Fill the screen
            .padding(16.dp) // Add padding for better spacing on different screens
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp, start = 8.dp, end = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Column(
                modifier = Modifier.fillMaxWidth() // Make column responsive to screen width
            ) {
                Text(
                    text = "Let's Sign you in",
                    color = colorResource(id = R.color.black),
                    fontSize = 42.sp, // Adjust font size for better readability on smaller screens
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Welcome Back, ",
                    modifier = Modifier.align(Alignment.Start),
                    color = colorResource(id = R.color.black),
                    fontSize = 24.sp
                )
                Text(
                    text = "You have been missed",
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(Alignment.Start),
                    color = colorResource(id = R.color.black),
                    fontSize = 24.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))


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
                )
            )


            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth() // Make text field responsive to screen width
                    .background(colorResource(id = R.color.white)),

                value = password,
                onValueChange = { password = it },

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
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Forgot Password ?",
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 8.dp), // Adjusted padding for smaller screens
                color = colorResource(id = R.color.black),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    navController.navigate(route = Route.BottomNav.route)
                },
                modifier = Modifier
                    .fillMaxWidth() // Button width adjusts to screen size
                    .height(50.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.black))
            ) {
                Text(
                    text = "Sign In",
                    modifier = Modifier,
                    color = colorResource(id = R.color.white),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "------------------ OR --------------------",
                modifier = Modifier,
                color = colorResource(id = R.color.Gray),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Icon(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "",
                    modifier = Modifier.size(32.dp),
                    tint = Color.Unspecified // Prevent tinting
                )

                Spacer(modifier = Modifier.width(10.dp))

                Icon(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = "",
                    modifier = Modifier.size(32.dp),
                    tint = Color.Unspecified // Prevent tinting
                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Don't have an Account ?",
                    modifier = Modifier,
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


@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}