package com.example.focusmate.data.backup

import com.example.focusmate.data.local.entity.FocusSessionEntity
import com.example.focusmate.data.local.entity.MotivationEntity
import com.example.focusmate.data.local.entity.TaskEntity

data class BackupData(

    val tasks: List<TaskEntity>,

    val focusSessions: List<FocusSessionEntity>,

    val motivations: List<MotivationEntity>
)