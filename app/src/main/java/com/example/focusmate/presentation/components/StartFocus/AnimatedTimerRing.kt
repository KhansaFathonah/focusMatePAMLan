package com.example.focusmate.presentation.components.StartFocus

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedTimerRing(

    progress: Float,

    modifier: Modifier = Modifier
) {

    /*
    ====================================
    ANIMATED PROGRESS
    ====================================
    */

    val animatedProgress = remember {

        Animatable(progress)
    }

    LaunchedEffect(progress) {

        animatedProgress.animateTo(

            targetValue = progress,

            animationSpec = tween(

                durationMillis = 450,

                easing =
                    FastOutSlowInEasing
            )
        )
    }

    /*
    ====================================
    TIMER RING
    ====================================
    */

    Canvas(

        modifier = modifier
            .size(248.dp)
    ) {

        /*
        ====================================
        STROKE WIDTH
        ====================================
        */

        val strokeWidth =
            12.dp.toPx()

        /*
        ====================================
        BACKGROUND RING
        ====================================
        */

        drawArc(

            color =
                Color(0xFF1A2D5A),

            startAngle = -90f,

            sweepAngle = 360f,

            useCenter = false,

            style = Stroke(

                width = strokeWidth,

                cap = StrokeCap.Round
            )
        )

        /*
        ====================================
        ELAPSED TIME RING
        ====================================
        */

        drawArc(

            color =
                Color(0xFFB1C4FF),

            startAngle = -90f,

            sweepAngle =

                360f *
                        (1f - animatedProgress.value),

            useCenter = false,

            style = Stroke(

                width = strokeWidth,

                cap = StrokeCap.Round
            ),

            size = Size(

                width = size.width,

                height = size.height
            )
        )
    }
}