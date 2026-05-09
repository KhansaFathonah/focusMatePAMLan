package com.example.focusmate.domain.usecase.focus

import javax.inject.Inject

class ExtendFocusSessionUseCase @Inject constructor() {

    operator fun invoke(
        currentMinutes: Int
    ): Int {

        return currentMinutes + 10
    }
}