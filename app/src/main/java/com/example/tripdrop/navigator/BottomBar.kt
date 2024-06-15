package com.example.tripdrop.navigator

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tripdrop.navigation.Route
import com.example.tripdrop.presentation.HomeScreen
import com.example.tripdrop.presentation.PostScreen
import com.example.tripdrop.presentation.ProfileScreen

@Composable
fun BottomBar() {
    var selectedItem by rememberSaveable { mutableStateOf(0) }
    val navController = rememberNavController()
    val items = listOf(
        NavigationItem("Home", Icons.Filled.Home, Route.HomeScreen.route),
        NavigationItem("Post", Icons.Filled.AddCircle, Route.PostScreen.route),
        NavigationItem("Profile", Icons.Filled.AccountCircle, Route.ProfileScreen.route)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(item.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        },
                        alwaysShowLabel = false
                    )
                }
            }
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.HomeScreen.route) {
                HomeScreen(navController = navController)
            }
            composable(route = Route.PostScreen.route) {
                PostScreen(navController = navController)
            }
            composable(route = Route.ProfileScreen.route) {
                ProfileScreen(navController = navController)
            }
        }
    }
}

data class NavigationItem(val label: String, val icon: ImageVector, val route: String)
