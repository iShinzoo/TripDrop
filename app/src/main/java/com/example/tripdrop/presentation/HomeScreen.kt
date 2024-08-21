package com.example.tripdrop.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tripdrop.R

@Composable
fun HomeScreen(){
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
                text = "HomeScreen",
                modifier = Modifier
                    .padding(top = 120.dp)
                    .width(320.dp),
                color = colorResource(id = R.color.MainBackground),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}