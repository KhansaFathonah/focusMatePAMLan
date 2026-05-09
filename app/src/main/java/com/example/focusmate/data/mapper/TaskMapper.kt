package com.example.focusmate.data.mapper

import com.example.focusmate.data.local.entity.TaskEntity
import com.example.focusmate.domain.model.Task

fun TaskEntity.toDomain(): Task {

    return Task(

        id = id,

        title = title,

        deadline = deadline,

        status = status,

        focusMinutes = focusMinutes
    )
}

fun Task.toEntity(): TaskEntity {

    return TaskEntity(

        id = id,

        title = title,

        deadline = deadline,

        status = status,

        focusMinutes = focusMinutes
    )
}