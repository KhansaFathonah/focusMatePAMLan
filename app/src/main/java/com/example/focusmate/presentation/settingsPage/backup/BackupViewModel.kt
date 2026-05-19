package com.example.focusmate.presentation.settingsPage.backup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BackupViewModel @Inject constructor(

) : ViewModel() {

    /*
    ====================================
    UI STATE
    ====================================
    */

    private val _uiState =

        MutableStateFlow(
            BackupUiState()
        )

    val uiState:
            StateFlow<BackupUiState> =

        _uiState.asStateFlow()

    /*
    ====================================
    TOGGLE AUTO BACKUP
    ====================================
    */

    fun updateAutoBackup(
        enabled: Boolean
    ) {

        _uiState.update {

            it.copy(
                autoBackup = enabled
            )
        }
    }

    /*
    ====================================
    UPDATE FREQUENCY
    ====================================
    */

    fun updateFrequency(
        frequency: String
    ) {

        _uiState.update {

            it.copy(
                selectedFrequency =
                    frequency
            )
        }
    }
}