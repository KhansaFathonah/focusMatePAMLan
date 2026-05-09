package com.example.focusmate.domain.repository

import com.example.focusmate.domain.model.Motivation
import kotlinx.coroutines.flow.Flow

interface MotivationRepository {

    fun getRandomQuote():
            Flow<Motivation>

    suspend fun saveQuote(
        quote: Motivation
    )

    suspend fun refreshQuote()
}