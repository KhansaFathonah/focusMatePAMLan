package com.example.focusmate.domain.usecase.settings

import com.example.focusmate.domain.repository.SettingsRepository
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(

    private val repository: SettingsRepository
) {

    operator fun invoke() =

        repository.getSettings()
}
