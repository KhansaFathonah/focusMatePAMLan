package com.example.focusmate.data.remote.api

import com.example.focusmate.data.remote.response.MotivationResponse
import retrofit2.http.GET

interface MotivationApi {

    @GET("quotes/random?maxLength=100")
    suspend fun getRandomQuote():
            MotivationResponse
}
