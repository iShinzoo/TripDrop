package com.example.tripdrop.data.network

import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder().addInterceptor(logging).addInterceptor { chain ->
            val request = chain.request().newBuilder().addHeader(
                "Authorization", "key=YOUR_SERVER_KEY"
            ) // Replace with your FCM server key
                .addHeader("Content-Type", "application/json").build()
            chain.proceed(request)
        }.build()

        return Retrofit.Builder().baseUrl("https://fcm.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()
    }


    @Provides
    @Singleton
    fun provideFcmService(retrofit: Retrofit): FcmService {
        return retrofit.create(FcmService::class.java)
    }

    @Provides
    @Singleton
    fun provideFirebaseMessaging(): FirebaseMessaging {
        return FirebaseMessaging.getInstance()
    }
}
