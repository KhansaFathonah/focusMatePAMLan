package com.example.focusmate.presentation.settingsPage.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.focusmate.R
import com.example.focusmate.presentation.components.settings.AboutSectionCard
import com.example.focusmate.presentation.theme.BackgroundDark
import com.example.focusmate.presentation.theme.ButtonPrimary
import com.example.focusmate.presentation.theme.TextPrimary
import com.example.focusmate.presentation.theme.TextSecondary

@Composable
fun AboutScreen(

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

                    horizontal = 20.dp,
                    vertical = 16.dp
                )
                .navigationBarsPadding()
                .verticalScroll(
                    rememberScrollState()
                ),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

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

                    text = "About App",

                    color = TextPrimary,

                    fontSize = 20.sp,

                    fontWeight =
                        FontWeight.SemiBold
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
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
                    "FocusMate Logo",

                modifier = Modifier
                    .size(82.dp)
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            /*
            ====================================
            APP NAME
            ====================================
            */

            Row(

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(

                    text = "FOCUS",

                    color = TextPrimary,

                    fontSize = 31.sp,

                    fontWeight =
                        FontWeight.ExtraBold
                )

                Spacer(
                    modifier = Modifier.size(4.dp)
                )

                Text(

                    text = "MATE",

                    color = ButtonPrimary,

                    fontSize = 31.sp,

                    fontWeight =
                        FontWeight.ExtraBold
                )
            }

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            /*
            ====================================
            SUBTITLE
            ====================================
            */

            Text(

                text =
                    "Smart Productivity & Digital Wellness App",

                color = TextPrimary,

                fontSize = 14.sp,

                fontWeight =
                    FontWeight.Medium
            )

            Spacer(
                modifier = Modifier.height(2.dp)
            )

            /*
            ====================================
            VERSION
            ====================================
            */

            Text(

                text = "Version 1.0.0",

                color = ButtonPrimary,

                fontSize = 14.sp,

                fontWeight =
                    FontWeight.Medium
            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            /*
            ====================================
            ABOUT
            ====================================
            */

            AboutSectionCard(

                title = "About"
            ) {

                Text(

                    text =
                        "FocusMate helps you manage tasks, stay focused, and reduce digital distractions. Built with a calm and minimal design to support your productivity journey and digital wellness.",

                    color = TextPrimary,

                    fontSize = 14.sp,

                    lineHeight = 24.sp,

                    fontWeight =
                        FontWeight.Medium
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            /*
            ====================================
            FEATURES
            ====================================
            */

            AboutSectionCard(

                title = "Features"
            ) {

                Column(

                    verticalArrangement =
                        Arrangement.spacedBy(10.dp)
                ) {

                    Text(
                        text =
                            "• Task management with status tracking",
                        color = TextPrimary,
                        fontSize = 14.sp,
                        lineHeight = 22.sp
                    )

                    Text(
                        text =
                            "• Pomodoro focus timer",
                        color = TextPrimary,
                        fontSize = 14.sp,
                        lineHeight = 22.sp
                    )

                    Text(
                        text =
                            "• Focus time tracking per task",
                        color = TextPrimary,
                        fontSize = 14.sp,
                        lineHeight = 22.sp
                    )

                    Text(
                        text =
                            "• Progress statistics",
                        color = TextPrimary,
                        fontSize = 14.sp,
                        lineHeight = 22.sp
                    )

                    Text(
                        text =
                            "• Daily motivation quotes",
                        color = TextPrimary,
                        fontSize = 14.sp,
                        lineHeight = 22.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            /*
            ====================================
            CONNECT
            ====================================
            */

            AboutSectionCard(

                title = "Connect"
            ) {

                Column(

                    verticalArrangement =
                        Arrangement.spacedBy(14.dp)
                ) {

                    ConnectItem(

                        icon =
                            Icons.Outlined.Language,

                        text =
                            "focusmate.app"
                    )

                    ConnectItem(

                        icon =
                            Icons.Outlined.Email,

                        text =
                            "support@focusmate.app"
                    )

                    ConnectItem(

                        icon =
                            Icons.Outlined.Share,

                        text =
                            "github.com/focusmate"
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )
        }
    }
}

/*
========================================
CONNECT ITEM
========================================
*/

@Composable
fun ConnectItem(

    icon: androidx.compose.ui.graphics.vector.ImageVector,

    text: String
) {

    Row(

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Icon(

            imageVector = icon,

            contentDescription = text,

            tint = TextSecondary,

            modifier = Modifier
                .size(20.dp)
        )

        Spacer(
            modifier = Modifier.size(12.dp)
        )

        Text(

            text = text,

            color = TextSecondary,

            fontSize = 14.sp,

            fontWeight =
                FontWeight.Medium
        )
    }
}