package com.example.focusmate.presentation.settingsPage.backup

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focusmate.domain.usecase.backup.BackupDataUseCase
import com.example.focusmate.domain.usecase.backup.RestoreBackupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class BackupViewModel @Inject constructor(

    private val backupDataUseCase: BackupDataUseCase,

    private val restoreBackupUseCase: RestoreBackupUseCase
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

    /*
    ====================================
    MANUAL BACKUP
    ====================================
    */

    fun backupToDirectory(
        directoryUri: Uri
    ) {

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isProcessing = true,
                    statusMessage = null
                )
            }

            runCatching {
                backupDataUseCase(
                    directoryUri
                )
            }.onSuccess { backupPath ->

                _uiState.update {
                    it.copy(
                        isProcessing = false,
                        lastBackupTime =
                            SimpleDateFormat(
                                "dd MMM yyyy, HH:mm",
                                Locale.getDefault()
                            ).format(Date()),
                        statusMessage =
                            "Backup is completed, file name ($backupPath)"
                    )
                }
            }.onFailure { throwable ->

                _uiState.update {
                    it.copy(
                        isProcessing = false,
                        statusMessage =
                            throwable.message
                                ?: "Backup failed"
                    )
                }
            }
        }
    }

    /*
    ====================================
    RESTORE BACKUP
    ====================================
    */

    fun restoreFromFile(
        fileUri: Uri
    ) {

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isProcessing = true,
                    statusMessage = null
                )
            }

            runCatching {
                restoreBackupUseCase(
                    fileUri
                )
            }.onSuccess {

                _uiState.update {
                    it.copy(
                        isProcessing = false,
                        statusMessage =
                            "Restore is completed"
                    )
                }
            }.onFailure { throwable ->

                _uiState.update {
                    it.copy(
                        isProcessing = false,
                        statusMessage =
                            throwable.message
                                ?: "Restore failed"
                    )
                }
            }
        }
    }
}
