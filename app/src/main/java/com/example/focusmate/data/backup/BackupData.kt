package com.example.focusmate.data.backup

import com.example.focusmate.data.local.entity.FocusSessionEntity
import com.example.focusmate.data.local.entity.TaskEntity

data class BackupData(

    val schemaVersion: Int = 1,

    val createdAt: String,

    val tasks: List<TaskEntity>,

    val progress: BackupProgress,

    val history: List<FocusSessionEntity>
)

data class BackupProgress(

    val totalTasks: Int,

    val completedTasks: Int,

    val totalFocusMinutes: Int,

    val totalSessions: Int
)
