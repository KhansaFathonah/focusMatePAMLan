package com.example.focusmate.domain.usecase.focus

import com.example.focusmate.domain.repository.FocusRepository
import javax.inject.Inject

class GetActiveSessionUseCase @Inject constructor(

    private val repository: FocusRepository

) {

    /*
    ====================================
    GET ACTIVE SESSION
    ====================================
    */

    operator fun invoke() =

        repository.getActiveSession()
}