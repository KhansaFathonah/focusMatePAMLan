package com.example.focusmate.domain.usecase.focus

import com.example.focusmate.domain.repository.FocusRepository
import javax.inject.Inject

class GetFocusStatisticsUseCase @Inject constructor(

    private val repository: FocusRepository

) {

    /*
    ====================================
    TOTAL MINUTES
    ====================================
    */

    fun getTotalMinutes() =

        repository.getTotalFocusMinutes()

    /*
    ====================================
    TOTAL SESSIONS
    ====================================
    */

    fun getTotalSessions() =

        repository.getTotalSessions()
}