package com.example.focusmate.domain.usecase.focus

import com.example.focusmate.domain.model.FocusSession
import com.example.focusmate.domain.repository.FocusRepository
import javax.inject.Inject

class UpdateFocusSessionUseCase @Inject constructor(

    private val repository: FocusRepository

) {

    /*
    ====================================
    UPDATE SESSION
    ====================================
    */

    suspend operator fun invoke(
        session: FocusSession
    ) {

        repository.updateSession(
            session
        )
    }
}