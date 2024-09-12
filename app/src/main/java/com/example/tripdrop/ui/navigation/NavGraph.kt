package com.example.tripdrop.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tripdrop.DropViewModel
import com.example.tripdrop.MainActivity
import com.example.tripdrop.ui.presentation.BottomBar
import com.example.tripdrop.ui.presentation.authentication.LoginScreen
import com.example.tripdrop.ui.presentation.authentication.SignUpScreen
import com.example.tripdrop.ui.presentation.authentication.WelcomeScreen
import com.example.tripdrop.ui.presentation.home.HomeScreen
import com.example.tripdrop.ui.presentation.home.details.ProductDetailsScreen


@Composable
fun NavGraph(vm: DropViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.WelcomeScreen.route) {

        composable(Route.WelcomeScreen.route) {
            WelcomeScreen(navController)
        }

        composable(Route.LoginScreen.route) {
            LoginScreen(navController, vm)
        }

        composable(Route.SignUpScreen.route) {
            SignUpScreen(navController, vm)
        }

        composable(Route.ProductDetailsScreen.route) {
            ProductDetailsScreen(navController)
        }

        composable(Route.Home.route) {
            MainActivity()
        }

        composable(Route.BottomNav.route) {
            BottomBar(vm = vm)
        }
    }
}