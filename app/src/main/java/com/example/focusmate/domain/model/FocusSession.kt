package com.example.focusmate.domain.model

data class FocusSession(

    /*
    ====================================
    ID
    ====================================
    */

    val id: Int = 0,

    /*
    ====================================
    TASK RELATION
    null kalau quick focus
    ====================================
    */

    val taskId: Int? = null,

    val taskTitle: String? = null,

    /*
    ====================================
    SESSION CONFIG
    ====================================
    */

    val durationMinutes: Int,

    /*
    ====================================
    TIMER STATE
    ====================================
    */

    val remainingSeconds: Int,

    val totalSeconds: Int,

    /*
    ====================================
    STATUS
    ====================================
    */

    val isRunning: Boolean = false,

    val isCompleted: Boolean = false,

    /*
    ====================================
    TIME
    ====================================
    */

    val date: String,

    val startTime: String,

    val endTime: String? = null
)