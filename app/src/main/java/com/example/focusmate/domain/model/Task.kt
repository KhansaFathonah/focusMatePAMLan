package com.example.focusmate.domain.model

data class Task(

    val id: Int = 0,

    val title: String,

    val deadline: String,

    val status: String = "Not Started",

    val focusMinutes: Int = 0
)