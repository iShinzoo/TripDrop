package com.example.tripdrop.navigation

sealed class Route(
    val route: String
) {
    data object WelcomeScreen : Route(route = "welcomeScreen")
    data object LoginScreen : Route(route = "loginScreen")
    data object SignUpScreen : Route(route = "signupScreen")


    data object HomeScreen : Route(route = "homeScreen")
    data object DetailsScreen : Route(route = "detailsScreen")
    data object PostScreen : Route(route = "postScreen")
    data object ProfileScreen : Route(route = "profileScreen")

    data object TripDropNavigatorScreen : Route(route = "tripDropNavigatorScreen")
}