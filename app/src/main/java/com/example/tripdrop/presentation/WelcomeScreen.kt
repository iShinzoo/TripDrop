package com.example.tripdrop.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tripdrop.R

@Composable
fun WelcomeScreen(){

    Box(modifier = Modifier
        .fillMaxWidth()
        .background(colorResource(id = R.color.MainBackground))
    ){
     Column(
        modifier = Modifier.fillMaxWidth(),
         verticalArrangement = Arrangement.Center,
         horizontalAlignment = Alignment.CenterHorizontally
     ) {
         Text(text = "Welcome To",
             modifier = Modifier.padding(top = 120.dp),
             color = colorResource(id = R.color.white),
             fontSize = 32.sp,
             fontWeight = FontWeight.Bold
             )

         Spacer(modifier = Modifier.height(80.dp))

         Text(text = "Trip",
             modifier = Modifier.padding(end = 36.dp),
             color = colorResource(id = R.color.black),
             fontSize = 42.sp,
             fontWeight = FontWeight.Bold
         )

         Text(text = "Drop",
             modifier = Modifier.padding(top = 6.dp),
             color = colorResource(id = R.color.black),
             fontSize = 52.sp,
             fontWeight = FontWeight.Bold
         )

         Spacer(modifier = Modifier.height(200.dp))

         ElevatedButton(
             onClick = {

             },
             modifier = Modifier
                 .width(284.dp)
                 .height(52.dp),
             colors = ButtonDefaults.elevatedButtonColors(colorResource(id = R.color.black))
         ) {
             Text(
                 text = "Sign Up",
                 modifier = Modifier,
                 color = Color.White,
                 fontSize = 22.sp,
                 fontWeight = FontWeight.Bold
             )
         }


         Text(text = "Already have an account ? Login",
             modifier = Modifier.padding(top = 6.dp),
             color = colorResource(id = R.color.white),
             fontSize = 16.sp,
             fontWeight = FontWeight.Bold
         )





     }
    }

}

@Preview
@Composable
fun WelcomeScreenPreview(){
    WelcomeScreen()
}