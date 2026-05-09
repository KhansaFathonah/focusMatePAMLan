package com.example.focusmate.domain.model

data class Settings(

    val notificationsEnabled: Boolean,

    val autoBackupEnabled: Boolean,

    val backupFrequency: String
)