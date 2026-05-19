package com.example.focusmate.presentation.settingsPage.backup

data class BackupUiState(

    val autoBackup: Boolean = true,

    val selectedFrequency: String = "Daily",

    val lastBackupTime: String =
        "Today, 08:45 PM"
)