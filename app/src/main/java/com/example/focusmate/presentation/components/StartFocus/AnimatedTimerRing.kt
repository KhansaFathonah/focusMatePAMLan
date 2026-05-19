package com.example.focusmate.presentation.components.startfocus

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedTimerRing(

    progress: Float,

    timeText: String,

    modifier: Modifier = Modifier
) {

    /*
    ====================================
    ANIMATED PROGRESS
    ====================================
    */

    val animatedProgress by

    animateFloatAsState(

        targetValue = progress,

        animationSpec = tween(

            durationMillis = 600,

            easing =
                FastOutSlowInEasing
        ),

        label = "Timer Progress"
    )

    /*
    ====================================
    CONTAINER
    ====================================
    */

    Box(

        modifier = modifier
            .size(288.dp),

        contentAlignment =
            Alignment.Center
    ) {

        /*
        ====================================
        TIMER RING
        ====================================
        */

        Canvas(

            modifier = Modifier
                .size(288.dp)

        ) {

            /*
            ================================
            STROKE WIDTH
            ================================
            */

            val strokeWidth = 10.dp.toPx()

            val gapAngle = 22f

            val segmentSweep = 90f - gapAngle

            /*
            ================================
            BACKGROUND RING
            ================================
            */

            repeat(4) { index ->

                drawArc(

                    color =
                        Color(0xFF25345A),

                    startAngle =
                        -90f + (index * 90f) + (gapAngle / 2f),

                    sweepAngle = segmentSweep,

                    useCenter = false,

                    topLeft = Offset(
                        strokeWidth / 2f,
                        strokeWidth / 2f
                    ),

                    size = Size(

                        width = size.width - strokeWidth,

                        height = size.height - strokeWidth
                    ),

                    style = Stroke(

                        width = strokeWidth,

                        cap = StrokeCap.Round
                    )
                )
            }

            /*
            ================================
            ACTIVE PROGRESS
            ================================
            */

            repeat(4) { index ->

                val segmentProgress =
                    ((animatedProgress * 4f) - index)
                        .coerceIn(0f, 1f)

                if (segmentProgress > 0f) {

                    drawArc(

                        color =
                            Color(0xFFB1C4FF),

                        startAngle =
                            -90f + (index * 90f) + (gapAngle / 2f),

                        sweepAngle =
                            segmentSweep * segmentProgress,

                        useCenter = false,

                        topLeft = Offset(
                            strokeWidth / 2f,
                            strokeWidth / 2f
                        ),

                        size = Size(

                            width = size.width - strokeWidth,

                            height = size.height - strokeWidth
                        ),

                        style = Stroke(

                            width = strokeWidth,

                            cap = StrokeCap.Round
                        )
                    )
                }
            }
        }

        /*
        ====================================
        CENTER CONTENT
        ====================================
        */

        Column(

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            /*
            ================================
            TIMER TEXT
            ================================
            */

            Text(

                text = timeText,

                color = Color.White,

                fontSize = 48.sp,

                fontWeight =
                    FontWeight.Light
            )

            /*
            ================================
            SUBTITLE
            ================================
            */

            Text(

                text = "Focus in progress...",

                color =
                    Color(0xFF7B86A8),

                fontSize = 14.sp
            )
        }
    }
}
