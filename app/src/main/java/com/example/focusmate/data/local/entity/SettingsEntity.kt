package com.example.focusmate.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class SettingsEntity(

    @PrimaryKey
    val id: Int = 1,

    val notificationsEnabled: Boolean = true,

    val autoBackupEnabled: Boolean = true,

    val backupFrequency: String = "Daily"
)