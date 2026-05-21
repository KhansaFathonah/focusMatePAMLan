package com.example.focusmate.presentation.homePage.addtask

data class AddTaskUiState(

    val title: String = "",

    val deadline: String = "",

    val deadlineMillis: Long? = null,

    val errorMessage: String? = null,

    val isSaveEnabled: Boolean = false
)
