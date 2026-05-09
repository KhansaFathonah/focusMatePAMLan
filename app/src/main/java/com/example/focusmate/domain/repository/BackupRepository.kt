package com.example.focusmate.domain.repository

interface BackupRepository {

    suspend fun backupData()

    suspend fun restoreBackup()
}