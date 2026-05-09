package com.example.focusmate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontFamily

@Composable
fun MotivationCard(

    quote: String
) {

    /*
    ==========================================
    MAIN CONTAINER
    ==========================================
    */

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)

            /*
            ==================================
            CARD SHAPE
            ==================================
            */

            .clip(
                RoundedCornerShape(30.dp)
            )

            /*
            ==================================
            GLASS BACKGROUND
            ==================================
            */

            .background(

                brush = Brush.horizontalGradient(

                    colors = listOf(

                        /*
                        LEFT
                        */

                        Color(0xFF5F6B82),

                        /*
                        CENTER
                        */

                        Color(0xFF566177),

                        /*
                        RIGHT
                        */

                        Color(0xFF5F6B82)
                    )
                )
            )

            /*
            ==================================
            SOFT OUTLINE
            ==================================
            */

            .border(

                width = 1.dp,

                brush = Brush.horizontalGradient(

                    colors = listOf(

                        /*
                        LEFT OUTLINE
                        */

                        Color.White.copy(
                            alpha = 0.55f
                        ),

                        /*
                        CENTER OUTLINE
                        */

                        Color.White.copy(
                            alpha = 0.00f
                        ),

                        /*
                        RIGHT OUTLINE
                        */

                        Color.White.copy(
                            alpha = 0.55f
                        )
                    )
                ),

                shape = RoundedCornerShape(
                    30.dp
                )
            )
    ) {

        /*
        ======================================
        LEFT STAR
        ======================================
        */

        Icon(

            imageVector =
                Icons.Outlined.AutoAwesome,

            contentDescription =
                "Sparkle Left",

            tint = Color.White,

            modifier = Modifier
                .align(
                    Alignment.TopStart
                )
                .padding(

                    start = 18.dp,
                    top = 16.dp
                )
                .size(22.dp)
        )

        /*
        ======================================
        RIGHT STAR
        ======================================
        */

        Icon(

            imageVector =
                Icons.Outlined.AutoAwesome,

            contentDescription =
                "Sparkle Right",

            tint = Color.White,

            modifier = Modifier
                .align(
                    Alignment.BottomEnd
                )
                .padding(

                    end = 18.dp,
                    bottom = 16.dp
                )
                .size(20.dp)
        )

        /*
        ======================================
        QUOTE TEXT
        ======================================
        */

        Text(

            text = "\"$quote\"",

            modifier = Modifier
                .align(
                    Alignment.Center
                )
                .padding(
                    horizontal = 42.dp
                ),

            color = Color.White,

            fontSize = 15.sp,

            fontWeight =
                FontWeight.Medium,

            /*
            ==================================
            ITALIC STYLE
            ==================================
            */

            fontStyle =
                FontStyle.Italic,

            /*
            ==================================
            AGAR ITALIC KELIHATAN
            ==================================
            */

            fontFamily =
                FontFamily.Serif,

            textAlign =
                TextAlign.Center,

            lineHeight = 24.sp
        )
    }
}