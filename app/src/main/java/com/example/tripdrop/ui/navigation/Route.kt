package com.example.tripdrop.ui.navigation

sealed class Route(val route: String) {
    data object WelcomeScreen : Route("welcomeScreen")
    data object LoginScreen : Route("loginScreen")
    data object SignUpScreen : Route("signupScreen")
    data object HomeScreen : Route("homeScreen")
    data object ProductDetailsScreen : Route("productDetailsScreen")
    data object PostScreen : Route("postScreen")
    data object ProfileScreen : Route("profileScreen")
    data object NotificationScreen : Route("notificationScreen") // corrected name to match usage
    data object BottomNav : Route("bottomNav") // corrected name to match usage
    data object SingleChatScreen : Route("singleChatScreen")
    data object ProfileDetailsScreen : Route("profileDetailsScreen")
}
