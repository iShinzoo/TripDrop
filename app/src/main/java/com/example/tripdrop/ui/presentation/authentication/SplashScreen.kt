package com.example.tripdrop.ui.presentation.authentication

import android.content.Context
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tripdrop.MainActivity
import com.example.tripdrop.R
import com.example.tripdrop.ui.navigation.Route
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavHostController , context: MainActivity) {

    val systemUiController = rememberSystemUiController()
    val statusBarColor = Color(0xFFfb7c1f)
    systemUiController.setStatusBarColor(
        color = statusBarColor,
        darkIcons = false
    )

    val alpha = remember {
        Animatable(0f)
    }




    LaunchedEffect(key1 = true) {
        alpha.animateTo(1f,
            animationSpec = tween(300)
        )
        delay(1000)

        if (onBoardingIsFinished(context)){
            navController.popBackStack()
            navController.navigate(Route.HomeScreen.name)
        }
        else{
            navController.popBackStack()
            navController.navigate(Route.OnboardingScreen.name)
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFfb7c1f) ,
                        Color(0xFFfb7c1f)
                    ) ,
                    start = Offset(0.0979f , 0f) ,
                    end = Offset(0.2064f , 0f)
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

            Image(
                painter = painterResource(id = R.drawable.logodropit) ,
                contentDescription = null ,
                contentScale = ContentScale.Fit ,
                modifier = Modifier
                    .width(150.dp),

                )


        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Facilitate your delivery of goods",
            modifier = Modifier.alpha(alpha.value),
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF2c53a7)
        )
    }
}

private fun onBoardingIsFinished(context: MainActivity): Boolean {
    val sharedPreferences = context.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("isFinished", false)

}
