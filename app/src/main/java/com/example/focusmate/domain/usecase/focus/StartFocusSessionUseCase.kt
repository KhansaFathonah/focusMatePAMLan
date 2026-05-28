package com.example.focusmate.domain.usecase.focus

import com.example.focusmate.domain.model.FocusSession
import com.example.focusmate.domain.repository.FocusRepository
import javax.inject.Inject

class StartFocusSessionUseCase @Inject constructor(

    private val repository: FocusRepository

) {

    suspend operator fun invoke(
        session: FocusSession
    ): Long {

        return repository.startSession(session)
    }
}
