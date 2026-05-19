package com.example.focusmate.presentation.homePage.home

import com.example.focusmate.domain.model.Task

sealed class HomeEvent {

    data object RefreshQuote :
        HomeEvent()

    data class CompleteTask(

        val task: Task

    ) : HomeEvent()

    data class DeleteTask(

        val task: Task

    ) : HomeEvent()
}