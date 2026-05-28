package com.example.focusmate.presentation.progressPage.progress

import com.example.focusmate.domain.model.FocusSession
import com.example.focusmate.domain.model.Task

data class ProgressUiState(

    val tasks: List<Task> = emptyList(),

    val sessions: List<FocusSession> = emptyList(),

    val completedTasks: Int = 0,

    val totalTasks: Int = 0,

    val totalFocusMinutes: Int = 0,

    val totalSessions: Int = 0,

    val focusSessions: Int = 0,

    val thisWeekCompleted: Int = 0,

    val weeklyActivity: List<Int> =
        List(7) { 0 },

    val weeklyMinutes: List<Int> =
        List(7) { 0 }
)
