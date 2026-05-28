package com.example.focusmate.domain.repository

import android.net.Uri

interface BackupRepository {

    suspend fun backupData(
        directoryUri: Uri
    ): String

    suspend fun restoreBackup(
        fileUri: Uri
    )
}
