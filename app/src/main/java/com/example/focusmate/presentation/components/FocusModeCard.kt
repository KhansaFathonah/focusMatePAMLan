package com.example.focusmate.presentation.components

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FocusModeCard(

    title: String,

    subtitle: String,

    icon: ImageVector,

    onClick: () -> Unit,

    modifier: Modifier = Modifier
) {

    /*
    ====================================
    CARD
    ====================================
    */

    Card(

        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(28.dp)
            )
            .clickable {

                onClick()
            },

        shape = RoundedCornerShape(28.dp),

        colors = CardDefaults.cardColors(

            containerColor =
                Color(0xFFB1C4FF)
        ),

        elevation = CardDefaults.cardElevation(

            defaultElevation = 0.dp
        )
    ) {

        /*
        ====================================
        CONTENT
        ====================================
        */

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(

                    start = 22.dp,
                    end = 22.dp,
                    top = 22.dp,
                    bottom = 22.dp
                ),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            /*
            ====================================
            ICON CONTAINER
            ====================================
            */

            Box(

                modifier = Modifier
                    .size(56.dp)
                    .background(

                        color =
                            Color(0xFF0C1A33),

                        shape =
                            RoundedCornerShape(18.dp)
                    ),

                contentAlignment =
                    Alignment.Center
            ) {

                Icon(

                    imageVector = icon,

                    contentDescription = title,

                    tint = Color.White,

                    modifier = Modifier
                        .size(24.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(16.dp)
            )

            /*
            ====================================
            TEXT CONTENT
            ====================================
            */

            Column(

                modifier = Modifier
                    .weight(1f),

                verticalArrangement =
                    Arrangement.Center
            ) {

                /*
                ===============================
                TITLE
                ===============================
                */

                Text(

                    text = title,

                    color =
                        Color(0xFF1B2336),

                    fontSize = 18.sp,

                    fontWeight =
                        FontWeight.Bold,

                    maxLines = 1,

                    overflow =
                        TextOverflow.Ellipsis
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                /*
                ===============================
                SUBTITLE
                ===============================
                */

                Text(

                    text = subtitle,

                    color =
                        Color(0xFF4D5A6E),

                    fontSize = 14.sp,

                    fontWeight =
                        FontWeight.Medium,

                    lineHeight = 20.sp,

                    maxLines = 2,

                    overflow =
                        TextOverflow.Ellipsis
                )
            }
        }
    }
}