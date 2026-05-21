package com.example.focusmate.presentation.focus.withTask

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.focusmate.presentation.components.StartFocus.AnimatedTimerRing
import com.example.focusmate.presentation.components.StartFocus.ExtendFocusDialog
import com.example.focusmate.presentation.components.StartFocus.FocusControls
import com.example.focusmate.presentation.components.StartFocus.FocusTimer
import com.example.focusmate.presentation.components.StartFocus.TaskCard
import com.example.focusmate.presentation.focus.FocusViewModel
import com.example.focusmate.presentation.navigation.Screen
import com.example.focusmate.presentation.theme.BackgroundDark
import com.example.focusmate.presentation.theme.TextPrimary
import com.example.focusmate.presentation.theme.TextSecondary

@Composable
fun ActiveSessionScreen(

    navController: NavController,

    viewModel: FocusViewModel
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
    SESSION COMPLETE NAVIGATION
    ====================================
    */

    LaunchedEffect(
        uiState.isCompleted
    ) {

        if (uiState.isCompleted) {

            navController.navigate(
                Screen.SessionComplete.route
            ) {

                launchSingleTop = true
            }
        }
    }

    /*
    ====================================
    SCREEN
    ====================================
    */

    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(
                BackgroundDark
            )
    ) {

        /*
        ====================================
        MAIN CONTENT
        ====================================
        */

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally,

            verticalArrangement =
                Arrangement.Top
        ) {

            /*
            ====================================
            TOP SPACE
            ====================================
            */

            Spacer(
                modifier = Modifier.height(34.dp)
            )

            /*
            ====================================
            TITLE
            ====================================
            */

            Text(

                text = "Focus Mode",

                color = TextPrimary,

                fontSize = 28.sp,

                fontWeight =
                    FontWeight.ExtraBold
            )

            /*
            ====================================
            SUBTITLE
            ====================================
            */

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(

                text = "Stay Focused 🚀",

                color = TextSecondary,

                fontSize = 15.sp,

                fontWeight =
                    FontWeight.Medium
            )

            /*
            ====================================
            SPACE
            ====================================
            */

            Spacer(
                modifier = Modifier.height(34.dp)
            )

            /*
            ====================================
            TASK CARD
            ====================================
            */

            TaskCard(

                task =
                    uiState.selectedTask
            )

            /*
            ====================================
            SPACE
            ====================================
            */

            Spacer(
                modifier = Modifier.height(54.dp)
            )

            /*
            ====================================
            TIMER SECTION
            ====================================
            */

            Box(

                contentAlignment =
                    Alignment.Center
            ) {

                /*
                ====================================
                TIMER RING
                ====================================
                */

                AnimatedTimerRing(

                    progress =
                        uiState.progress
                )

                /*
                ====================================
                TIMER TEXT
                ====================================
                */

                FocusTimer(

                    remainingSeconds =
                        uiState.remainingSeconds
                )
            }

            /*
            ====================================
            SPACE
            ====================================
            */

            Spacer(
                modifier = Modifier.height(64.dp)
            )

            /*
            ====================================
            FOCUS CONTROLS
            ====================================
            */

            FocusControls(

                isRunning =
                    uiState.isRunning,

                onStopClick = {

                    /*
                    ================================
                    PAUSE / RESUME
                    ================================
                    */

                    if (uiState.isRunning) {

                        viewModel.pauseTimer()

                    } else {

                        viewModel.resumeTimer()
                    }
                },

                onAddTimeClick = {

                    viewModel.addExtraTime()
                }
            )

            /*
            ====================================
            SPACE
            ====================================
            */

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            /*
            ====================================
            SESSION STATUS
            ====================================
            */

            Text(

                text =

                    if (uiState.isRunning)

                        "●  Active Session"

                    else

                        "●  Session Paused",

                color =

                    if (uiState.isRunning)

                        Color(0xFF6BE59B)

                    else

                        Color(0xFFFFC857),

                fontSize = 13.sp,

                fontWeight =
                    FontWeight.SemiBold
            )
        }

        /*
        ====================================
        EXTEND FOCUS DIALOG
        ====================================
        */

        if (uiState.showExtendDialog) {

            ExtendFocusDialog(

                /*
                ================================
                ADD EXTRA TIME
                ================================
                */

                onAddTime = {

                    viewModel.addExtraTime()

                    viewModel.hideExtendDialog()
                },

                /*
                ================================
                FINISH TASK
                ================================
                */

                onFinishTask = {

                    uiState.selectedTask?.let {

                        viewModel.completeTask(it)
                    }

                    viewModel.resetSession()

                    navController.navigate(
                        Screen.Focus.route
                    ) {

                        popUpTo(
                            Screen.Focus.route
                        ) {

                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                }
            )
        }
    }
}