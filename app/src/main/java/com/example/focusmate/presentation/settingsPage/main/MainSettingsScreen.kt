package com.example.focusmate.presentation.settingsPage.main

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
import androidx.compose.material.icons.outlined.CloudQueue
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.focusmate.presentation.components.BottomNavbar
import com.example.focusmate.presentation.navigation.Screen
import com.example.focusmate.presentation.settingsPage.main.component.SettingsMainMenuCard
import com.example.focusmate.presentation.theme.BackgroundDark
import com.example.focusmate.presentation.theme.TextPrimary
import com.example.focusmate.presentation.theme.TextSecondary

@Composable
fun MainSettingsScreen(

    navController: NavController
) {

    /*
    ====================================
    SCREEN
    ====================================
    */

    Scaffold(

        containerColor =
            BackgroundDark

    ) { paddingValues ->

        /*
        ====================================
        ROOT CONTAINER
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
                    .padding(
                        paddingValues
                    )
                    .padding(

                        horizontal = 32.dp,
                        vertical = 24.dp
                    )
                    .navigationBarsPadding(),

                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                /*
                ====================================
                TITLE
                ====================================
                */

                Text(

                    text = "Settings",

                    color = TextPrimary,

                    fontSize = 32.sp,

                    fontWeight =
                        FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                /*
                ====================================
                SUBTITLE
                ====================================
                */

                Text(

                    text =
                        "Customize your experience",

                    color =
                        TextSecondary,

                    fontSize = 15.sp
                )

                Spacer(
                    modifier = Modifier.height(38.dp)
                )

                /*
                ====================================
                MENU SECTION
                ====================================
                */

                Column(

                    verticalArrangement =
                        Arrangement.spacedBy(18.dp)
                ) {

                    /*
                    ================================
                    NOTIFICATION
                    ================================
                    */

                    SettingsMainMenuCard(

                        title = "Notifications",

                        icon =
                            Icons.Outlined.Notifications,

                        onClick = {

                            navController.navigate(
                                Screen.Notification.route
                            )
                        }
                    )

                    /*
                    ================================
                    BACKUP
                    ================================
                    */

                    SettingsMainMenuCard(

                        title = "Backup & Sync",

                        icon =
                            Icons.Outlined.CloudQueue,

                        onClick = {

                            navController.navigate(
                                Screen.Backup.route
                            )
                        }
                    )

                    /*
                    ================================
                    ABOUT
                    ================================
                    */

                    SettingsMainMenuCard(

                        title = "About App",

                        icon =
                            Icons.Outlined.Info,

                        onClick = {

                            navController.navigate(
                                Screen.About.route
                            )
                        }
                    )
                }
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
}