package com.example.focusmate.data.remote.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    private const val BASE_URL =
        "http://api.quotable.io/"

    fun provideApi(): MotivationApi {

        val logging =
            HttpLoggingInterceptor().apply {

                level =
                    HttpLoggingInterceptor.Level.BODY
            }

        val client =
            OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

        val retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .build()

        return retrofit.create(
            MotivationApi::class.java
        )
    }
}
