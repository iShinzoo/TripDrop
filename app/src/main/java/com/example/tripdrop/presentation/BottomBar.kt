package com.example.tripdrop.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tripdrop.R
import com.example.tripdrop.navigation.Route
import com.example.tripdrop.ui.theme.bgwhite

@Preview(showSystemUi = true)
@Composable
fun BottomBar() {
    val navController = rememberNavController()

    Scaffold(
        containerColor = bgwhite,
        bottomBar = {
            // Get the current back stack entry
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            // Get the current route
            val currentRoute = navBackStackEntry?.destination?.route

            // Conditionally show the bottom bar
            if (currentRoute in listOf(
                    Route.HomeScreen.route,
                    Route.PostScreen.route,
                    Route.NotificationScreen.route,
                    Route.ProfileScreen.route
                )) {
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
                HomeScreen(navController)
            }
            composable(Route.PostScreen.route) {
                PostScreen(navController)
            }
            composable(Route.NotificationScreen.route) {
                NotificationScreen()
            }
            composable(Route.ProfileScreen.route) {
                ProfileScreen()
            }
            composable(Route.ProductDetailsScreen.route) {
                ProductDetailsScreen(navController)
            }
        }
    }
}

@Composable
fun MyBottomBar(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    data class BottomNavItem(
        val title: String,
        val route: String,
        val icon: ImageVector
    )

    val list = remember {
        listOf(
            BottomNavItem("Home", Route.HomeScreen.route, Icons.Rounded.Home),
            BottomNavItem("Post", Route.PostScreen.route, Icons.Rounded.AddBox),
            BottomNavItem("Notification", Route.NotificationScreen.route, Icons.Rounded.Notifications),
            BottomNavItem("Profile", Route.ProfileScreen.route, Icons.Rounded.AccountCircle)
        )
    }

    ElevatedCard(
        modifier = Modifier
            .shadow(
                32.dp,
                CircleShape, ambientColor = Color.Black,
                spotColor = Color.Black
            )
            .padding(bottom = 20.dp, start = 20.dp, end = 20.dp)
            .height(60.dp),
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(
            10.dp
        )
    ) {
        BottomAppBar(
            containerColor = Color.White,
            tonalElevation = 10.dp
        ) {
            list.forEach { item ->
                val selected = item.route == backStackEntry?.destination?.route
                NavigationBarItem(
                    modifier = Modifier
                        .background(color = Color.Transparent)
                        .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)),
                    selected = selected,
                    enabled = true,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    },
                    alwaysShowLabel = false,
                    icon = {
                        Icon(
                            modifier = Modifier.size(27.dp),
                            imageVector = item.icon,
                            tint = if (selected) colorResource(id = R.color.white) else Color.Gray,
                            contentDescription = item.title
                        )
                    }
                )
            }
        }
    }
}
