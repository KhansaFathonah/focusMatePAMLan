package com.example.focusmate.domain.usecase.backup

import android.net.Uri
import com.example.focusmate.domain.repository.BackupRepository
import javax.inject.Inject

class BackupDataUseCase @Inject constructor(

    private val repository: BackupRepository

) {

    suspend operator fun invoke(
        directoryUri: Uri
    ): String {

        return repository.backupData(
            directoryUri
        )
    }
}
