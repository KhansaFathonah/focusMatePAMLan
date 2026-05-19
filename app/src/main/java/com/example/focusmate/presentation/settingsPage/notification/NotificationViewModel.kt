package com.example.focusmate.presentation.settingsPage.notification

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(

) : ViewModel() {

    /*
    ====================================
    UI STATE
    ====================================
    */

    private val _uiState =

        MutableStateFlow(
            NotificationUiState()
        )

    val uiState:
            StateFlow<NotificationUiState> =

        _uiState.asStateFlow()

    /*
    ====================================
    UPDATE FOCUS REMINDER
    ====================================
    */

    fun updateFocusReminder(
        enabled: Boolean
    ) {

        _uiState.update {

            it.copy(
                focusReminder = enabled
            )
        }
    }

    /*
    ====================================
    UPDATE TASK DEADLINE
    ====================================
    */

    fun updateTaskDeadline(
        enabled: Boolean
    ) {

        _uiState.update {

            it.copy(
                taskDeadline = enabled
            )
        }
    }

    /*
    ====================================
    UPDATE SESSION COMPLETE
    ====================================
    */

    fun updateSessionComplete(
        enabled: Boolean
    ) {

        _uiState.update {

            it.copy(
                sessionComplete = enabled
            )
        }
    }

    /*
    ====================================
    UPDATE BREAK REMINDER
    ====================================
    */

    fun updateBreakReminder(
        enabled: Boolean
    ) {

        _uiState.update {

            it.copy(
                breakReminder = enabled
            )
        }
    }
}