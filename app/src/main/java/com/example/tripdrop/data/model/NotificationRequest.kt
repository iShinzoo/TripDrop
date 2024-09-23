package com.example.tripdrop.data.model

data class NotificationRequest(
    val to: String,
    val notification: NotificationBody,
    val data: Map<String, String>
)