package com.example.focusmate.presentation.components.startfocus

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FocusTimer(

    taskTitle: String,

    sessionLabel: String,

    modifier: Modifier = Modifier
) {

    /*
    ====================================
    CONTAINER
    ====================================
    */

    Column(

        modifier = modifier,

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        /*
        ====================================
        TASK TITLE
        ====================================
        */

        Text(

            text = taskTitle,

            color = Color.White,

            fontSize = 24.sp,

            fontWeight =
                FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        /*
        ====================================
        SESSION LABEL
        ====================================
        */

        Text(

            text = sessionLabel,

            color =
                Color(0xFFB1C4FF),

            fontSize = 15.sp,

            fontWeight =
                FontWeight.Medium
        )
    }
}