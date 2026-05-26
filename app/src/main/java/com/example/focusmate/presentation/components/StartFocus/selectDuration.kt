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
    val durations = listOf(

        15,
        25,
        45
    )

    Row(

        modifier = Modifier
            .fillMaxWidth(),

        horizontalArrangement =
            Arrangement.SpaceBetween
    ) {

        durations.forEach { duration ->

            DurationCard(

                duration = duration,

                label = "minutes",

                isSelected =
                    selectedDuration ==
                            duration,

                modifier = Modifier
                    .width(90.dp),

                onClick = {

                    onDurationSelected(
                        duration
                    )
                }
            )
        }
    }
}

@Composable
private fun DurationCard(

    duration: Int,
    label: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

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

    Box(
        modifier = modifier
            .height(80.dp)
            .scale(scale)
            .background(
                color =
                    backgroundColor,
                shape =
                    RoundedCornerShape(18.dp)
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

            Text(
                text =
                    duration.toString(),
                color =
                    if (isSelected)
                        TextDark
                    else
                        TextSecondary,
                fontSize = 25.sp,
                fontWeight =
                    FontWeight.ExtraBold
            )
            Spacer(
                modifier = Modifier.height(2.dp)
            )
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