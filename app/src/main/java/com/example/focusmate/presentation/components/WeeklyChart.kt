package com.example.focusmate.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import com.example.focusmate.presentation.theme.ButtonPrimary

@Composable
fun WeeklyChart(
    values: List<Int>,
    highlightedIndex: Int,
    modifier: Modifier = Modifier
) {

    Canvas(
        modifier = modifier
    ) {

        val safeValues =
            values.take(7)
                .let { list ->

                    if (list.size == 7) {

                        list

                    } else {

                        list + List(7 - list.size) { 0 }
                    }
                }

        val maxValue =
            (safeValues.maxOrNull() ?: 1)
                .coerceAtLeast(1)
                .toFloat()

        val barWidth =
            size.width / 15f

        val gap =
            (size.width - (barWidth * safeValues.size)) /
                    (safeValues.size - 1).coerceAtLeast(1)

        safeValues.forEachIndexed { index, value ->

            val rawBarHeight =
                (value / maxValue) * (size.height - 28f)

            val barHeight =
                if (value > 0) {

                    rawBarHeight.coerceAtLeast(8f)

                } else {

                    0f
                }

            val x =
                index * (barWidth + gap)

            drawRoundRect(
                color =
                    if (index == highlightedIndex) {

                        ButtonPrimary

                    } else {

                        Color(0xFF637AC6)
                    },
                topLeft = Offset(
                    x = x,
                    y = size.height - barHeight - 22f
                ),
                size = Size(
                    width = barWidth,
                    height = barHeight
                ),
                cornerRadius = CornerRadius(8f, 8f)
            )
        }
    }
}
