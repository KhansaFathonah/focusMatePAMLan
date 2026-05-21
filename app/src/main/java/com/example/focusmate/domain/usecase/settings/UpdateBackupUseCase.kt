package com.example.focusmate.domain.usecase.settings

import com.example.focusmate.domain.model.Settings
import com.example.focusmate.domain.repository.SettingsRepository
import javax.inject.Inject

class UpdateBackupUseCase @Inject constructor(

    private val repository: SettingsRepository
) {

    suspend operator fun invoke(
        settings: Settings
    ) {

        repository.saveSettings(
            settings
        )
    }
}
