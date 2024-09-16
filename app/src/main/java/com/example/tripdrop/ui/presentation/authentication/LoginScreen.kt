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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tripdrop.DropViewModel
import com.example.tripdrop.R
import com.example.tripdrop.ui.navigation.Route
import com.example.tripdrop.ui.presentation.buttonHeight
import com.example.tripdrop.ui.presentation.largeTextSize
import com.example.tripdrop.ui.presentation.mediumTextSize
import com.example.tripdrop.ui.presentation.roundedCornerSize
import com.example.tripdrop.ui.presentation.smallTextSize
import com.example.tripdrop.ui.presentation.standardPadding

@Composable
fun LoginScreen(navController: NavController, vm: DropViewModel) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    val signIn by remember(vm.signIn) { vm.signIn }

    if (signIn) {
        navController.navigate(Route.BottomNav.route) {
            popUpTo(Route.LoginScreen.route) { inclusive = true }
        }
    }

    BackHandler { navController.navigate(Route.WelcomeScreen.route) }

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
            LoginHeaderText()
            Spacer(modifier = Modifier.height(32.dp))
            EmailInputField(email) { email = it }
            Spacer(modifier = Modifier.height(16.dp))
            PasswordInputField(
                password,
                passwordHidden,
                onPasswordChange = { password = it },
                onVisibilityToggle = { passwordHidden = !passwordHidden })
            Spacer(modifier = Modifier.height(8.dp))
            ForgotPasswordText()
            Spacer(modifier = Modifier.height(16.dp))
            SignInButton(email, password, vm, navController)
            Spacer(modifier = Modifier.height(16.dp))
            DividerText()
            Spacer(modifier = Modifier.height(16.dp))
            SocialMediaIcons()
            Spacer(modifier = Modifier.height(16.dp))
            SignUpText(navController)
        }
    }
}

// Header composable for the title text
@Composable
fun LoginHeaderText() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Let's Sign you in",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = largeTextSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = "Welcome Back, ",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = mediumTextSize,
            modifier = Modifier.align(Alignment.Start)
        )
        Text(
            text = "You have been missed",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = mediumTextSize,
            modifier = Modifier
                .padding(top = 4.dp)
                .align(Alignment.Start)
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

// Forgot password text
@Composable
fun ForgotPasswordText() {
    Column {
        Text(
            text = "Forgot Password?",
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 8.dp),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = smallTextSize,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SignInButton(
    email: String,
    password: String,
    vm: DropViewModel,
    navController: NavController
) {
    val context = LocalContext.current  // Use LocalContext.current directly

    Button(
        onClick = { vm.login(email, password, context, navController) },
        modifier = Modifier
            .fillMaxWidth()
            .height(buttonHeight)
            .clip(RoundedCornerShape(roundedCornerSize)),
        colors = ButtonDefaults.buttonColors(Color.Black)
    ) {
        Text(
            text = "Sign In",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = smallTextSize,
            fontWeight = FontWeight.Bold
        )
    }
}


// Divider text
@Composable
fun DividerText() {
    Text(
        text = "------------------ OR --------------------",
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = smallTextSize,
        fontWeight = FontWeight.Bold
    )
}

// Social media icons
@Composable
fun SocialMediaIcons() {
    Row(horizontalArrangement = Arrangement.Center) {
        Icon(
            painter = painterResource(id = R.drawable.google),
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            painter = painterResource(id = R.drawable.facebook),
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = Color.Unspecified
        )
    }
}

// Sign-up text
@Composable
fun SignUpText(navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Don't have an Account?",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = smallTextSize
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Sign Up",
            modifier = Modifier.clickable { navController.navigate(route = Route.SignUpScreen.route) },
            color = Color.Black,
            fontSize = smallTextSize,
            fontWeight = FontWeight.Bold
        )
    }
}
