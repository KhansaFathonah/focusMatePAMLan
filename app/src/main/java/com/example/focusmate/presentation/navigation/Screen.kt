package com.example.focusmate.presentation.navigation

sealed class Screen(

    val route: String
) {

    data object Splash :
        Screen("splash")

    data object Home :
        Screen("home")

    data object AddTask :
        Screen("add_task")

    data object Focus :
        Screen("focus")

    data object SelectTask :
        Screen("select_task")

    data object StartFocus :
        Screen("start_focus")

    data object ActiveSession :
        Screen("active_session")

    data object SessionComplete :
        Screen("session_complete")

    data object QuickFocusDuration :
        Screen("quick_focus_duration")

    data object QuickFocusSession :
        Screen("quick_focus_session")

    data object QuickFocusComplete :
        Screen("quick_focus_complete")

    data object Progress :
        Screen("progress")

    data object History :
        Screen("history")

    data object Settings :
        Screen("settings")

    data object Notification :
        Screen("notification")

    data object Backup :
        Screen("backup")

    data object About :
        Screen("about")
}