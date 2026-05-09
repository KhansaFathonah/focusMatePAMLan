package com.example.focusmate.presentation.focus.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.focusmate.presentation.components.startfocus.AnimatedTimerRing
import com.example.focusmate.presentation.components.startfocus.FocusControls
import com.example.focusmate.presentation.components.startfocus.FocusTimer
import com.example.focusmate.presentation.focus.FocusViewModel
import com.example.focusmate.presentation.theme.BackgroundDark

@Composable
fun StartFocusScreen(

    navController: NavController,

    viewModel: FocusViewModel =
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
    SELECTED TASK
    ====================================
    */

    val selectedTask =
        uiState.selectedTask

    /*
    ====================================
    FORMATTED TIME
    ====================================
    */

    val minutes =

        uiState.remainingSeconds / 60

    val seconds =

        uiState.remainingSeconds % 60

    val formattedTime =

        String.format(
            "%02d:%02d",
            minutes,
            seconds
        )

    /*
    ====================================
    SCREEN
    ====================================
    */

    Scaffold(

        containerColor =
            BackgroundDark

    ) { paddingValues ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .background(
                    BackgroundDark
                )
                .padding(
                    paddingValues
                )
                .padding(
                    horizontal = 24.dp,
                    vertical = 22.dp
                )
                .navigationBarsPadding(),

            horizontalAlignment =
                Alignment.CenterHorizontally,

            verticalArrangement =
                Arrangement.SpaceBetween
        ) {

            /*
            ====================================
            TOP SECTION
            ====================================
            */

            Column(

                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                /*
                ================================
                BACK BUTTON
                ================================
                */

                IconButton(

                    onClick = {

                        navController.popBackStack()
                    },

                    modifier = Modifier
                        .align(
                            Alignment.Start
                        )
                ) {

                    Icon(

                        imageVector =
                            Icons.AutoMirrored.Outlined.ArrowBack,

                        contentDescription =
                            "Back",

                        tint = Color.White
                    )
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                /*
                ================================
                TASK INFO
                ================================
                */

                FocusTimer(

                    taskTitle =

                        selectedTask?.title
                            ?: "Quick Focus",

                    sessionLabel =

                        if (uiState.isPaused)

                            "Session Paused"

                        else

                            "Focus Session"
                )

                Spacer(
                    modifier = Modifier.height(48.dp)
                )

                /*
                ================================
                TIMER RING
                ================================
                */

                AnimatedTimerRing(

                    progress =
                        uiState.progress,

                    timeText =
                        formattedTime,

                    modifier = Modifier
                        .size(280.dp)
                )
            }

            /*
            ====================================
            CONTROL SECTION
            ====================================
            */

            Column(

                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                /*
                ================================
                CONTROLS
                ================================
                */

                FocusControls(

                    isRunning =
                        uiState.isRunning,

                    isPaused =
                        uiState.isPaused,

                    onPauseResumeClick = {

                        /*
                        ========================
                        START FOCUS
                        ========================
                        */

                        selectedTask?.let { task ->

                            viewModel.startTaskFocus(
                                task
                            )
                        }

                        /*
                        nanti logic timer
                        pause/resume
                        */
                    },

                    onStopClick = {

                        viewModel.resetSession()

                        navController.popBackStack()
                    },

                    onExtendClick = {

                        viewModel.selectDuration(

                            (uiState.totalSeconds / 60)
                                    + 10
                        )
                    }
                )

                Spacer(
                    modifier = Modifier.height(34.dp)
                )
            }
        }
    }
}