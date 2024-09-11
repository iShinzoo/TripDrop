package com.example.tripdrop.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tripdrop.MainActivity
import com.example.tripdrop.presentation.BottomBar
import com.example.tripdrop.presentation.LoginScreen
import com.example.tripdrop.presentation.ProductDetailsScreen
import com.example.tripdrop.presentation.SignUpScreen
import com.example.tripdrop.presentation.WelcomeScreen


@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.WelcomeScreen.route ) {

        composable(Route.WelcomeScreen.route){
            WelcomeScreen(navController)
        }

        composable(Route.LoginScreen.route){
            LoginScreen(navController)
        }

        composable(Route.SignUpScreen.route){
            SignUpScreen(navController)
        }

        composable(Route.ProductDetailsScreen.route){
            ProductDetailsScreen(navController)
        }

        composable(Route.Home.route){
            MainActivity()
        }

        composable(Route.BottomNav.route){
            BottomBar()
        }
    }
}