package com.example.focusmate.data.mapper

import com.example.focusmate.data.local.entity.FocusSessionEntity
import com.example.focusmate.domain.model.FocusSession

/*
========================================
ENTITY TO DOMAIN
========================================
*/

fun FocusSessionEntity.toDomain(): FocusSession {

    return FocusSession(

        id = id,

        taskId = taskId,

        taskTitle = taskTitle,

        durationMinutes = durationMinutes,

        remainingSeconds = remainingSeconds,

        totalSeconds = totalSeconds,

        isRunning = isRunning,

        isCompleted = isCompleted,

        date = date,

        startTime = startTime,

        endTime = endTime
    )
}

/*
========================================
DOMAIN TO ENTITY
========================================
*/

fun FocusSession.toEntity(): FocusSessionEntity {

    return FocusSessionEntity(

        id = id,

        taskId = taskId,

        taskTitle = taskTitle,

        durationMinutes = durationMinutes,

        remainingSeconds = remainingSeconds,

        totalSeconds = totalSeconds,

        isRunning = isRunning,

        isCompleted = isCompleted,

        date = date,

        startTime = startTime,

        endTime = endTime
    )
}