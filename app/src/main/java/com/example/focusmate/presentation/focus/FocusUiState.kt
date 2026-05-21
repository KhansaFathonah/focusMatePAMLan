package com.example.focusmate.presentation.focus

import com.example.focusmate.domain.model.FocusSession
import com.example.focusmate.domain.model.Task

data class FocusUiState(

    /*
    ====================================
    LOADING
    ====================================
    */

    val isLoading: Boolean = false,

    /*
    ====================================
    ACTIVE SESSION
    ====================================
    */

    val activeSession: FocusSession? = null,

    /*
    ====================================
    TIMER
    ====================================
    */

    val selectedDuration: Int? = null,

    val remainingSeconds: Int = 0,

    val totalSeconds: Int = 0,

    /*
    ====================================
    TIMER PROGRESS
    ====================================
    */

    val progress: Float = 1f,

    /*
    ====================================
    TIMER STATUS
    ====================================
    */

    val isRunning: Boolean = false,

    val isPaused: Boolean = false,

    val isCompleted: Boolean = false,

    /*
    ====================================
    EXTEND FOCUS DIALOG
    ====================================
    */

    val showExtendDialog: Boolean = false,

    /*
    ====================================
    TASK
    ====================================
    */

    val tasks: List<Task> = emptyList(),

    val selectedTask: Task? = null,

    /*
    ====================================
    QUICK FOCUS
    ====================================
    */

    val isQuickFocus: Boolean = false,

    /*
    ====================================
    HISTORY
    ====================================
    */

    val focusHistory:
    List<FocusSession> = emptyList(),

    /*
    ====================================
    STATISTICS
    ====================================
    */

    val totalFocusMinutes: Int = 0,

    val totalSessions: Int = 0,

    /*
    ====================================
    HISTORY FILTER
    ====================================
    */

    val selectedHistoryTab: String =
        "Daily",

    /*
    ====================================
    ERROR
    ====================================
    */

    val error: String? = null
)