package com.example.tripdrop.ui.navigation

enum class Route(val route: String) {
    WelcomeScreen("welcomeScreen"),
    LoginScreen("loginScreen"),
    SignUpScreen("signUpScreen"),
    HomeScreen("homeScreen"),
    ProductDetailScreen("productDetailScreen"),
    PostScreen("postScreen"),
    ProfileScreen("profileScreen"),
    NotificationScreen("notificationScreen"),
    BottomNav("bottomNav"),
    SingleChatScreen("singleChatScreen/{chatId}"),
    DummyChatScreen("DummyChatScreen"),
    ProfileDetailScreen("profileDetailScreen"),
    UserDataCollectionScreen("userDataCollectionScreen"),
    YoursOrderScreen("yoursOrderScreen"),
    PaymentScreen("paymentScreen"),
    HelpScreen("helpScreen"),
    PolicyScreen("policyScreen"),
    FeedbackScreen("feedbackScreen"),
    FavouriteScreen("favouriteScreen"),
    SplashScreen("splashScreen"),
    OnboardingScreen("onboardingScreen");

    // Function to create route for SingleChatScreen with chatId or return static route for other screens
    fun createRoute(chatId: String? = null): String {
        return if (this == SingleChatScreen && chatId != null) {
            "singleChatScreen/$chatId"
        } else {
            route
        }
    }

}
