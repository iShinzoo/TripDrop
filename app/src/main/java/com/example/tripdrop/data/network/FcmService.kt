package com.example.tripdrop.data.network

import com.example.tripdrop.data.model.NotificationRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FcmService {
    @POST("v1/projects/\n" +
            "dropit-cdbed/messages:send")
    suspend fun sendNotification(
        @Body notification: NotificationRequest
    ): Response<ResponseBody>// No need for type arguments here
}

