package com.example.focusmate.presentation.homePage.addtask

sealed class AddTaskEvent {

    data class OnTitleChange(
        val title: String
    ) : AddTaskEvent()

    data class OnDeadlineChange(
        val deadline: String,

        val deadlineMillis: Long
    ) : AddTaskEvent()

    data object SaveTask :
        AddTaskEvent()
}
