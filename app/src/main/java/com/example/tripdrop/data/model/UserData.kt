package com.example.tripdrop.data.model

data class UserData(
    val userId : String?= "",
    var name : String?= "",
    var number : String?= "",
    var imageUrl : String?= ""
){
    fun toMap() = mapOf(
        "userId" to userId,
        "name" to name,
        "number" to number,
        "imageUrl" to imageUrl,
    )
}