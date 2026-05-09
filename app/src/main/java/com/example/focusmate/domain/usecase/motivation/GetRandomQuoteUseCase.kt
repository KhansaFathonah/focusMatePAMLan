package com.example.focusmate.domain.usecase.motivation

import com.example.focusmate.domain.repository.MotivationRepository
import javax.inject.Inject

class GetRandomQuoteUseCase @Inject constructor(

    private val repository: MotivationRepository

) {

    operator fun invoke() =

        repository.getRandomQuote()
}