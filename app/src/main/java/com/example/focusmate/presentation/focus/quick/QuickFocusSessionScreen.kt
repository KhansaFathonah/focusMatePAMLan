package com.example.focusmate.presentation.focus.quick

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.focusmate.presentation.components.StartFocus.AnimatedTimerRing
import com.example.focusmate.presentation.components.StartFocus.FocusControls
import com.example.focusmate.presentation.components.StartFocus.FocusTimer
import com.example.focusmate.presentation.focus.FocusViewModel
import com.example.focusmate.presentation.navigation.Screen
import com.example.focusmate.presentation.theme.BackgroundDark
import com.example.focusmate.presentation.theme.TextPrimary
import com.example.focusmate.presentation.theme.TextSecondary

@Composable
fun QuickFocusSessionScreen(

    navController: NavController,

    duration: Int,

    viewModel: FocusViewModel =
        hiltViewModel()
) {

    val uiState by
    viewModel.uiState.collectAsState()

    BackHandler(
        enabled = true
    ) {

    }

    LaunchedEffect(Unit) {

        viewModel.setQuickFocus(true)

        viewModel.selectDuration(
            duration
        )

        viewModel.startFocusSession()
    }

    LaunchedEffect(
        uiState.isCompleted
    ) {

        if (uiState.isCompleted) {

            navController.navigate(

                "${Screen.QuickFocusComplete.route}/$duration"
            ) {

                popUpTo(
                    Screen.Focus.route
                ) {

                    inclusive = false
                }

                launchSingleTop = true
            }
        }
    }

    DisposableEffect(Unit) {

        onDispose {

            viewModel.resetSession()
        }
    }

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
                .padding(
                    paddingValues
                )
        ) {

            Column(

                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(horizontal = 24.dp),

                horizontalAlignment =
                    Alignment.CenterHorizontally,

                verticalArrangement =
                    Arrangement.Top
            ) {

                Spacer(
                    modifier = Modifier.height(34.dp)
                )

                Text(

                    text = "Quick Focus",

                    color = TextPrimary,

                    fontSize = 28.sp,

                    fontWeight =
                        FontWeight.ExtraBold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(

                    text =
                        "Stay Focused",

                    color =
                        TextSecondary,

                    fontSize = 15.sp,

                    fontWeight =
                        FontWeight.Medium
                )

                Spacer(
                    modifier = Modifier.height(88.dp)
                )

                Box(

                    contentAlignment =
                        Alignment.Center
                ) {

                    AnimatedTimerRing(

                        progress =
                            uiState.progress
                    )

                    FocusTimer(

                        remainingSeconds =
                            uiState.remainingSeconds,

                        isPaused =
                            !uiState.isRunning
                    )
                }

                Spacer(
                    modifier = Modifier.height(88.dp)
                )

                FocusControls(

                    isRunning =
                        uiState.isRunning,

                    onStopClick = {

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

                Spacer(
                    modifier = Modifier.height(28.dp)
                )

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
        }
    }
}