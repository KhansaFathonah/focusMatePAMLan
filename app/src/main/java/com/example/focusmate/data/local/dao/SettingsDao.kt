package com.example.focusmate.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.focusmate.data.local.entity.SettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSettings(
        settings: SettingsEntity
    )

    @Query("SELECT * FROM settings LIMIT 1")
    fun getSettings():
            Flow<SettingsEntity?>
}