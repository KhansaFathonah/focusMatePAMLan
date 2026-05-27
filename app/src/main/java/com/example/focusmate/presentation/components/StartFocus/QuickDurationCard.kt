package com.example.focusmate.presentation.components.StartFocus

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuickDurationCard(

    minutes: Int,

    isSelected: Boolean,

    onClick: () -> Unit
) {

    /*
    ====================================
    ANIMATED COLORS
    ====================================
    */

    val backgroundColor by
    animateColorAsState(

        targetValue =

            if (isSelected) {

                Color(0xFFB1C4FF)

            } else {

                Color(0xFF1B2A52)
            },

        label = "background_color"
    )

    val textColor by
    animateColorAsState(

        targetValue =

            if (isSelected) {

                Color(0xFF091633)

            } else {

                Color.White
            },

        label = "text_color"
    )

    val subtitleColor by
    animateColorAsState(

        targetValue =

            if (isSelected) {

                Color(0xFF23345F)

            } else {

                Color(0xFF9AA6C6)
            },

        label = "subtitle_color"
    )

    /*
    ====================================
    ELEVATION
    ====================================
    */

    val elevation by
    animateDpAsState(

        targetValue =

            if (isSelected) {

                18.dp

            } else {

                0.dp
            },

        animationSpec =
            spring(),

        label = "elevation"
    )

    /*
    ====================================
    CARD
    ====================================
    */

    Box(

        modifier = Modifier
            .width(92.dp)
            .height(118.dp)
            .shadow(

                elevation = elevation,

                shape =
                    RoundedCornerShape(24.dp),

                ambientColor =
                    Color(0xFFB1C4FF),

                spotColor =
                    Color(0xFFB1C4FF)
            )
            .background(

                color = backgroundColor,

                shape =
                    RoundedCornerShape(24.dp)
            )
            .border(

                width =

                    if (isSelected) {

                        2.dp

                    } else {

                        0.dp
                    },

                color =

                    if (isSelected) {

                        Color(0xFFDDE5FF)

                    } else {

                        Color.Transparent
                    },

                shape =
                    RoundedCornerShape(24.dp)
            )
            .clickable {

                onClick()
            },

        contentAlignment =
            Alignment.Center
    ) {

        /*
        ====================================
        CONTENT
        ====================================
        */

        Column(

            horizontalAlignment =
                Alignment.CenterHorizontally,

            verticalArrangement =
                Arrangement.Center
        ) {

            /*
            ====================================
            MINUTES
            ====================================
            */

            Text(

                text = "$minutes",

                color = textColor,

                fontSize = 36.sp,

                fontWeight =
                    FontWeight.Bold
            )

            /*
            ====================================
            SPACE
            ====================================
            */

            Text(

                text = "minutes",

                color = subtitleColor,

                fontSize = 15.sp,

                fontWeight =
                    FontWeight.Medium
            )
        }
    }
}