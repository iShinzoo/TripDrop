package com.example.tripdrop.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tripdrop.R


@Composable
fun LoginScreen() {

    // remember keyword is used to store/remember the value in case recompose is called
    // mutableStateOf() is used to state that the value can be changed

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordHidden by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier.background(colorResource(id = R.color.white))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column {
                Text(
                    text = "Welcome Back",
                    modifier = Modifier
                        .width(320.dp)
                        .align(Alignment.Start),
                    color = colorResource(id = R.color.HighlightText),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Enter your email and password to log in",
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .width(320.dp)
                        .align(Alignment.Start),
                    color = colorResource(id = R.color.HighlightText),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(50.dp))

            OutlinedTextField(
                modifier = Modifier
                    .width(320.dp)
                    .background(colorResource(id = R.color.white)),

                value = email,

                onValueChange = { email = it },

                leadingIcon = {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = null,
                        tint = colorResource(id = R.color.HighlightText)
                    )
                },

                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                ),

                placeholder = {
                    Text(
                        text = "Email", color = colorResource(id = R.color.HighlightText)
                    )
                },

                singleLine = true
            )

            Spacer(modifier = Modifier.height(18.dp))

            OutlinedTextField(
                modifier = Modifier
                    .width(320.dp)
                    .background(colorResource(id = R.color.white)),

                value = password,

                onValueChange = { password = it },

                placeholder = {
                    Text(
                        text = "Password", color = colorResource(id = R.color.HighlightText)
                    )
                },
                singleLine = true,

                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = null,
                        tint = colorResource(id = R.color.HighlightText)
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
                            tint = colorResource(id = R.color.HighlightText)
                        )
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None
                // visual Transformation is used for formatting and transforming Text field input
            )
            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = {
//                    navController.navigate(route = Route.SignupScreen.route)
                },
                modifier = Modifier
                    .width(320.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(colorResource(id = R.color.HighlightText))
            ) {
                Text(
                    text = "Login",
                    modifier = Modifier,
                    color = colorResource(id = R.color.white),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
//                    navController.navigate(route = Route.SignupScreen.route)
                },
                modifier = Modifier
                    .width(320.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(colorResource(id = R.color.ButtonsBackground))
            ) {
                Text(
                    text = "Don't have an Account? SignUP",
                    modifier = Modifier,
                    color = colorResource(id = R.color.white),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "-------------------OR---------------------",
                modifier = Modifier,
                color = colorResource(id = R.color.HighlightText),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
//                    navController.navigate(route = Route.SignupScreen.route)
                },
                modifier = Modifier
                    .width(320.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(colorResource(id = R.color.HighlightText))

            ) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = null,
                    modifier = Modifier
                        .size(22.dp)
                        .padding(end = 6.dp),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = "LogIn with Google",
                    modifier = Modifier,
                    color = colorResource(id = R.color.white),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
//                    navController.navigate(route = Route.SignupScreen.route)
                },
                modifier = Modifier
                    .width(320.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(colorResource(id = R.color.ButtonsBackground))

            ) {
                Image(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = null,
                    modifier = Modifier
                        .size(22.dp)
                        .padding(end = 6.dp),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = "LogIn with Facebook",
                    modifier = Modifier,
                    color = colorResource(id = R.color.white),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

}


@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}