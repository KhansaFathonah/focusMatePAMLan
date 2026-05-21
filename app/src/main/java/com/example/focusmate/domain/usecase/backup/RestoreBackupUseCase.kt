package com.example.focusmate.domain.usecase.backup

import android.net.Uri
import com.example.focusmate.domain.repository.BackupRepository
import javax.inject.Inject

class RestoreBackupUseCase @Inject constructor(

    private val repository: BackupRepository

) {

    suspend operator fun invoke(
        fileUri: Uri
    ) {

        repository.restoreBackup(
            fileUri
        )
    }
}
