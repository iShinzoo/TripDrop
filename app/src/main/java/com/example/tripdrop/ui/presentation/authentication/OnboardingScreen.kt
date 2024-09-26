package com.example.tripdrop.ui.presentation.authentication

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tripdrop.MainActivity
import com.example.tripdrop.R
import com.example.tripdrop.ui.navigation.Route
import com.example.tripdrop.viewModel.DropViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay


@Composable
fun OnboardingScreen(navController: NavHostController, context: MainActivity,vm:DropViewModel) {

    val systemUiController = rememberSystemUiController()
    val statusBarColor = Color.White
    systemUiController.setStatusBarColor(
        color = statusBarColor,
        darkIcons = true
    )

    val image1Visibility = remember { mutableStateOf(false) }
    val image2Visibility = remember { mutableStateOf(false) }
    val image3Visibility = remember { mutableStateOf(false) }
    val image4Visibility = remember {
        mutableStateOf(false)
    }


    LaunchedEffect(key1 = 0) {
        image1Visibility.value = true
        delay(500)
        image2Visibility.value = true
        delay(500)
        image3Visibility.value = true
        delay(500)
        image4Visibility.value = true
    }


    var alignment by remember {
        mutableStateOf(Alignment.CenterHorizontally)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = alignment
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        ) {

            val density = LocalDensity.current
            val offsetX = with(density) { 225.1.dp.roundToPx() }
            val offsetY = with(density) { -23.05.dp.roundToPx() }


            this@Column.AnimatedVisibility(
                visible = image1Visibility.value,
                enter = fadeIn(animationSpec = tween(durationMillis = 5000)),
                modifier = Modifier
                    .offset { IntOffset(offsetX, offsetY) }
                    .size(width = 254.65.dp, height = 257.49.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ellipse_10),
                    contentDescription = null
                )
            }

            val offsetX2 = with(density) { 115.02.dp.roundToPx() }
            val offsetY2 = with(density) { 143.5.dp.roundToPx() }

            this@Column.AnimatedVisibility(
                visible = image2Visibility.value,
                enter = fadeIn(animationSpec = tween(durationMillis = 5000)),
                modifier = Modifier
                    .offset { IntOffset(offsetX2, offsetY2) }
                    .size(width = 93.8.dp, height = 93.8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ellipse_11),
                    contentDescription = null
                )
            }

            val offsetX3 = with(density) { 161.92.dp.roundToPx() }
            val offsetY3 = with(density) { 84.4.dp.roundToPx() }

            this@Column.AnimatedVisibility(
                visible = image3Visibility.value,
                enter = fadeIn(animationSpec = tween(durationMillis = 5000)),
                modifier = Modifier
                    .offset { IntOffset(offsetX3, offsetY3) }
                    .size(width = 27.18.dp, height = 27.18.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ellipse_13),
                    contentDescription = null
                )
            }

            val offsetX4 = with(density) { 196.61.dp.roundToPx() }
            val offsetY4 = with(density) { 55.12.dp.roundToPx() }

            this@Column.AnimatedVisibility(
                visible = image1Visibility.value,
                enter = fadeIn(animationSpec = tween(durationMillis = 5000)),
                modifier = Modifier
                    .offset { IntOffset(offsetX4, offsetY4) }
                    .size(width = 12.21.dp, height = 12.21.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ellipse_10),
                    contentDescription = null
                )
            }
        }

        var isVisible by remember {
            mutableStateOf(true)
        }

        var clickCount by remember {
            mutableStateOf(0)
        }

        var clickCount2 by remember {
            mutableStateOf(0)
        }


        Spacer(modifier = Modifier.height(10.dp))

        var arr = arrayOf(
            "Post delivery\n" +
                    "requests\n" +
                    "quickly and easily.",
            "Local users " +
                    "accept and\n" +
                    "deliver your items.\n",
            "Simplify deliveries\n" +
                    "with Dropit,\n" +
                    "anytime, anywhere."
        )

        val buttonText = arrayOf("Start", "Next", "Next", "Get Started")

        Box {


            this@Column.AnimatedVisibility(
                visible = clickCount == 0,
                modifier = Modifier
                    .fillMaxWidth(),
//                enter =  fadeIn(),
                exit = fadeOut()
            ) {

                Image(
                    painter = painterResource(id = R.drawable.logodropit),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .width(150.dp),

                    )
            }

            Spacer(modifier = Modifier.padding(150.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 25.dp)
            ) {

                AnimatedVisibility(
                    visible = clickCount > 0,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(
                        text = arr[clickCount - 1],
                        fontSize = 28.sp,
                        lineHeight = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )

                }
                Spacer(modifier = Modifier.height(12.dp))
                AnimatedVisibility(
                    visible = clickCount > 0,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    PageIndicator(pageCount = 3, currentPage = clickCount - 1)
                }
            }
        }

        val width = animateDpAsState(
            targetValue = if (clickCount == 0) 100.dp
            else if (clickCount == 1) 150.dp
            else if (clickCount == 2) 300.dp
            else 400.dp, label = "Animating the width of the button",
            animationSpec = tween(2000)
        )

        Button(
            modifier = Modifier
                .height(60.dp)
                .width(width.value),
            colors = ButtonDefaults.buttonColors(Color(0xFF3068de)),
            onClick = {

                alignment = Alignment.Start
                isVisible = !isVisible
                clickCount2 += 1
                if (clickCount2 <= 3) {
                    clickCount += 1
                }

                if (clickCount2 == 4) {
                    onBoardingIsFinished(context = context)

                    // Check if the user is logged in
                    if (vm.isUserLoggedIn) {
                        navController.popBackStack()
                        navController.navigate(Route.BottomNav.name)
                    } else {
                        navController.popBackStack()
                        navController.navigate(Route.LoginScreen.name) {
                            // Clear the back stack to prevent navigating back to OnboardingScreen
                            popUpTo(Route.OnboardingScreen.name) {
                                inclusive = true
                            }
                        }
                    }
                }
            },
        ) {

            Text(
                text = buttonText[clickCount],
                fontSize = 20.sp
            )

        }
    }
}


private fun onBoardingIsFinished(context: MainActivity) {
    val sharedPreferences = context.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean("isFinished", true)
    editor.apply()

}


@Composable
fun PageIndicator(pageCount: Int, currentPage: Int) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        repeat(pageCount) {
            IndicatorSingleDot(isSelected = it == currentPage)
        }


    }
}

@Composable
fun IndicatorSingleDot(isSelected: Boolean) {

    val width = animateDpAsState(targetValue = 14.dp, label = "")
    Box(
        modifier = Modifier
            .padding(4.dp)
            .height(14.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(if (isSelected) Color(0xFF3068de) else Color.LightGray)
    )
}