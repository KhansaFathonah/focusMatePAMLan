package com.example.focusmate.domain.repository

import com.example.focusmate.domain.model.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    suspend fun saveSettings(
        settings: Settings
    )

    fun getSettings():
            Flow<Settings?>
}