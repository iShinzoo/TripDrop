package com.example.tripdrop.data

data class UserData(
    val userId : String?= "",
    val name : String?= "",
    val number : String?= "",
    val imageUrl : String?= ""
){
    fun toMap() = mapOf(
        "userId" to userId,
        "name" to name,
        "number" to number,
        "imageUrl" to imageUrl,
    )
}