package com.example.focusmate.presentation.settingsPage.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.focusmate.presentation.components.settings.NotificationToggleCard
import com.example.focusmate.presentation.theme.BackgroundDark
import com.example.focusmate.presentation.theme.TextPrimary
import com.example.focusmate.presentation.theme.TextSecondary
import androidx.compose.ui.text.style.TextAlign

@Composable
fun NotificationScreen(

    navController: NavController,

    viewModel: NotificationViewModel =
        hiltViewModel()
) {

    /*
    ====================================
    UI STATE
    ====================================
    */

    val uiState by
    viewModel.uiState.collectAsState()

    /*
    ====================================
    SCREEN
    ====================================
    */

    Scaffold(

        containerColor =
            BackgroundDark

    ) { paddingValues ->

        Box(

            modifier = Modifier
                .fillMaxSize()
                .background(
                    BackgroundDark
                )
        ) {

            Column(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        paddingValues
                    )
                    .padding(

                        horizontal = 24.dp,
                        vertical = 16.dp
                    )
                    .navigationBarsPadding(),

                verticalArrangement =
                    Arrangement.Top
            ) {

                /*
                ====================================
                TOP BAR
                ====================================
                */

                androidx.compose.foundation.layout.Row(

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    IconButton(

                        onClick = {

                            navController.popBackStack()
                        }
                    ) {

                        Icon(

                            imageVector =
                                Icons.AutoMirrored.Outlined.ArrowBack,

                            contentDescription =
                                "Back",

                            tint = TextPrimary
                        )
                    }

                    Text(

                        text = "Notifications",

                        color = TextPrimary,

                        fontSize = 21.sp,

                        fontWeight =
                            FontWeight.SemiBold
                    )
                }

                Spacer(
                    modifier = Modifier.height(28.dp)
                )

                NotificationToggleCard(

                    title = "Focus Reminders",

                    subtitle =
                        "Remind me to start focus sessions",

                    checked =
                        uiState.focusReminder,

                    onCheckedChange = {

                        viewModel.updateFocusReminder(
                            it
                        )
                    }
                )

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                NotificationToggleCard(

                    title = "Task Deadlines",

                    subtitle =
                        "Alert me before tasks are due",

                    checked =
                        uiState.taskDeadline,

                    onCheckedChange = {

                        viewModel.updateTaskDeadline(
                            it
                        )
                    }
                )

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                NotificationToggleCard(

                    title = "Session Complete",

                    subtitle =
                        "Notify when focus session ends",

                    checked =
                        uiState.sessionComplete,

                    onCheckedChange = {

                        viewModel.updateSessionComplete(
                            it
                        )
                    }
                )

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                NotificationToggleCard(

                    title = "Break Reminder",

                    subtitle =
                        "Remind me to take short breaks",

                    checked =
                        uiState.breakReminder,

                    onCheckedChange = {

                        viewModel.updateBreakReminder(
                            it
                        )
                    }
                )

                Spacer(
                    modifier = Modifier.height(34.dp)
                )

                Text(

                    text =
                        "Enable notifications to stay on track with your productivity goals",

                    color =
                        TextSecondary,

                    fontSize = 15.sp,

                    lineHeight = 24.sp,

                    textAlign =
                        TextAlign.Center,

                    modifier = Modifier
                        .align(
                            Alignment.CenterHorizontally
                        )
                )
            }
        }
    }
}