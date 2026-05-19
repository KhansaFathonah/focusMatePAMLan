package com.example.focusmate.presentation.history

import com.example.focusmate.domain.model.FocusSession

data class HistoryUiState(

    val sessions: List<FocusSession> = emptyList(),

    val selectedTab: String = "Daily"
)
