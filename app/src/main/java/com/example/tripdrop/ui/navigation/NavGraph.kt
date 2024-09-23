package com.example.tripdrop.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tripdrop.ChatViewModel
import com.example.tripdrop.DropViewModel
import com.example.tripdrop.NotificationViewModel
import com.example.tripdrop.ui.presentation.BottomBar
import com.example.tripdrop.ui.presentation.NotificationScreen
import com.example.tripdrop.ui.presentation.post.PostScreen
import com.example.tripdrop.ui.presentation.authentication.UserDataCollectionScreen
import com.example.tripdrop.ui.presentation.authentication.LoginScreen
import com.example.tripdrop.ui.presentation.authentication.SignUpScreen
import com.example.tripdrop.ui.presentation.authentication.WelcomeScreen
import com.example.tripdrop.ui.presentation.home.HomeScreen
import com.example.tripdrop.ui.presentation.home.details.ProductDetailsScreen
import com.example.tripdrop.ui.presentation.home.details.chat.SingleChatScreen
import com.example.tripdrop.ui.presentation.profile.ProfileDetailsScreen
import com.example.tripdrop.ui.presentation.profile.ProfileScreen


@Composable
fun NavGraph(vm: DropViewModel,chatViewModel : ChatViewModel,nm : NotificationViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.WelcomeScreen.name) {
        composable(Route.HomeScreen.name) {
            HomeScreen(navController,vm)
        }
        composable(Route.PostScreen.name) {
            PostScreen(vm)
        }
        composable(Route.NotificationScreen.name) {
            NotificationScreen()
        }
        composable(Route.WelcomeScreen.name) {
            WelcomeScreen(navController)
        }
        composable(Route.LoginScreen.name) {
            LoginScreen(navController, vm)
        }
        composable(Route.SignUpScreen.name) {
            SignUpScreen(navController, vm)
        }
        composable(Route.ProfileScreen.name) {
            ProfileScreen(navController, vm)
        }
        composable(Route.UserDataCollectionScreen.name) {
            UserDataCollectionScreen(navController, vm)
        }
        composable("productDetailsScreen/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            productId?.let {
                ProductDetailsScreen(vm = vm, productId = it, navController = navController, nm = nm)
            }
        }
        composable(
            route = "singleChatScreen/{chatId}",
            arguments = listOf(navArgument("chatId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId")
            chatId?.let {
                SingleChatScreen(navController = navController, chatModel = chatViewModel, chatId = chatId)
            }
        }
        composable(Route.ProfileDetailScreen.name) {
            ProfileDetailsScreen(navController, vm = vm)
        }
        composable(Route.BottomNav.name) {
            BottomBar(vm,chatViewModel,nm)
        }
    }
}
