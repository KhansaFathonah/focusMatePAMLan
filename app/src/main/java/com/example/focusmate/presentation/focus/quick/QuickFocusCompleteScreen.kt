package com.example.focusmate.presentation.focus.quick

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.focusmate.presentation.components.StartFocus.QuickFocusCompleteDialog
import com.example.focusmate.presentation.navigation.Screen
import com.example.focusmate.presentation.theme.BackgroundDark

@Composable
fun QuickFocusCompleteScreen(

    navController: NavController,

    duration: Int
) {

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
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(
                    horizontal = 24.dp
                ),

            contentAlignment =
                Alignment.Center
        ) {

            QuickFocusCompleteDialog(

                duration = duration,

                onDoneClick = {

                    navController.navigate(
                        Screen.Focus.route
                    ) {

                        popUpTo(
                            Screen.Focus.route
                        ) {

                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}