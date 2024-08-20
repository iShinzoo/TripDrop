package com.example.tripdrop.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tripdrop.R

@Composable
fun WelcomeScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {


        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Welcome to Trip Drop",
                modifier = Modifier
                    .padding(top = 120.dp)
                    .width(320.dp),
                color = colorResource(id = R.color.MainBackground),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )


            Text(
                text = " Get your essentials delivered in no time.",
                modifier = Modifier.width(320.dp),
                color = colorResource(id = R.color.MainBackground),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(200.dp))

//            Image(
//                painter = painterResource(id = R.drawable.welcome),
//                contentDescription = null,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 60.dp),
//                contentScale = ContentScale.Fit
//            )


            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
//                    navController.navigate(route = Route.LoginScreen.route)
                },
                modifier = Modifier
                    .width(320.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.HighlightText))
            ) {
                Text(
                    text = "Log In",
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
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.ButtonsBackground))

            ) {
                Text(
                    text = "Don't have an Account? SignUP",
                    modifier = Modifier,
                    color = colorResource(id = R.color.white),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }

    }

}

@Preview
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen()
}