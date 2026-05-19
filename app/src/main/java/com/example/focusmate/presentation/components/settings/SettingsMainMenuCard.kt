package com.example.focusmate.presentation.settingsPage.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.focusmate.presentation.theme.ButtonPrimary
import com.example.focusmate.presentation.theme.StatusInProgress
import com.example.focusmate.presentation.theme.TextDark
import com.example.focusmate.presentation.theme.TextPrimary

@Composable
fun SettingsMainMenuCard(

    title: String,

    icon: ImageVector,

    onClick: () -> Unit
) {

    /*
    ====================================
    MENU CARD
    ====================================
    */

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .background(

                color = ButtonPrimary,

                shape =
                    RoundedCornerShape(24.dp)
            )
            .clickable {

                onClick()
            }
            .padding(

                horizontal = 18.dp,
                vertical = 18.dp
            ),

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        /*
        ====================================
        ICON CONTAINER
        ====================================
        */

        Row(

            modifier = Modifier
                .background(

                    color = StatusInProgress,

                    shape =
                        RoundedCornerShape(16.dp)
                )
                .padding(10.dp),

            horizontalArrangement =
                Arrangement.Center,

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Icon(

                imageVector = icon,

                contentDescription = title,

                tint = TextPrimary,

                modifier = Modifier
                    .size(20.dp)
            )
        }

        Spacer(
            modifier = Modifier.padding(8.dp)
        )

        /*
        ====================================
        TITLE
        ====================================
        */

        Text(

            text = title,

            color = TextDark,

            fontSize = 16.sp,

            fontWeight =
                FontWeight.Medium
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        /*
        ====================================
        ARROW
        ====================================
        */

        Icon(

            imageVector =
                Icons.Outlined.KeyboardArrowRight,

            contentDescription =
                "Arrow",

            tint = TextDark
        )
    }
}