package com.example.focusmate.presentation.focus.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.TrackChanges
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.focusmate.R
import com.example.focusmate.presentation.components.BottomNavbar
import com.example.focusmate.presentation.components.StartFocus.FocusModeCard
import com.example.focusmate.presentation.focus.FocusViewModel
import com.example.focusmate.presentation.navigation.Screen
import com.example.focusmate.presentation.theme.BackgroundDark

@Composable
fun FocusModeScreen(

    navController: NavController,

    viewModel: FocusViewModel =
        hiltViewModel()
) {

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
                .padding(horizontal = 32.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            /*
            ====================================
            TOP SPACE
            ====================================
            */

            Spacer(
                modifier = Modifier.height(60.dp)
            )

            /*
            ====================================
            LOGO
            ====================================
            */

            Image(

                painter = painterResource(
                    id = R.drawable.focusmate_logo
                ),

                contentDescription =
                    "Focus Logo",

                modifier = Modifier
                    .size(108.dp)
            )

            /*
            ====================================
            SPACE
            ====================================
            */

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            /*
            ====================================
            TITLE
            ====================================
            */

            Row(

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(

                    text = "Focus ",

                    color = Color.White,

                    fontSize = 32.sp,

                    fontWeight =
                        FontWeight.ExtraBold
                )

                Text(

                    text = "Mode",

                    color =
                        Color(0xFFB1C4FF),

                    fontSize = 32.sp,

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
                modifier = Modifier.height(2.dp)
            )

            /*
            ====================================
            SUBTITLE
            ====================================
            */

            Text(

                text =
                    "Choose how you want to focus",

                color =
                    Color(0xFF697688),

                fontSize = 15.sp,

                fontWeight =
                    FontWeight.Normal
            )

            /*
            ====================================
            SPACE TO CARD
            ====================================
            */

            Spacer(
                modifier = Modifier.height(42.dp)
            )

            /*
            ====================================
            FOCUS WITH TASK
            ====================================
            */

            FocusModeCard(

                modifier = Modifier
                    .fillMaxWidth(),

                title = "Focus with Task",

                subtitle =
                    "Select a task and stay focused",

                icon =
                    Icons.Outlined.TrackChanges,

                onClick = {

                    navController.navigate(
                        Screen.SelectTask.route
                    )
                }
            )

            /*
            ====================================
            SPACE
            ====================================
            */

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            /*
            ====================================
            QUICK FOCUS
            ====================================
            */

            FocusModeCard(

                modifier = Modifier
                    .fillMaxWidth(),

                title = "Quick Focus",

                subtitle =
                    "Start focus session without task",

                icon =
                    Icons.Outlined.Bolt,

                onClick = {

                    navController.navigate(
                        Screen.QuickFocusDuration.route
                    )
                }
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
            BOTTOM TEXT
            ====================================
            */

            Text(

                text =
                    "Choose your focus mode to get started",

                color =
                    Color(0xFF697688),

                fontSize = 15.sp,

                fontWeight =
                    FontWeight.Normal
            )
        }

        /*
        ====================================
        BOTTOM NAVBAR
        ====================================
        */

        BottomNavbar(

            navController = navController,

            modifier = Modifier
                .align(
                    Alignment.BottomCenter
                )
                .padding(

                    horizontal = 20.dp,

                    vertical = 18.dp
                )
        )
    }
}