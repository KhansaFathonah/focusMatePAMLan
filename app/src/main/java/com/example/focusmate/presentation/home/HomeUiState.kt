package com.example.focusmate.presentation.home

import com.example.focusmate.domain.model.Motivation
import com.example.focusmate.domain.model.Task

data class HomeUiState(

    val isLoading: Boolean = false,

    val tasks: List<Task> = emptyList(),

    val motivation: Motivation? = null
)