package com.example.tripdrop.data

import java.util.UUID

data class Product(
    val productId: String = UUID.randomUUID().toString(),  // Generates a unique ID
    var title: String? = null,
    var description: String? = null,
    var imageUrl: String? = null,
    var pickupPoint: String? = null,
    var deliveryPoint: String? = null,
    var time: String? = null,
    var date: String? = null,
    var rewards: String? = null,
    val userId: String? = null // User who posted the product
) {
    constructor() : this(null.toString(), null, null, null, null, null, null, null, null,null)
}

