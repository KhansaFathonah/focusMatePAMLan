package com.example.focusmate.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.focusmate.data.local.dao.FocusSessionDao
import com.example.focusmate.data.local.dao.MotivationDao
import com.example.focusmate.data.local.dao.SettingsDao
import com.example.focusmate.data.local.dao.TaskDao
import com.example.focusmate.data.local.entity.FocusSessionEntity
import com.example.focusmate.data.local.entity.MotivationEntity
import com.example.focusmate.data.local.entity.SettingsEntity
import com.example.focusmate.data.local.entity.TaskEntity

@Database(
    entities = [
        TaskEntity::class,
        FocusSessionEntity::class,
        MotivationEntity::class,
        SettingsEntity::class
    ],
    version = 4,
    exportSchema = false
)

abstract class FocusMateDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    abstract fun focusSessionDao(): FocusSessionDao

    abstract fun motivationDao(): MotivationDao

    abstract fun settingsDao(): SettingsDao
}
