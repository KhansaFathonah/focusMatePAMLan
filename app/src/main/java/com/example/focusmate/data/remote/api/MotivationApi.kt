package com.example.focusmate.data.remote.api

import com.example.focusmate.data.remote.response.MotivationResponse
import retrofit2.http.GET

interface MotivationApi {

    @GET("api/random")
    suspend fun getRandomQuote():
            MotivationResponse
}