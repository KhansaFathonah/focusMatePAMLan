package com.example.focusmate.presentation.focus.withTask

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.focusmate.presentation.components.StartFocus.SelectDuration
import com.example.focusmate.presentation.focus.FocusViewModel
import com.example.focusmate.presentation.navigation.Screen
import com.example.focusmate.presentation.theme.BackgroundDark
import com.example.focusmate.presentation.theme.ButtonPrimary
import com.example.focusmate.presentation.theme.CardDark
import com.example.focusmate.presentation.theme.CardLight
import com.example.focusmate.presentation.theme.TextDark
import com.example.focusmate.presentation.theme.TextPrimary
import com.example.focusmate.presentation.theme.TextSecondary

@Composable
fun SelectDurationScreen(

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

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(
                BackgroundDark
            )
            .padding(horizontal = 28.dp),

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        /*
        ====================================
        TOP SPACE
        ====================================
        */

        Spacer(
            modifier = Modifier.height(26.dp)
        )

        /*
        ====================================
        TOP BAR
        ====================================
        */

        Row(

            modifier = Modifier
                .fillMaxWidth(),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            /*
            ====================================
            BACK BUTTON
            ====================================
            */

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

                    tint =
                        TextPrimary
                )
            }

            /*
            ====================================
            TITLE
            ====================================
            */

            Text(

                text = "Start Focus",

                color = TextPrimary,

                fontSize = 22.sp,

                fontWeight =
                    FontWeight.ExtraBold
            )
        }

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
        WORKING ON
        ====================================
        */

        Text(

            text = "Working on",

            modifier = Modifier
                .fillMaxWidth(),

            color = TextPrimary,

            fontSize = 18.sp,

            fontWeight =
                FontWeight.Bold
        )

        /*
        ====================================
        SPACE
        ====================================
        */

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        /*
        ====================================
        TASK CARD
        ====================================
        */

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .background(

                    color = CardLight,

                    shape =
                        RoundedCornerShape(24.dp)
                )
                .padding(

                    horizontal = 22.dp,
                    vertical = 20.dp
                )
        ) {

            /*
            ====================================
            TASK TITLE
            ====================================
            */

            Text(

                text =
                    uiState.selectedTask?.title
                        ?: "No Task Selected",

                color = TextDark,

                fontSize = 19.sp,

                fontWeight =
                    FontWeight.ExtraBold
            )

            /*
            ====================================
            SPACE
            ====================================
            */

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            /*
            ====================================
            DEADLINE
            ====================================
            */

            Text(

                text =

                    uiState.selectedTask?.deadline
                        ?.replace("|", " • ")
                        ?: "",

                color = TextSecondary,

                fontSize = 15.sp,

                fontWeight =
                    FontWeight.Medium
            )
        }

        /*
        ====================================
        SPACE
        ====================================
        */

        Spacer(
            modifier = Modifier.height(42.dp)
        )

        /*
        ====================================
        DURATION TITLE
        ====================================
        */

        Text(

            text = "Focus Duration",

            modifier = Modifier
                .fillMaxWidth(),

            color = TextPrimary,

            fontSize = 18.sp,

            fontWeight =
                FontWeight.Bold
        )

        /*
        ====================================
        SPACE
        ====================================
        */

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        /*
        ====================================
        DURATION SELECTOR
        ====================================
        */

        SelectDuration(

            selectedDuration =
                uiState.selectedDuration,

            onDurationSelected = {

                viewModel.selectDuration(it)
            }
        )

        /*
        ====================================
        SPACE
        ====================================
        */

        Spacer(
            modifier = Modifier.height(52.dp)
        )

        /*
        ====================================
        START BUTTON
        ====================================
        */

        Button(

            onClick = {

                viewModel.startFocusSession()

                navController.navigate(
                    Screen.ActiveSession.route
                )
            },

            enabled =
                uiState.selectedDuration != null,

            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp),

            shape =
                RoundedCornerShape(22.dp),

            colors =
                ButtonDefaults.buttonColors(

                    containerColor =

                        if (
                            uiState.selectedDuration != null
                        )

                            ButtonPrimary

                        else

                            CardDark,

                    contentColor =
                        TextDark
                )
        ) {

            Text(

                text =
                    "Start Focus Session",

                color =
                    if (
                        uiState.selectedDuration != null
                    )

                        TextDark

                    else

                        TextSecondary,

                fontSize = 18.sp,

                fontWeight =
                    FontWeight.ExtraBold
            )
        }

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
        FOOTER TEXT
        ====================================
        */

        Text(

            text =
                "Stay focused and track your productivity",

            color =
                TextSecondary,

            fontSize = 15.sp
        )
    }
}