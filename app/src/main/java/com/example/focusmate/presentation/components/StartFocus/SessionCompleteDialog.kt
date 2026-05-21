package com.example.focusmate.presentation.components.StartFocus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.focusmate.domain.model.Task
import com.example.focusmate.presentation.theme.ButtonDark
import com.example.focusmate.presentation.theme.ButtonPrimary
import com.example.focusmate.presentation.theme.CardDark
import com.example.focusmate.presentation.theme.TextDark
import com.example.focusmate.presentation.theme.TextPrimary
import com.example.focusmate.presentation.theme.TextSecondary
import androidx.compose.foundation.layout.width

@Composable
fun SessionCompleteDialog(

    task: Task?,

    onMarkDone: () -> Unit,

    onContinueFocus: () -> Unit
) {

    Column(

        modifier = Modifier
            .fillMaxWidth()
            .background(

                color = CardDark,

                shape =
                    RoundedCornerShape(28.dp)
            )
            .padding(

                horizontal = 24.dp,
                vertical = 30.dp
            ),

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        /*
        ====================================
        EMOJI
        ====================================
        */

        Text(

            text = "🎉",

            fontSize = 52.sp
        )

        /*
        ====================================
        SPACE
        ====================================
        */

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        /*
        ====================================
        TITLE
        ====================================
        */

        Text(

            text = "Session Completed!",

            color = TextPrimary,

            fontSize = 28.sp,

            fontWeight =
                FontWeight.ExtraBold
        )

        /*
        ====================================
        SPACE
        ====================================
        */

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        /*
        ====================================
        SUBTITLE
        ====================================
        */

        Text(

            text = "Did you finish this task?",

            color = TextSecondary,

            fontSize = 16.sp
        )

        /*
        ====================================
        SPACE
        ====================================
        */

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        /*
        ====================================
        TASK CARD
        ====================================
        */

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .background(

                    color = Color(0xFF16284D),

                    shape =
                        RoundedCornerShape(20.dp)
                )
                .padding(18.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Text(

                text = "Working on",

                color = TextSecondary,

                fontSize = 13.sp
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(

                text =
                    task?.title
                        ?: "Focus Session",

                color = TextPrimary,

                fontSize = 18.sp,

                fontWeight =
                    FontWeight.Bold
            )
        }

        /*
        ====================================
        SPACE
        ====================================
        */

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        /*
        ====================================
        MARK DONE BUTTON
        ====================================
        */

        Button(

            onClick = {

                onMarkDone()
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),

            shape =
                RoundedCornerShape(18.dp),

            colors =
                ButtonDefaults.buttonColors(

                    containerColor =
                        ButtonPrimary,

                    contentColor =
                        TextDark
                )
        ) {

            Icon(

                imageVector =
                    Icons.Outlined.CheckCircle,

                contentDescription =
                    "Done"
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Text(

                text = "Yes, mark as done",

                fontSize = 16.sp,

                fontWeight =
                    FontWeight.Bold
            )
        }

        /*
        ====================================
        SPACE
        ====================================
        */

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        /*
        ====================================
        CONTINUE BUTTON
        ====================================
        */

        Button(

            onClick = {

                onContinueFocus()
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),

            shape =
                RoundedCornerShape(18.dp),

            colors =
                ButtonDefaults.buttonColors(

                    containerColor =
                        ButtonDark,

                    contentColor =
                        TextPrimary
                )
        ) {

            Icon(

                imageVector =
                    Icons.Outlined.PlayArrow,

                contentDescription =
                    "Continue"
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Text(

                text = "Continue Focus",

                fontSize = 16.sp,

                fontWeight =
                    FontWeight.Bold
            )
        }
    }
}