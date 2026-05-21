package com.example.focusmate.presentation.focus.complete

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.focusmate.presentation.components.StartFocus.SessionCompleteDialog
import com.example.focusmate.presentation.focus.FocusViewModel
import com.example.focusmate.presentation.navigation.Screen

@Composable
fun SessionCompleteScreen(

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
    SCREEN
    ====================================
    */

    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(

                Color(0xCC020B1F)
            )
            .padding(24.dp),

        contentAlignment =
            Alignment.Center
    ) {

        /*
        ====================================
        COMPLETE DIALOG
        ====================================
        */

        SessionCompleteDialog(

            task =
                uiState.selectedTask,

            /*
            ====================================
            MARK AS DONE
            ====================================
            */

            onMarkDone = {

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
                }
            },

            /*
            ====================================
            CONTINUE FOCUS
            ====================================
            */

            onContinueFocus = {

                /*
                ================================
                SHOW EXTEND DIALOG
                ================================
                */

                viewModel.showExtendDialog()

                /*
                ================================
                RETURN TO SESSION
                ================================
                */

                navController.popBackStack()
            }
        )
    }
}