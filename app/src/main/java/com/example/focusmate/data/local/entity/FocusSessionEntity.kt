package com.example.focusmate.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "focus_sessions")
data class FocusSessionEntity(

    /*
    ====================================
    ID
    ====================================
    */

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /*
    ====================================
    TASK
    ====================================
    */

    val taskId: Int? = null,

    val taskTitle: String? = null,

    /*
    ====================================
    SESSION
    ====================================
    */

    val durationMinutes: Int,

    /*
    ====================================
    TIMER
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

    val sessionStatus: String = "Success",

    /*
    ====================================
    TIME
    ====================================
    */

    val date: String,

    val startTime: String,

    val endTime: String? = null
)
