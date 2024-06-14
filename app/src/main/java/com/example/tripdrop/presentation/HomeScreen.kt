package com.example.tripdrop.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tripdrop.ui.theme.TripDropTheme

@Composable
fun HomeScreen(navController: NavController){

}

@Preview
@Composable
fun HomeScreenPreview(){
    TripDropTheme {
        HomeScreen(navController = rememberNavController())
    }
}