package com.example.focusmate.presentation.settingsPage.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focusmate.domain.model.Settings
import com.example.focusmate.domain.usecase.settings.GetSettingsUseCase
import com.example.focusmate.domain.usecase.settings.UpdateUsernameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainSettingsViewModel @Inject constructor(

    private val getSettingsUseCase: GetSettingsUseCase,

    private val updateUsernameUseCase: UpdateUsernameUseCase

) : ViewModel() {

    private val _settings =
        MutableStateFlow(
            Settings(
                notificationsEnabled = true,
                autoBackupEnabled = true,
                backupFrequency = "Daily"
            )
        )

    val settings: StateFlow<Settings> =
        _settings.asStateFlow()

    init {

        viewModelScope.launch {

            getSettingsUseCase()
                .collect { currentSettings ->

                    currentSettings?.let { settings ->

                        _settings.value = settings
                    }
                }
        }
    }

    fun updateUsername(
        username: String
    ) {

        val updatedSettings =
            _settings.value.copy(
                username = username.trim()
            )

        _settings.update {
            updatedSettings
        }

        viewModelScope.launch {

            updateUsernameUseCase(
                updatedSettings
            )
        }
    }
}
