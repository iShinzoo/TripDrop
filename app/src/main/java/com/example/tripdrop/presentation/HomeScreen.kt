package com.example.tripdrop.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tripdrop.R
import com.example.tripdrop.navigation.Route
import com.example.tripdrop.presentation.common.DetailsCard
import com.example.tripdrop.ui.theme.TripDropTheme

@Composable
fun HomeScreen(navController: NavController){
    Column (
        modifier = Modifier.fillMaxSize()
    ){
        TopAppbar(navController = navController)
        DetailsCard()
        DetailsCard()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppbar(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "TripDrop",
                    color = colorResource(id = R.color.white),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            actions = {
                IconButton(onClick = {
                    navController.navigate(Route.NotificationScreen.route)
                }) {
                    Icon(
                        Icons.Default.Notifications,
                        contentDescription = "Localized description",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(R.color.safeGreen))
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    TripDropTheme {
        HomeScreen(navController = rememberNavController())
    }
}