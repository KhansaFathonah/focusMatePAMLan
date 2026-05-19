package com.example.focusmate.presentation.components.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.focusmate.presentation.theme.BackgroundDark
import com.example.focusmate.presentation.theme.ButtonPrimary
import com.example.focusmate.presentation.theme.CardDark
import com.example.focusmate.presentation.theme.TextDark
import com.example.focusmate.presentation.theme.TextPrimary
import com.example.focusmate.presentation.theme.TextSecondary

@Composable
fun NotificationToggleCard(

    title: String,

    subtitle: String,

    checked: Boolean,

    onCheckedChange:
        (Boolean) -> Unit
) {

    /*
    ====================================
    DYNAMIC COLORS
    ====================================
    */

    val backgroundColor =

        if (checked)

            ButtonPrimary

        else

            CardDark.copy(
                alpha = 0.96f
            )

    val titleColor =

        if (checked)

            TextDark

        else

            TextPrimary

    val subtitleColor =

        if (checked)

            TextDark.copy(
                alpha = 0.78f
            )

        else

            TextSecondary

    /*
    ====================================
    CARD
    ====================================
    */

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .background(

                color = backgroundColor,

                shape =
                    RoundedCornerShape(24.dp)
            )
            .padding(

                horizontal = 20.dp,
                vertical = 18.dp
            ),

        horizontalArrangement =
            Arrangement.SpaceBetween,

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        /*
        ====================================
        TEXT CONTENT
        ====================================
        */

        Column(

            modifier = Modifier
                .weight(1f)
        ) {

            Text(

                text = title,

                color = titleColor,

                fontSize = 17.sp,

                fontWeight =
                    FontWeight.Bold
            )

            Text(

                text = subtitle,

                color = subtitleColor,

                fontSize = 14.sp,

                lineHeight = 20.sp
            )
        }

        /*
        ====================================
        SWITCH
        ====================================
        */

        Switch(

            checked = checked,

            onCheckedChange =
                onCheckedChange,

            colors =
                SwitchDefaults.colors(

                    checkedThumbColor =
                        Color.White,

                    checkedTrackColor =
                        BackgroundDark,

                    uncheckedThumbColor =
                        Color.White,

                    uncheckedTrackColor =
                        Color.White.copy(
                            alpha = 0.28f
                        )
                )
        )
    }
}