package com.example.tripdrop.data.model

data class ChatData(
    val chatId : String?= "",
    val user1 : UserData = UserData(),
    val user2 : UserData = UserData()
)