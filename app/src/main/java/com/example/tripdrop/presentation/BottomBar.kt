package com.example.tripdrop.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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

    val navController1 = rememberNavController()

    Scaffold(containerColor = bgwhite,
        bottomBar = { MyBottomBar(navController1) }
    ) { innerPadding ->

        NavHost(
            navController = navController1, startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Route.HomeScreen.route) {
                HomeScreen()
            }
            composable(Route.PostScreen.route) {
                PostScreen(navController1)
            }
            composable(Route.NotificationScreen.route) {
                NotificationScreen()
            }
            composable(Route.ProfileScreen.route) {
                ProfileScreen()
            }

        }
    }
}


@Composable
fun MyBottomBar(navController1: NavHostController) {

    val backStackEntry = navController1.currentBackStackEntryAsState()

    data class BottomNavItem(
        val title: String,
        val route: String,
        val icon: ImageVector
    )

    val list = listOf(
        BottomNavItem(
            "Home",
            Route.HomeScreen.route,
            Icons.Rounded.Home
        ),
        BottomNavItem(
            "Post",
            Route.PostScreen.route, // Corrected route for Search destination
            Icons.Rounded.AddBox
        ),
        BottomNavItem(
            "Notification",
            Route.NotificationScreen.route, // Corrected route for AddThread destination
            Icons.Rounded.Notifications
        ),
        BottomNavItem(
            "Profile",
            Route.ProfileScreen.route, // Corrected route for Notification destination
            Icons.Rounded.AccountCircle
        )
    )
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
//            modifier = Modifier.padding(10.dp)
        ) {
            list.forEach {
                // val selected = it.route == backStackEntry?.value?.destination?.route

                val selected = it.route == backStackEntry.value?.destination?.route

//                val selectedColor = if (selected) Color.Green else Color.Red
//                val unselectedColor = if (!selected) Color.Red else Color.Green
                NavigationBarItem(
                    modifier = Modifier
                        .background(color = Color.Transparent)
                        .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)),
                    selected = selected,
                    enabled = true,

                    onClick = {
                        navController1.navigate(it.route) {
                            popUpTo(navController1.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    },
                    alwaysShowLabel = false,
//                    label ={false},
                    icon = {
                        Icon(
                            modifier = Modifier.size(27.dp),
                            imageVector = it.icon,
                            tint = if (selected) colorResource(id = R.color.white) else Color.Gray,
                            contentDescription = it.title
                        )
                    }
                )
            }
        }
    }
}