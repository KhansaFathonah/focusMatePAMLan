package com.example.focusmate.data.mapper

import com.example.focusmate.data.local.entity.SettingsEntity
import com.example.focusmate.domain.model.Settings

fun SettingsEntity.toDomain(): Settings {

    return Settings(
        notificationsEnabled = notificationsEnabled,
        autoBackupEnabled = autoBackupEnabled,
        backupFrequency = backupFrequency,
        username = username
    )
}

fun Settings.toEntity(): SettingsEntity {

    return SettingsEntity(
        notificationsEnabled = notificationsEnabled,
        autoBackupEnabled = autoBackupEnabled,
        backupFrequency = backupFrequency,
        username = username
    )
}
