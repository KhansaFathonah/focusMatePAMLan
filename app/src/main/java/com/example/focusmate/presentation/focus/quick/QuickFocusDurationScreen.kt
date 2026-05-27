package com.example.focusmate.presentation.focus.quick

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.focusmate.presentation.components.StartFocus.SelectDuration
import com.example.focusmate.presentation.navigation.Screen
import com.example.focusmate.presentation.theme.BackgroundDark
import com.example.focusmate.presentation.theme.ButtonDark
import com.example.focusmate.presentation.theme.ButtonPrimary
import com.example.focusmate.presentation.theme.CardLight
import com.example.focusmate.presentation.theme.TextDark
import com.example.focusmate.presentation.theme.TextPrimary
import com.example.focusmate.presentation.theme.TextSecondary

@Composable
fun QuickFocusDurationScreen(

    navController: NavController
) {

    /*
    ====================================
    DURATION
    ====================================
    */

    var selectedDuration by remember {

        mutableStateOf<Int?>(null)
    }

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
            TITLE SECTION
            ====================================
            */

            Column {

                /*
                ====================================
                TITLE
                ====================================
                */

                Text(

                    text = "Quick Focus",

                    color = TextPrimary,

                    fontSize = 22.sp,

                    fontWeight =
                        FontWeight.ExtraBold
                )

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
                        "Choose your focus duration",

                    color =
                        TextSecondary,

                    fontSize = 14.sp,

                    fontWeight =
                        FontWeight.Medium
                )
            }
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
        QUICK INFO
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
            INFO TITLE
            ====================================
            */

            Text(

                text =
                    "Quick Focus Session",

                color = TextDark,

                fontSize = 18.sp,

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
            INFO DESCRIPTION
            ====================================
            */

            Text(

                text =
                    "Focus instantly without task tracking",

                color = TextSecondary,

                fontSize = 14.sp,

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
                selectedDuration,

            onDurationSelected = {

                selectedDuration = it
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

                selectedDuration?.let { duration ->

                    navController.navigate(

                        "${Screen.QuickFocusSession.route}/$duration"
                    )
                }
            },

            enabled =
                selectedDuration != null,

            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),

            shape =
                RoundedCornerShape(22.dp),

            colors =
                ButtonDefaults.buttonColors(

                    containerColor =

                        if (
                            selectedDuration != null
                        )

                            ButtonPrimary

                        else

                            ButtonDark,

                    contentColor =
                        TextDark
                )
        ) {

            Text(

                text =
                    "Start Focus Session",

                color =
                    if (
                        selectedDuration != null
                    )

                        TextDark

                    else

                        TextSecondary,

                fontSize = 14.sp,

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