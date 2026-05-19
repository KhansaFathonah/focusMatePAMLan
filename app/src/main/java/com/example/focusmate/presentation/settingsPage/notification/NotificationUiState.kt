package com.example.focusmate.presentation.settingsPage.notification

data class NotificationUiState(

    val focusReminder: Boolean = true,

    val taskDeadline: Boolean = true,

    val sessionComplete: Boolean = true,

    val breakReminder: Boolean = false
)