package com.example.focusmate.presentation.components.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudQueue
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.focusmate.presentation.theme.TextPrimary
import com.example.focusmate.presentation.theme.TextSecondary

@Composable
fun BackupHeaderCard(

    lastBackup: String
) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .background(

                brush = Brush.linearGradient(

                    colors = listOf(

                        Color.White.copy(
                            alpha = 0.18f
                        ),

                        Color.White.copy(
                            alpha = 0.08f
                        )
                    )
                ),

                shape =
                    RoundedCornerShape(26.dp)
            )
            .border(

                BorderStroke(

                    width = 1.dp,

                    color =
                        Color.White.copy(
                            alpha = 0.40f
                        )
                ),

                shape =
                    RoundedCornerShape(26.dp)
            )
            .padding(

                horizontal = 20.dp,
                vertical = 20.dp
            ),

        horizontalArrangement =
            Arrangement.SpaceBetween,

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Column {

            Text(

                text = "Last Backup",

                color = TextPrimary,

                fontSize = 18.sp,

                fontWeight =
                    FontWeight.Bold
            )

            Text(

                text = lastBackup,

                color = TextSecondary,

                fontSize = 14.sp
            )
        }

        Icon(

            imageVector =
                Icons.Outlined.CloudQueue,

            contentDescription =
                "Backup",

            tint = TextPrimary
        )
    }
}