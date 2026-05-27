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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.focusmate.presentation.theme.ButtonPrimary
import com.example.focusmate.presentation.theme.CardDark
import com.example.focusmate.presentation.theme.TextDark
import com.example.focusmate.presentation.theme.TextPrimary
import com.example.focusmate.presentation.theme.TextSecondary

@Composable
fun QuickFocusCompleteDialog(

    duration: Int,

    onDoneClick: () -> Unit
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
                vertical = 32.dp
            ),

        horizontalAlignment =
            Alignment.CenterHorizontally,

        verticalArrangement =
            Arrangement.Center
    ) {

        /*
        ====================================
        ICON
        ====================================
        */

        Icon(

            imageVector =
                Icons.Outlined.CheckCircle,

            contentDescription =
                "Completed",

            tint =
                Color(0xFFB1C4FF),

            modifier = Modifier
                .height(72.dp)
        )

        /*
        ====================================
        SPACE
        ====================================
        */

        Spacer(
            modifier = Modifier.height(22.dp)
        )

        /*
        ====================================
        TITLE
        ====================================
        */

        Text(

            text =
                "Focus Session Complete",

            color = TextPrimary,

            fontSize = 28.sp,

            fontWeight =
                FontWeight.ExtraBold,

            textAlign =
                TextAlign.Center
        )

        /*
        ====================================
        SPACE
        ====================================
        */

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        /*
        ====================================
        DESCRIPTION
        ====================================
        */

        Text(

            text =
                "Great job! You stayed focused for $duration minutes 🚀",

            color = TextSecondary,

            fontSize = 16.sp,

            lineHeight = 24.sp,

            textAlign =
                TextAlign.Center
        )

        /*
        ====================================
        SPACE
        ====================================
        */

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        /*
        ====================================
        DONE BUTTON
        ====================================
        */

        Button(

            onClick = {

                onDoneClick()
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

            Text(

                text = "Back to Focus",

                fontSize = 16.sp,

                fontWeight =
                    FontWeight.Bold
            )
        }
    }
}