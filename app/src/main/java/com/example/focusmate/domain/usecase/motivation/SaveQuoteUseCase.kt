package com.example.focusmate.domain.usecase.motivation

import com.example.focusmate.domain.model.Motivation
import com.example.focusmate.domain.repository.MotivationRepository
import javax.inject.Inject

class SaveQuoteUseCase @Inject constructor(

    private val repository: MotivationRepository

) {

    suspend operator fun invoke(
        quote: Motivation
    ) {

        repository.saveQuote(quote)
    }
}