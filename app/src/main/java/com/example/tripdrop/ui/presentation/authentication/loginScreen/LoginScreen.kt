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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.tripdrop.ui.presentation.authentication.loginScreen.component.Password
import com.example.tripdrop.DropViewModel
import com.example.tripdrop.R
import com.example.tripdrop.ui.navigation.Route
import com.example.tripdrop.ui.presentation.authentication.loginScreen.component.TextField
import com.example.tripdrop.ui.presentation.common.buttonHeight
import com.example.tripdrop.ui.presentation.common.roundedCornerSize
import com.example.tripdrop.ui.presentation.common.smallTextSize
import com.example.tripdrop.ui.presentation.common.standardPadding
import com.example.tripdrop.ui.theme.h1TextStyle
import com.example.tripdrop.ui.theme.h2TextStyle
import com.example.tripdrop.ui.theme.h3TextStyle


@Composable
fun LoginScreen(
    navController: NavController, vm: DropViewModel
) {
    val context = LocalContext.current
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    val signIn by remember(vm.signIn) { vm.signIn }

    if (signIn) {
        navController.navigate(Route.BottomNav.name) {
            popUpTo(Route.LoginScreen.name) { inclusive = true }
        }
    }

    BackHandler { navController.navigate(Route.WelcomeScreen.name) }

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(standardPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            LoginHeaderText()
            LottieAnimationLoginPage()
            email =
                TextField(icon = Icons.Default.Email, plText = "Enter Your Email", prefixText = "")
            password = Password(
                icon = Icons.Default.Lock, plText = "sshhh... Keep it Secret!!!", prefixText = ""
            )
            Spacer(modifier = Modifier.height(8.dp))
            ForgotPasswordText(vm)
            Spacer(modifier = Modifier.height(16.dp))
            SignInButton(email, password, vm, navController)
            Spacer(modifier = Modifier.height(16.dp))
            DividerText()
            Spacer(modifier = Modifier.height(16.dp))
            SocialMediaIcons()
            Spacer(modifier = Modifier.height(16.dp))
            SignUpText(navController)
        }


        if (vm.isDialogShow) {
            SendResetPasswordDialogBox(onDismiss = { vm.dismissDialog() }, onConfirm = {
                vm.resetPassword(it, context)

            })
        }

    }
}

@Composable
fun LottieAnimationLoginPage() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.drop))
    val progress by animateLottieCompositionAsState(
        composition = composition, restartOnPlay = true, iterations = LottieConstants.IterateForever
    )

    LottieAnimation(modifier = Modifier.size(300.dp),
        composition = composition,
        progress = { progress })

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
            fontSize = 35.sp,
            style = h1TextStyle,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = "Welcome Back, ",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp,
            style = h3TextStyle,
            modifier = Modifier.align(Alignment.Start)
        )
        Text(
            text = "You have been missed",
            color = MaterialTheme.colorScheme.onBackground,
            style = h3TextStyle,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(top = 4.dp)
                .align(Alignment.Start)
        )
    }
}


// Forgot password text
@Composable
fun ForgotPasswordText(vm: DropViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.End
    ) {

        Text(text = "Forgot Password ?",
            color = Color.DarkGray,
            fontSize = 15.sp,
            modifier = Modifier.clickable {
                vm.displayDialog()
            })
    }
}

@Composable
fun SendResetPasswordDialogBox(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {

    Dialog(
        onDismissRequest = onDismiss, properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        val context = LocalContext.current

        Box(
            Modifier
                .padding(horizontal = 18.dp)
                .wrapContentHeight()
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(color = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(horizontal = 12.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Enter Your Email",
                    color = Color.Black,
                    style = h2TextStyle,
                    modifier = Modifier
                )
                val userEmail = TextField(
                    icon = Icons.Filled.AccountCircle, plText = "xyz@gmail.com", prefixText = ""
                )
                Button(
                    onClick = {
                        if (userEmail.isEmpty()) {
                            Toast.makeText(
                                context, "Enter a valid email address", Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            onConfirm(userEmail.trim())
                            onDismiss()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black, contentColor = Color.White
                    )
                ) {
                    Text("Submit")
                }
            }
        }
    }
}


@Composable
fun SignInButton(
    email: String, password: String, vm: DropViewModel, navController: NavController
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
        text = "---------- OR ----------",
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {

            Text(
                text = "Don't have an Account?", color = Color.DarkGray, fontSize = 15.sp
            )

            Text(text = "Sign Up",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .clickable {
                        navController.navigate(Route.SignUpScreen.name)
                    })
        }
    }
}



