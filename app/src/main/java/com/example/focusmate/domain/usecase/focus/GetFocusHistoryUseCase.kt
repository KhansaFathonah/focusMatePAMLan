package com.example.focusmate.domain.usecase.focus

import com.example.focusmate.domain.repository.FocusRepository
import javax.inject.Inject

class GetFocusHistoryUseCase @Inject constructor(

    private val repository: FocusRepository

) {

    operator fun invoke() =

        repository.getAllSessions()

    fun completedSessions() =

        repository.getCompletedSessions()

    fun completedTaskSessions() =

        repository.getCompletedTaskSessions()
}
