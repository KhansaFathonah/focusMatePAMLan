package com.example.focusmate.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks"
)
data class TaskEntity(

    @PrimaryKey(autoGenerate = true)

    val id: Int = 0,

    val title: String,

    val deadline: String,

    val status: String,

    val focusMinutes: Int = 0,

    val focusSessions: Int = 0
)