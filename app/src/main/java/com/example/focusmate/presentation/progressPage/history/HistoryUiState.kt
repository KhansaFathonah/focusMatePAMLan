package com.example.focusmate.presentation.progressPage.history

import com.example.focusmate.domain.model.FocusSession

data class HistoryUiState(

    val sessions: List<FocusSession> = emptyList(),

    val selectedTab: String = "Daily"
)
