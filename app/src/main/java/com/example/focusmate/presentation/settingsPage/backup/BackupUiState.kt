package com.example.focusmate.presentation.settingsPage.backup

data class BackupUiState(

    val autoBackup: Boolean = true,

    val selectedFrequency: String = "Daily",

    val lastBackupTime: String =
        "No backup yet",

    val isProcessing: Boolean = false,

    val statusMessage: String? = null
)
