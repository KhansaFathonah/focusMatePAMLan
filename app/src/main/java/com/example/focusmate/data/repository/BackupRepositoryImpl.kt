package com.example.focusmate.data.repository

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import androidx.room.withTransaction
import com.example.focusmate.data.backup.BackupData
import com.example.focusmate.data.backup.BackupProgress
import com.example.focusmate.data.backup.JsonExporter
import com.example.focusmate.data.backup.JsonImporter
import com.example.focusmate.data.local.database.FocusMateDatabase
import com.example.focusmate.domain.repository.BackupRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class BackupRepositoryImpl @Inject constructor(

    @ApplicationContext
    private val context: Context,

    private val database: FocusMateDatabase,

    private val jsonExporter: JsonExporter,

    private val jsonImporter: JsonImporter
) : BackupRepository {

    override suspend fun backupData(
        directoryUri: Uri
    ): String {

        val timestamp =
            SimpleDateFormat(
                "yyyy-MM-dd-HH-mm-ss",
                Locale.US
            ).format(Date())

        val fileName =
            "focusmate-backup_$timestamp.json"

        val backupData =
            createBackupData(
                timestamp = timestamp
            )

        val documentUri =
            createBackupDocument(
                directoryUri = directoryUri,
                fileName = fileName
            )

        context.contentResolver
            .openOutputStream(
                documentUri
            )
            ?.use { outputStream ->

                outputStream.write(
                    jsonExporter.export(
                        backupData
                    ).toByteArray()
                )
            }
            ?: throw IOException("Unable to open backup file")

        return "${getDirectoryDisplayName(directoryUri)}/$fileName"
    }

    override suspend fun restoreBackup(
        fileUri: Uri
    ) {

        val json =
            context.contentResolver
                .openInputStream(
                    fileUri
                )
                ?.bufferedReader()
                ?.use { reader ->
                    reader.readText()
                }
                ?: throw IOException("Unable to read backup file")

        val backupData =
            jsonImporter.import(
                json
            )

        database.withTransaction {

            database.taskDao()
                .clearTasks()

            database.focusSessionDao()
                .clearSessions()

            database.taskDao()
                .insertTasks(
                    backupData.tasks
                )

            database.focusSessionDao()
                .insertSessions(
                    backupData.history
                )
        }
    }

    private suspend fun createBackupData(
        timestamp: String
    ): BackupData {

        val tasks =
            database.taskDao()
                .getAllTasksSnapshot()

        val sessions =
            database.focusSessionDao()
                .getAllSessionsSnapshot()

        val completedSessions =
            sessions.filter { session ->
                session.isCompleted
            }

        return BackupData(
            createdAt = timestamp,
            tasks = tasks,
            progress = BackupProgress(
                totalTasks = tasks.size,
                completedTasks =
                    tasks.count { task ->
                        task.status == "Completed"
                    },
                totalFocusMinutes =
                    completedSessions.sumOf { session ->
                        session.durationMinutes
                    },
                totalSessions =
                    completedSessions.size
            ),
            history = sessions
        )
    }

    private fun createBackupDocument(
        directoryUri: Uri,
        fileName: String
    ): Uri {

        val parentDocumentUri =
            DocumentsContract.buildDocumentUriUsingTree(
                directoryUri,
                DocumentsContract.getTreeDocumentId(
                    directoryUri
                )
            )

        return DocumentsContract.createDocument(
            context.contentResolver,
            parentDocumentUri,
            "application/json",
            fileName
        ) ?: throw IOException("Unable to create backup file")
    }

    private fun getDirectoryDisplayName(
        directoryUri: Uri
    ): String {

        return DocumentsContract.getTreeDocumentId(
            directoryUri
        ).replace(
            oldValue = ":",
            newValue = "/"
        ).ifBlank {
            "Selected folder"
        }
    }
}
