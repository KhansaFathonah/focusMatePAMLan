package com.example.focusmate.domain.usecase.backup

import com.example.focusmate.domain.repository.BackupRepository
import javax.inject.Inject

class RestoreBackupUseCase @Inject constructor(

    private val repository: BackupRepository

) {

    suspend operator fun invoke() {

        repository.restoreBackup()
    }
}