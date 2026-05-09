package com.example.focusmate.presentation.focus

import com.example.focusmate.domain.model.Task

sealed class FocusEvent {

    /*
    ====================================
    FOCUS MODE
    ====================================
    */

    data object StartQuickFocus :
        FocusEvent()

    data class StartFocusWithTask(

        val task: Task

    ) : FocusEvent()

    /*
    ====================================
    TIMER
    ====================================
    */

    data class SelectDuration(

        val minutes: Int

    ) : FocusEvent()

    data object StartTimer :
        FocusEvent()

    data object PauseTimer :
        FocusEvent()

    data object ResumeTimer :
        FocusEvent()

    data object StopTimer :
        FocusEvent()

    data object CompleteTimer :
        FocusEvent()

    data object AddExtraTime :
        FocusEvent()

    /*
    ====================================
    TASK
    ====================================
    */

    data class SelectTask(

        val task: Task

    ) : FocusEvent()

    /*
    ====================================
    HISTORY
    ====================================
    */

    data class ChangeHistoryTab(

        val tab: String

    ) : FocusEvent()

    /*
    ====================================
    RESET
    ====================================
    */

    data object ResetSession :
        FocusEvent()
}