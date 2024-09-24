package com.example.tripdrop.ui.presentation

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.tripdrop.ChatViewModel
import com.example.tripdrop.DropViewModel
import com.example.tripdrop.NotificationViewModel
import com.example.tripdrop.R
import com.example.tripdrop.ui.navigation.Route
import com.example.tripdrop.ui.presentation.authentication.LoginScreen
import com.example.tripdrop.ui.presentation.authentication.SignUpScreen
import com.example.tripdrop.ui.presentation.authentication.UserDataCollectionScreen
import com.example.tripdrop.ui.presentation.authentication.WelcomeScreen
import com.example.tripdrop.ui.presentation.home.HomeScreen
import com.example.tripdrop.ui.presentation.home.details.ProductDetailsScreen
import com.example.tripdrop.ui.presentation.home.details.chat.SingleChatScreen
import com.example.tripdrop.ui.presentation.post.PostScreen
import com.example.tripdrop.ui.presentation.profile.FeedbackFormScreen
import com.example.tripdrop.ui.presentation.profile.HelpScreen
import com.example.tripdrop.ui.presentation.profile.PaymentScreen
import com.example.tripdrop.ui.presentation.profile.PolicyScreen
import com.example.tripdrop.ui.presentation.profile.child.ProfileDetailsScreen
import com.example.tripdrop.ui.presentation.profile.ProfileScreen
import com.example.tripdrop.ui.presentation.profile.YourOrdersScreen
import com.example.tripdrop.ui.theme.bgwhite

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
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Route.HomeScreen.name) {
                HomeScreen(navController, vm)
            }
            composable(Route.PostScreen.name) {
                PostScreen(vm)
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
                route = Route.SingleChatScreen.name + "/{chatId}",
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
                        navController.navigate(item.name) {
                            // Navigate to the destination and pop up to start destination
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    alwaysShowLabel = false,
                    icon = {
                        Icon(
                            modifier = Modifier.size(27.dp),
                            imageVector = item.icon,
                            tint = if (isSelected) colorResource(id = R.color.black) else Color.Gray,
                            contentDescription = item.title
                        )
                    }
                )
            }
        }
    }
}

data class BottomNavItem(val title: String, val name: String, val icon: ImageVector)