package com.example.tripdrop.ui.navigation

sealed class Route(
    val route: String
) {
    data object WelcomeScreen : Route(route = "welcomeScreen")
    data object LoginScreen : Route(route = "loginScreen")
    data object SignUpScreen : Route(route = "signupScreen")


    data object HomeScreen : Route(route = "homeScreen")
    data object ProductDetailsScreen : Route(route = "productDetailsScreen")
    data object PostScreen : Route(route = "postScreen")
    data object ProfileScreen : Route(route = "profileScreen")

    data object NotificationScreen : Route(route = "NotificationScreen")

    data object BottomNav : Route(route = "BottomNav")
    data object Home : Route(route = "Home")

    data object DetailsCard : Route(route = "detailsCard")

    data object SingleChatScreen : Route(route = "singleChatScreen")
    data object ProfileDetailsScreen : Route(route = "profileDetailsScreen")
}