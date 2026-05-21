package com.example.focusmate.presentation.components.StartFocus

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import com.example.focusmate.presentation.theme.TextPrimary
import com.example.focusmate.presentation.theme.TextSecondary

@Composable
fun FocusTimer(

    remainingSeconds: Int
) {

    /*
    ====================================
    TIME FORMAT
    ====================================
    */

    val minutes =

        remainingSeconds / 60

    val seconds =

        remainingSeconds % 60

    val formattedTime =

        String.format(
            "%02d:%02d",
            minutes,
            seconds
        )

    /*
    ====================================
    TIMER
    ====================================
    */

    Column(

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Text(

            text = formattedTime,

            color = TextPrimary,

            fontSize = 64.sp,

            fontWeight =
                FontWeight.Light
        )

        Text(

            text =
                "Focus in progress...",

            color = TextSecondary,

            fontSize = 16.sp
        )
    }
}