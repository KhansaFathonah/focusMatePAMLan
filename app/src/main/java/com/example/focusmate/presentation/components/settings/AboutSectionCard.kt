package com.example.focusmate.presentation.components.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.focusmate.presentation.theme.BackgroundDark
import com.example.focusmate.presentation.theme.ButtonPrimary
import com.example.focusmate.presentation.theme.TextPrimary

@Composable
fun AboutSectionCard(

    title: String,

    content:
    @Composable () -> Unit
) {

    /*
    ====================================
    ROOT
    ====================================
    */

    Box(

        modifier = Modifier
            .fillMaxWidth()
    ) {

        /*
        ====================================
        CARD
        ====================================
        */

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp)
                .border(

                    BorderStroke(

                        width = 1.5.dp,

                        color = ButtonPrimary
                    ),

                    shape =
                        RoundedCornerShape(28.dp)
                )
                .padding(

                    horizontal = 18.dp,
                    vertical = 22.dp
                )
        ) {

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            content()
        }

        /*
        ====================================
        TITLE
        ====================================
        */

        Text(

            text = title,

            color = TextPrimary,

            fontSize = 20.sp,

            fontWeight =
                FontWeight.Bold,

            modifier = Modifier
                .padding(start = 18.dp)
                .background(
                    BackgroundDark
                )
                .padding(horizontal = 10.dp)
        )
    }
}