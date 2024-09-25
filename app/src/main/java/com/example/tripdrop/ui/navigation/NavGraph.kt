package com.example.tripdrop.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.tripdrop.ChatViewModel
import com.example.tripdrop.DropViewModel
import com.example.tripdrop.NotificationViewModel
import com.example.tripdrop.ui.presentation.BottomBar
import com.example.tripdrop.ui.presentation.NotificationScreen
import com.example.tripdrop.ui.presentation.profile.child.PaymentScreen
import com.example.tripdrop.ui.presentation.profile.child.YourOrdersScreen
import com.example.tripdrop.ui.presentation.post.PostScreen
import com.example.tripdrop.ui.presentation.authentication.UserDataCollectionScreen
import com.example.tripdrop.ui.presentation.authentication.LoginScreen
import com.example.tripdrop.ui.presentation.authentication.SignUpScreen
import com.example.tripdrop.ui.presentation.authentication.WelcomeScreen
import com.example.tripdrop.ui.presentation.home.HomeScreen
import com.example.tripdrop.ui.presentation.home.details.ProductDetailsScreen
import com.example.tripdrop.ui.presentation.home.details.chat.SingleChatScreen
import com.example.tripdrop.ui.presentation.post.profile.child.FeedbackFormScreen
import com.example.tripdrop.ui.presentation.post.profile.child.HelpScreen
import com.example.tripdrop.ui.presentation.post.profile.child.PolicyScreen
import com.example.tripdrop.ui.presentation.profile.child.ProfileDetailsScreen
import com.example.tripdrop.ui.presentation.profile.ProfileScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(vm: DropViewModel, chatViewModel: ChatViewModel, nm: NotificationViewModel) {
    val navController = rememberNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = Route.WelcomeScreen.name,
        enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut() },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn() },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut() }
    ) {
        composable(Route.HomeScreen.name) {
            HomeScreen(navController, vm)
        }
        composable(Route.PostScreen.name) {
            PostScreen(vm,navController)
        }
        composable(
            Route.NotificationScreen.name,
            deepLinks = listOf(navDeepLink { uriPattern = "myapp://notification" })
        ) {
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
        composable(Route.ProductDetailScreen.name + "/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            productId?.let {
                ProductDetailsScreen(
                    vm = vm,
                    productId = it,
                    navController = navController,
                    nm = nm,
                    chatModel = chatViewModel
                )
            }
        }
        composable(
            route = "${Route.SingleChatScreen.name}/{chatId}",
            arguments = listOf(navArgument("chatId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId")
            chatId?.let {
                SingleChatScreen(
                    navController = navController,
                    chatModel = chatViewModel,
                    chatId = chatId
                )
            }
        }
        composable(Route.ProfileDetailScreen.name) {
            ProfileDetailsScreen(navController, vm = vm)
        }
        composable(Route.BottomNav.name) {
            BottomBar(vm, chatViewModel, nm)
        }
        composable(Route.YoursOrderScreen.name) {
            YourOrdersScreen(navController, vm = vm)
        }
        composable(Route.PaymentScreen.name) {
            PaymentScreen()
        }
        composable(Route.PolicyScreen.name) {
            PolicyScreen()
        }
        composable(Route.FeedbackScreen.name) {
            FeedbackFormScreen()
        }
        composable(Route.HelpScreen.name) {
            HelpScreen()
        }
    }
}
