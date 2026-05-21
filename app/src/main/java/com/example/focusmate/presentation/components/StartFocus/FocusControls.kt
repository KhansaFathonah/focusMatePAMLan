package com.example.focusmate.presentation.components.StartFocus

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.PauseCircle
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.focusmate.presentation.theme.ButtonDark
import com.example.focusmate.presentation.theme.ButtonPrimary
import com.example.focusmate.presentation.theme.TextDark
import com.example.focusmate.presentation.theme.TextPrimary
import com.example.focusmate.presentation.theme.TextSecondary

@Composable
fun FocusControls(

    isRunning: Boolean,

    onStopClick: () -> Unit,

    onAddTimeClick: () -> Unit
) {

    /*
    ====================================
    PAUSE BUTTON COLOR
    ====================================
    */

    val pauseButtonColor by
    animateColorAsState(

        targetValue =

            if (isRunning)

                ButtonDark

            else

                ButtonPrimary,

        animationSpec =
            tween(300),

        label = "pause_button"
    )

    /*
    ====================================
    TEXT COLOR
    ====================================
    */

    val pauseTextColor =

        if (isRunning)

            TextPrimary

        else

            TextDark

    /*
    ====================================
    BUTTON ROW
    ====================================
    */

    Row(

        modifier = Modifier
            .fillMaxWidth(),

        horizontalArrangement =
            Arrangement.spacedBy(14.dp)
    ) {

        /*
        ====================================
        PAUSE / RESUME BUTTON
        ====================================
        */

        Row(

            modifier = Modifier
                .weight(1f)
                .height(56.dp)
                .background(

                    color = pauseButtonColor,

                    shape =
                        RoundedCornerShape(18.dp)
                )
                .clickable {

                    onStopClick()
                }
                .padding(horizontal = 18.dp),

            horizontalArrangement =
                Arrangement.Center,

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            /*
            ====================================
            ICON
            ====================================
            */

            Icon(

                imageVector =

                    if (isRunning)

                        Icons.Outlined.PauseCircle

                    else

                        Icons.Outlined.PlayCircle,

                contentDescription =
                    "Pause Resume",

                tint =
                    pauseTextColor
            )

            /*
            ====================================
            SPACE
            ====================================
            */

            Spacer(
                modifier = Modifier.width(6.dp)
            )

            /*
            ====================================
            BUTTON TEXT
            ====================================
            */

            Text(

                text =

                    if (isRunning)

                        "Pause"

                    else

                        "Resume",

                color =
                    pauseTextColor,

                fontSize = 17.sp,

                fontWeight =
                    FontWeight.Bold
            )
        }

        /*
        ====================================
        ADD TIME BUTTON
        ====================================
        */

        Row(

            modifier = Modifier
                .weight(1.15f)
                .height(56.dp)
                .background(

                    color = ButtonPrimary,

                    shape =
                        RoundedCornerShape(18.dp)
                )
                .clickable {

                    onAddTimeClick()
                }
                .padding(horizontal = 18.dp),

            horizontalArrangement =
                Arrangement.Center,

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            /*
            ====================================
            ADD ICON
            ====================================
            */

            Icon(

                imageVector =
                    Icons.Outlined.Add,

                contentDescription =
                    "Add Time",

                tint =
                    TextDark
            )

            /*
            ====================================
            SPACE
            ====================================
            */

            Spacer(
                modifier = Modifier.width(6.dp)
            )

            /*
            ====================================
            ADD TIME TEXT
            ====================================
            */

            Text(

                text = "10 min",

                color =
                    TextDark,

                fontSize = 17.sp,

                fontWeight =
                    FontWeight.Bold
            )
        }
    }
}