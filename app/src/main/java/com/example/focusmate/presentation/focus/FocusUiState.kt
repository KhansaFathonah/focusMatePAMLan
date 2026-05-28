package com.example.focusmate.presentation.focus

import com.example.focusmate.domain.model.FocusSession
import com.example.focusmate.domain.model.Task

data class FocusUiState(

    val isLoading: Boolean = false,

    val activeSession: FocusSession? = null,

    val selectedDuration: Int? = null,

    val remainingSeconds: Int = 0,

    val totalSeconds: Int = 0,

    val focusedSeconds: Int = 0,

    val progress: Float = 1f,

    val isRunning: Boolean = false,

    val isPaused: Boolean = false,

    val isCompleted: Boolean = false,

    val showExtendDialog: Boolean = false,

    val tasks: List<Task> = emptyList(),

    val selectedTask: Task? = null,

    val isQuickFocus: Boolean = false,

    val focusHistory:
    List<FocusSession> = emptyList(),

    val totalFocusMinutes: Int = 0,

    val totalSessions: Int = 0,

    val selectedHistoryTab: String =
        "Daily",

    val error: String? = null
)