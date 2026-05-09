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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatusBadge(

    status: String,

    modifier: Modifier = Modifier
) {

    /*
    ====================================
    STATUS COLOR
    ====================================
    */

    val backgroundColor = when (status) {

        "In Progress" ->
            Color(0xFF091633)

        "Completed" ->
            Color(0xFF2E7D32)

        "Overdue" ->
            Color(0xFFE05656)

        else ->
            Color(0xFF7B8194)
    }

    /*
    ====================================
    BADGE
    ====================================
    */

    Row(

        modifier = modifier
            .background(

                color = backgroundColor,

                shape =
                    RoundedCornerShape(50.dp)
            )
            .padding(

                horizontal = 14.dp,

                vertical = 7.dp
            ),

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Text(

            text = status,

            color = Color.White,

            fontSize = 12.sp,

            fontWeight =
                FontWeight.Medium
        )
    }
}