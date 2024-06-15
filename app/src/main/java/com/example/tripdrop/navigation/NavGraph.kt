package com.example.tripdrop.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tripdrop.navigator.BottomBar
import com.example.tripdrop.presentation.HomeScreen


@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.TripDropNavigatorScreen.route) {

        composable(
            route = Route.HomeScreen.route
        ) {
            HomeScreen(navController = navController)
        }

        composable(
            route = Route.TripDropNavigatorScreen.route
        ) {
            BottomBar()
        }

    }
}