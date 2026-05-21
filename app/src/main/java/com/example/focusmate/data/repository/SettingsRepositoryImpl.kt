package com.example.focusmate.data.repository

import com.example.focusmate.data.local.dao.SettingsDao
import com.example.focusmate.data.local.entity.SettingsEntity
import com.example.focusmate.data.mapper.toDomain
import com.example.focusmate.data.mapper.toEntity
import com.example.focusmate.domain.model.Settings
import com.example.focusmate.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(

    private val settingsDao: SettingsDao

) : SettingsRepository {

    override suspend fun saveSettings(
        settings: Settings
    ) {

        settingsDao.saveSettings(
            settings.toEntity()
        )
    }

    override fun getSettings():
            Flow<Settings?> {

        return settingsDao
            .getSettings()
            .map { entity ->

                entity?.toDomain()
                    ?: SettingsEntity().toDomain()
            }
    }
}
