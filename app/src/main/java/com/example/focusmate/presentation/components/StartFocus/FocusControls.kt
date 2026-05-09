package com.example.focusmate.presentation.components.startfocus

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FocusControls(

    isRunning: Boolean,

    isPaused: Boolean,

    onPauseResumeClick: () -> Unit,

    onStopClick: () -> Unit,

    onExtendClick: () -> Unit,

    modifier: Modifier = Modifier
) {

    /*
    ====================================
    CONTAINER
    ====================================
    */

    Row(

        modifier = modifier
            .fillMaxWidth(),

        horizontalArrangement =
            Arrangement.SpaceEvenly,

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        /*
        ====================================
        STOP BUTTON
        ====================================
        */

        CircleControlButton(

            backgroundColor =
                Color(0xFF25345A),

            icon = {

                Icon(

                    imageVector =
                        Icons.Outlined.Close,

                    contentDescription =
                        "Stop Session",

                    tint = Color.White
                )
            },

            onClick = onStopClick
        )

        /*
        ====================================
        PAUSE / RESUME BUTTON
        ====================================
        */

        Box(

            modifier = Modifier
                .size(92.dp)
                .background(

                    color =
                        Color(0xFFB1C4FF),

                    shape =
                        CircleShape
                )
                .clickable {

                    onPauseResumeClick()
                },

            contentAlignment =
                Alignment.Center
        ) {

            Icon(

                imageVector =

                    if (
                        isRunning &&
                        !isPaused
                    )

                        Icons.Outlined.Pause

                    else

                        Icons.Outlined.PlayArrow,

                contentDescription =
                    "Pause Resume",

                tint =
                    Color(0xFF13203A),

                modifier = Modifier
                    .size(42.dp)
            )
        }

        /*
        ====================================
        EXTEND BUTTON
        ====================================
        */

        CircleControlButton(

            backgroundColor =
                Color(0xFF25345A),

            icon = {

                Icon(

                    imageVector =
                        Icons.Outlined.Add,

                    contentDescription =
                        "Add Time",

                    tint = Color.White
                )
            },

            onClick = onExtendClick
        )
    }
}

/*
====================================
REUSABLE CONTROL BUTTON
====================================
*/

@Composable
private fun CircleControlButton(

    backgroundColor: Color,

    icon: @Composable () -> Unit,

    onClick: () -> Unit
) {

    Box(

        modifier = Modifier
            .size(68.dp)
            .background(

                color = backgroundColor,

                shape = RoundedCornerShape(22.dp)
            )
            .clickable {

                onClick()
            },

        contentAlignment =
            Alignment.Center
    ) {

        icon()
    }
}