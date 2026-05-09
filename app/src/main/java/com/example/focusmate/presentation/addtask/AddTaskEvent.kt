package com.example.focusmate.presentation.addtask

sealed class AddTaskEvent {

    data class OnTitleChange(
        val title: String
    ) : AddTaskEvent()

    data class OnDeadlineChange(
        val deadline: String
    ) : AddTaskEvent()

    data object SaveTask :
        AddTaskEvent()
}