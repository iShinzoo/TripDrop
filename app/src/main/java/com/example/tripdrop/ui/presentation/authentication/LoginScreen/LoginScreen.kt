package com.example.tripdrop.ui.presentation.authentication.LoginScreen

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ashutosh.fsd.ui.theme.Screen.Authentication.SignIn.Component.Password
import com.example.tripdrop.DropViewModel
import com.example.tripdrop.R
import com.example.tripdrop.ui.navigation.Route
import com.example.tripdrop.ui.presentation.authentication.LoginScreen.Component.TextField
import com.example.tripdrop.ui.theme.h1TextStyle
import com.example.tripdrop.ui.theme.h3TextStyle

@Composable
fun LoginScreen(navController: NavController,vm : DropViewModel) {
    // State variables for email, password, and password visibility
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordHidden by remember { mutableStateOf(true) }
    val context = LocalContext.current


    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.drop))
    val progress by animateLottieCompositionAsState(composition = composition, restartOnPlay = true,
        iterations = LottieConstants.IterateForever)


    val signIn by remember(vm.signIn) { vm.signIn }
    if (signIn) {
        navController.navigate(Route.BottomNav.name)
    }

    BackHandler {
        navController.navigate(Route.WelcomeScreen.name)
    }

    // Root container
    Box(
        modifier = Modifier
            .background(colorResource(id = R.color.white)) // Background color
            .fillMaxSize()
            .verticalScroll(rememberScrollState())// Fill the screen
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
                    fontSize = 35.sp, // Large font size for readability
                    fontWeight = FontWeight.Bold,
                    style = h1TextStyle,
                    modifier = Modifier.align(Alignment.Start) // Align text to start
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Welcome Back, ",
                    color = colorResource(id = R.color.Gray),
                    fontSize = 20.sp,
                    style = h3TextStyle,
                    modifier = Modifier.align(Alignment.Start) // Align text to start
                )
                Text(
                    text = "You have been missed",
                    color = colorResource(id = R.color.Gray),
                    fontSize = 20.sp,
                    style = h3TextStyle,
                    modifier = Modifier
                        .padding(top = 4.dp) // Spacing between texts
                        .align(Alignment.Start) // Align text to start
                )
            }

            LottieAnimation(
                modifier = Modifier.size(300.dp),
                composition = composition,
                progress = {progress})

            // Email Input Field
            email = TextField(icon = Icons.Default.Email, plText = "Enter Your Email" , prefixText = "")

            // Password Input Field
            password = Password(icon = Icons.Default.Lock, plText = "sshh... Keep it Secret" , prefixText = "")

            Spacer(modifier = Modifier.height(8.dp))

            // Forgot Password Text
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 10.dp) ,
                horizontalAlignment = Alignment.End
            ) {

                Text(text = "Forgot Password?" ,
                    color = Color.DarkGray ,
                    fontSize = 14.sp ,
                    modifier = Modifier.clickable {

                    })
            }


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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 10.dp) ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {

                    Text(
                        text = "Don't have an Account?" ,
                        color = Color.DarkGray ,
                        fontSize = 13.sp
                    )

                    Text(text = "Sign Up" ,
                        color = Color.Black ,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .clickable {

                            })
                }
            }
        }
    }
}

