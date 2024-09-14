package com.example.tripdrop.data

import java.util.UUID

data class Product(
    val productId: String = UUID.randomUUID().toString(),  // Generates a unique ID
    val title: String,
    val description: String,
    val imageUrl: String?,
    val pickupPoint: String,
    val deliveryPoint: String,
    val time: String,
    val date: String,
    val rewards: String,
    val userId: String? = null // User who posted the product
)
