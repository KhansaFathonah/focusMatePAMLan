package com.example.focusmate.domain.usecase.motivation

import com.example.focusmate.domain.repository.MotivationRepository
import javax.inject.Inject

class RefreshQuoteUseCase @Inject constructor(

    private val repository: MotivationRepository

) {

    suspend operator fun invoke() {

        repository.refreshQuote()
    }
}