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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tripdrop.DropViewModel
import com.example.tripdrop.ui.navigation.Route
import com.example.tripdrop.ui.presentation.CheckUserSignedIn
import com.example.tripdrop.ui.presentation.buttonHeight
import com.example.tripdrop.ui.presentation.largeTextSize
import com.example.tripdrop.ui.presentation.smallTextSize
import com.example.tripdrop.ui.presentation.standardPadding

@Composable
fun SignUpScreen(navController: NavController, vm: DropViewModel) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordHidden by remember { mutableStateOf(true) }

    BackHandler { navController.navigate(Route.WelcomeScreen.route) }
    CheckUserSignedIn(vm = vm, navController = navController)

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(standardPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Header Section
            SignUpHeader()

            Spacer(modifier = Modifier.height(32.dp))

            EmailInputField(email) { email = it }
            Spacer(modifier = Modifier.height(16.dp))
            PasswordInputField(
                password,
                passwordHidden,
                onPasswordChange = { password = it },
                onVisibilityToggle = { passwordHidden = !passwordHidden })

            Spacer(modifier = Modifier.height(22.dp))

            // Sign Up Button
            SignUpButton {
                vm.signUp(email, password, navController, context)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Navigation to Sign In
            SignInNavigation(navController)
        }
    }
}

// Reusable header composable for sign-up
@Composable
fun SignUpHeader() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Let's Register",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = largeTextSize),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Account",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = largeTextSize),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = "Hello Champ, hope you",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = smallTextSize)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "have a grateful journey",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = smallTextSize)
        )
    }
}

// Email input field composable
@Composable
private fun EmailInputField(email: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = email,
        onValueChange = onValueChange,
        placeholder = { Text(text = "Email", color = MaterialTheme.colorScheme.onSurfaceVariant) },
        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.LightGray,
            cursorColor = Color.Black
        )
    )
}

// Password input field with visibility toggle
@Composable
private fun PasswordInputField(
    password: String,
    passwordHidden: Boolean,
    onPasswordChange: (String) -> Unit,
    onVisibilityToggle: () -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = password,
        onValueChange = onPasswordChange,
        placeholder = {
            Text(
                text = "Password",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
        trailingIcon = {
            IconButton(onClick = onVisibilityToggle) {
                val visibilityIcon =
                    if (passwordHidden) Icons.Default.VisibilityOff else Icons.Default.Visibility
                Icon(imageVector = visibilityIcon, contentDescription = null)
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.LightGray,
            cursorColor = Color.Black
        )
    )
}

// Sign Up button with rounded corners
@Composable
fun SignUpButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(buttonHeight)
            .clip(RoundedCornerShape(12.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
    ) {
        Text(
            text = "Sign Up",
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = smallTextSize
        )
    }
}

// Reusable composable for sign-in navigation
@Composable
fun SignInNavigation(navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Already have an Account?",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = smallTextSize)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = "Sign In",
            modifier = Modifier.clickable {
                navController.navigate(Route.LoginScreen.route)
            },
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = smallTextSize,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
