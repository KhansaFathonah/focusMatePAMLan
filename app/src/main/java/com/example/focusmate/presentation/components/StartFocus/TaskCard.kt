package com.example.focusmate.presentation.components.StartFocus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.focusmate.domain.model.Task
import com.example.focusmate.presentation.theme.ButtonDark
import com.example.focusmate.presentation.theme.TextPrimary
import com.example.focusmate.presentation.theme.TextSecondary

@Composable
fun TaskCard(

    task: Task?
) {

    Column(

        modifier = Modifier
            .fillMaxWidth()
            .background(

                color = ButtonDark,

                shape =
                    RoundedCornerShape(22.dp)
            )
            .padding(20.dp)
    ) {

        Text(

            text = "Working on",

            color = TextSecondary,

            fontSize = 14.sp
        )

        Text(

            text =
                task?.title
                    ?: "Quick Focus Session",

            color = TextPrimary,

            fontSize = 24.sp,

            fontWeight =
                FontWeight.Bold
        )
    }
}