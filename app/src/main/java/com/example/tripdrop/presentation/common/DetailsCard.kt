package com.example.tripdrop.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tripdrop.R


@Composable
fun DetailsCard(
//    onClick : () -> Unit
){

    Card(
//        onClick = { onClick() },
        modifier = Modifier.background(color = colorResource(id = R.color.white))
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = colorResource(id = R.color.white),
            modifier = Modifier
                .height(210.dp)
                .padding(10.dp),
            shadowElevation = 10.dp
        ) {

            Row (
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ){

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(2f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Delivery",
                        fontSize =  24.sp,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Price : 300$")


                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier.wrapContentSize(),
                        color = Color(0xFFD1D5E1)
                    ) {
                        Text(
                            text = "New release",
                            fontSize =  12.sp,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp)
                        )
                    }

                }


                Surface(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.size(width = 140.dp, height = 140.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.friend_delivers),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                }
            }

        }
    }

}


