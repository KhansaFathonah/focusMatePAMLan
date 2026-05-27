package com.example.focusmate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatusBadge(
    status: String,
    modifier: Modifier = Modifier
) {

    val normalizedStatus =
        status.trim()

    val backgroundColor =
        when (normalizedStatus) {

            "Completed" ->
                Color(0xFF2E7D32)

            "Overdue" ->
                Color(0xFFE05656)

            "Paused" ->
                Color(0xFFFFC857)

            "Not Started" ->
                Color(0xFF7B8194)

            else ->
                Color(0xFF7B8194)
        }

    val textColor =
        when (normalizedStatus) {

            "Paused" ->
                Color(0xFF1B2336)

            else ->
                Color.White
        }

    Row(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(50.dp)
            )
            .padding(
                horizontal = 14.dp,
                vertical = 7.dp
            ),

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Text(
            text = normalizedStatus,

            color = textColor,

            fontSize = 12.sp,

            fontWeight =
                FontWeight.Medium,

            maxLines = 1,

            overflow =
                TextOverflow.Ellipsis
        )
    }
}