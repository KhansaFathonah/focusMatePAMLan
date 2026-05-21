package com.example.focusmate.presentation.components.StartFocus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.focusmate.presentation.theme.ButtonPrimary
import com.example.focusmate.presentation.theme.CardDark
import com.example.focusmate.presentation.theme.TextDark
import com.example.focusmate.presentation.theme.TextPrimary
import com.example.focusmate.presentation.theme.TextSecondary

@Composable
fun QuickFocusDevelopmentDialog(

    onDismiss: () -> Unit
) {

    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xB3000000)
            ),

        contentAlignment =
            Alignment.Center
    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp)
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
                Alignment.CenterHorizontally,

            verticalArrangement =
                Arrangement.Center
        ) {

            /*
            ====================================
            EMOJI
            ====================================
            */

            Text(

                text = "🚧",

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

                text = "Coming Soon",

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
                modifier = Modifier.height(10.dp)
            )

            /*
            ====================================
            DESCRIPTION
            ====================================
            */

            Text(

                text =
                    "Quick Focus feature is currently under development and will be available in future updates.",

                color = TextSecondary,

                fontSize = 15.sp,

                lineHeight = 24.sp
            )

            /*
            ====================================
            SPACE
            ====================================
            */

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            /*
            ====================================
            BUTTON
            ====================================
            */

            Button(

                onClick = {

                    onDismiss()
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

                    text = "Understood",

                    fontSize = 16.sp,

                    fontWeight =
                        FontWeight.Bold
                )
            }
        }
    }
}