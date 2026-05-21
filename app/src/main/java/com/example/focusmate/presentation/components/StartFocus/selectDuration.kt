package com.example.focusmate.presentation.components.StartFocus

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.focusmate.presentation.theme.ButtonDark
import com.example.focusmate.presentation.theme.ButtonPrimary
import com.example.focusmate.presentation.theme.TextDark
import com.example.focusmate.presentation.theme.TextSecondary

@Composable
fun SelectDuration(

    selectedDuration: Int?,

    onDurationSelected:
        (Int) -> Unit
) {

    /*
    ====================================
    NORMAL DURATIONS
    ====================================
    */

    val normalDurations = listOf(

        15,
        25,
        45
    )

    /*
    ====================================
    MAIN COLUMN
    ====================================
    */

    Column(

        modifier = Modifier
            .fillMaxWidth()
    ) {

        /*
        ====================================
        DEMO DURATION
        ====================================
        */

        DurationCard(

            duration = 10,

            label = "seconds",

            isSelected =
                selectedDuration == 10,

            modifier = Modifier
                .fillMaxWidth(),

            onClick = {

                onDurationSelected(10)
            }
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
        NORMAL ROW
        ====================================
        */

        Row(

            modifier = Modifier
                .fillMaxWidth(),

            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {

            normalDurations.forEach { duration ->

                DurationCard(

                    duration = duration,

                    label = "minutes",

                    isSelected =
                        selectedDuration ==
                                duration,

                    modifier = Modifier
                        .width(102.dp),

                    onClick = {

                        onDurationSelected(
                            duration
                        )
                    }
                )
            }
        }
    }
}

/*
========================================
DURATION CARD
========================================
*/

@Composable
private fun DurationCard(

    duration: Int,

    label: String,

    isSelected: Boolean,

    modifier: Modifier = Modifier,

    onClick: () -> Unit
) {

    /*
    ====================================
    ANIMATION
    ====================================
    */

    val backgroundColor by
    animateColorAsState(

        targetValue =

            if (isSelected)

                ButtonPrimary

            else

                ButtonDark,

        animationSpec =
            tween(300),

        label = "duration_color"
    )

    val scale by
    animateFloatAsState(

        targetValue =

            if (isSelected)

                1.03f

            else

                1f,

        animationSpec =
            tween(300),

        label = "duration_scale"
    )

    /*
    ====================================
    CARD
    ====================================
    */

    Box(

        modifier = modifier
            .height(102.dp)
            .scale(scale)
            .background(

                color =
                    backgroundColor,

                shape =
                    RoundedCornerShape(24.dp)
            )
            .clickable {

                onClick()
            }
            .padding(horizontal = 10.dp),

        contentAlignment =
            Alignment.Center
    ) {

        Column(

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            /*
            ====================================
            DURATION TEXT
            ====================================
            */

            Text(

                text =
                    duration.toString(),

                color =

                    if (isSelected)

                        TextDark

                    else

                        TextSecondary,

                fontSize = 34.sp,

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
            LABEL
            ====================================
            */

            Text(

                text = label,

                color =

                    if (isSelected)

                        TextDark

                    else

                        TextSecondary,

                fontSize = 14.sp,

                fontWeight =
                    FontWeight.Medium
            )
        }
    }
}