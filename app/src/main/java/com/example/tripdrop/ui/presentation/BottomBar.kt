package com.example.tripdrop.ui.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.AddBox
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.tripdrop.R
import com.example.tripdrop.ui.navigation.Route
import com.example.tripdrop.ui.presentation.authentication.LoginScreen
import com.example.tripdrop.ui.presentation.authentication.SignUpScreen
import com.example.tripdrop.ui.presentation.authentication.UserDataCollectionScreen
import com.example.tripdrop.ui.presentation.home.HomeScreen
import com.example.tripdrop.ui.presentation.home.details.ProductDetailsScreen
import com.example.tripdrop.ui.presentation.home.details.chat.SingleChatScreen
import com.example.tripdrop.ui.presentation.post.PostScreen
import com.example.tripdrop.ui.presentation.profile.child.PolicyScreen
import com.example.tripdrop.ui.presentation.profile.ProfileScreen
import com.example.tripdrop.ui.presentation.profile.child.HelpScreen
import com.example.tripdrop.ui.presentation.profile.child.PaymentScreen
import com.example.tripdrop.ui.presentation.profile.child.ProfileDetailsScreen
import com.example.tripdrop.ui.presentation.profile.child.TermsAndConditionsScreen
import com.example.tripdrop.ui.presentation.profile.child.YourOrdersScreen
import com.example.tripdrop.viewModel.ChatViewModel
import com.example.tripdrop.viewModel.DropViewModel
import com.example.tripdrop.viewModel.NotificationViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomBar(vm: DropViewModel, chatViewModel: ChatViewModel, nm: NotificationViewModel) {
    val navController = rememberNavController()

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            if (currentRoute in listOf(
                    Route.HomeScreen.name,
                    Route.PostScreen.name,
                    Route.NotificationScreen.name,
                    Route.ProfileScreen.name
                )
            ) {
                MyBottomBar(navController)
            }
        }
    ) { innerPadding ->
        AnimatedNavHost(
            navController = navController,
            startDestination = Route.HomeScreen.name,
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut() },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn() },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut() },
            modifier = Modifier.padding(innerPadding)
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
                TermsAndConditionsScreen()
            }
            composable(Route.HelpScreen.name) {
                HelpScreen()
            }
            composable(Route.FavouriteScreen.name) {
                FavoritesScreen(navController, vm = vm)
            }
        }
    }
}

@Composable
fun MyBottomBar(navController: NavHostController) {
    // Data class to define bottom navigation items
    val navItems = listOf(
        BottomNavItem("Home", Route.HomeScreen.name, Icons.Rounded.Home),
        BottomNavItem("Post", Route.PostScreen.name, Icons.Rounded.AddBox),
        BottomNavItem("Notification", Route.NotificationScreen.name, Icons.Rounded.Notifications),
        BottomNavItem("Profile", Route.ProfileScreen.name, Icons.Rounded.AccountCircle)
    )

    ElevatedCard(
        modifier = Modifier
            .padding(bottom = 20.dp, start = 20.dp, end = 20.dp)
            .height(60.dp),
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        BottomAppBar(
            containerColor = Color.White, // Consistent with Material 3
            tonalElevation = 10.dp
        ) {
            // Loop through navigation items and create a NavigationBarItem for each
            navItems.forEach { item ->
                val isSelected =
                    item.name == navController.currentBackStackEntry?.destination?.route
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        // Navigate only if the item clicked is different from the current item
                        if (!isSelected) {
                            navController.navigate(item.name) {
                                // Navigate to the destination and pop up to start destination
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    alwaysShowLabel = false,
                    icon = {
                        val tintColor = if (isSelected) colorResource(id = R.color.black) else Color.Gray

                        // Change tint color briefly for visual feedback on click
                        Icon(
                            modifier = Modifier
                                .size(27.dp),
                            imageVector = item.icon,
                            tint = tintColor,
                            contentDescription = item.title
                        )
                    }
                )
            }
        }
    }
}

data class BottomNavItem(val title: String, val name: String, val icon: ImageVector)