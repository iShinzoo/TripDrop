package com.example.tripdrop.navigator

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tripdrop.R
import com.example.tripdrop.navigation.Route
import com.example.tripdrop.presentation.HomeScreen
import com.example.tripdrop.presentation.PostScreen
import com.example.tripdrop.presentation.ProfileScreen

@Composable
fun TripDropNavigator(navController: NavController) {

    val bottomNavigationItem = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.home, text = "HOME"),
            BottomNavigationItem(icon = R.drawable.add, text = "POST"),
            BottomNavigationItem(icon = R.drawable.account, text = "PROFILE")
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    selectedItem = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.PostScreen.route -> 1
            Route.ProfileScreen.route -> 2
            else -> 0
        }
    }


    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route ||
                backStackState?.destination?.route == Route.PostScreen.route ||
                backStackState?.destination?.route == Route.ProfileScreen.route
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                BottomNavigation(
                    items = bottomNavigationItem,
                    selected = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTab(
                                navController = navController,
                                route = Route.HomeScreen.route
                            )

                            1 -> navigateToTab(
                                navController = navController,
                                route = Route.PostScreen.route
                            )

                            2 -> navigateToTab(
                                navController = navController,
                                route = Route.ProfileScreen.route
                            )
                        }
                    }
                )
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

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}