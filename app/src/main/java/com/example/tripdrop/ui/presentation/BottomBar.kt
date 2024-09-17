package com.example.tripdrop.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.tripdrop.DropViewModel
import com.example.tripdrop.R
import com.example.tripdrop.ui.navigation.Route
import com.example.tripdrop.ui.presentation.authentication.LoginScreen
import com.example.tripdrop.ui.presentation.authentication.SignUpScreen
import com.example.tripdrop.ui.presentation.authentication.WelcomeScreen
import com.example.tripdrop.ui.presentation.home.HomeScreen
import com.example.tripdrop.ui.presentation.home.details.ProductDetailsScreen
import com.example.tripdrop.ui.presentation.home.details.chat.SingleChatScreen
import com.example.tripdrop.ui.presentation.profile.ProfileDetailsScreen
import com.example.tripdrop.ui.presentation.profile.ProfileScreen
import com.example.tripdrop.ui.theme.bgwhite

@Composable
fun BottomBar(vm: DropViewModel) {
    val navController = rememberNavController()

    Scaffold(
        containerColor = bgwhite,
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            if (currentRoute in listOf(
                    Route.HomeScreen.route,
                    Route.PostScreen.route,
                    Route.NotificationScreen.route,
                    Route.ProfileScreen.route
                )
            ) {
                MyBottomBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Route.HomeScreen.route) {
                HomeScreen(navController,vm)
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
            composable("productDetailsScreen/{productId}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId")
                productId?.let {
                    ProductDetailsScreen(vm = vm, productId = it, navController = navController)
                }
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
}

@Composable
fun MyBottomBar(navController: NavHostController) {
    // Data class to define bottom navigation items
    val navItems = listOf(
        BottomNavItem("Home", Route.HomeScreen.route, Icons.Rounded.Home),
        BottomNavItem("Post", Route.PostScreen.route, Icons.Rounded.AddBox),
        BottomNavItem("Notification", Route.NotificationScreen.route, Icons.Rounded.Notifications),
        BottomNavItem("Profile", Route.ProfileScreen.route, Icons.Rounded.AccountCircle)
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
                val isSelected = item.route == navController.currentBackStackEntry?.destination?.route
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        navController.navigate(item.route) {
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

data class BottomNavItem(val title: String, val route: String, val icon: ImageVector)