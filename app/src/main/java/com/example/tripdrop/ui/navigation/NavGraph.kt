package com.example.tripdrop.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tripdrop.DropViewModel
import com.example.tripdrop.ui.presentation.BottomBar
import com.example.tripdrop.ui.presentation.NotificationScreen
import com.example.tripdrop.ui.presentation.PostScreen
import com.example.tripdrop.ui.presentation.UserDataCollectionScreen
import com.example.tripdrop.ui.presentation.authentication.LoginScreen
import com.example.tripdrop.ui.presentation.authentication.SignUpScreen
import com.example.tripdrop.ui.presentation.authentication.WelcomeScreen
import com.example.tripdrop.ui.presentation.home.HomeScreen
import com.example.tripdrop.ui.presentation.home.details.ProductDetailsScreen
import com.example.tripdrop.ui.presentation.home.details.chat.SingleChatScreen
import com.example.tripdrop.ui.presentation.profile.ProfileDetailsScreen
import com.example.tripdrop.ui.presentation.profile.ProfileScreen


@Composable
fun NavGraph(vm: DropViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.WelcomeScreen.route) {
        composable(Route.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(Route.PostScreen.route) {
            PostScreen(vm)
        }
        composable(Route.NotificationScreen.route) {
            NotificationScreen()
        }
        composable(Route.WelcomeScreen.route) {
            WelcomeScreen(navController)
        }
        composable(Route.LoginScreen.route) {
            LoginScreen(navController, vm)
        }
        composable(Route.SignUpScreen.route) {
            SignUpScreen(navController, vm)
        }
        composable(Route.ProfileScreen.route) {
            ProfileScreen(navController, vm)
        }
        composable(Route.UserDataCollectionScreen.route) {
            UserDataCollectionScreen(navController, vm)
        }
        composable(Route.ProductDetailsScreen.route) {
            ProductDetailsScreen(navController)
        }
        composable(Route.SingleChatScreen.route) {
            SingleChatScreen()
        }
        composable(Route.ProfileDetailsScreen.route) {
            ProfileDetailsScreen(navController, vm = vm)
        }
        composable(Route.BottomNav.route) {
            BottomBar(vm)
        }
    }
}
