package com.example.focusmate.presentation.addtask

data class AddTaskUiState(

    val title: String = "",

    val deadline: String = "",

    val isSaveEnabled: Boolean = false
)