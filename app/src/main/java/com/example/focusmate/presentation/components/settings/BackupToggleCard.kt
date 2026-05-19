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
import com.example.focusmate.presentation.theme.TextDark

@Composable
fun BackupToggleCard(

    checked: Boolean,

    onCheckedChange:
        (Boolean) -> Unit
) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .background(

                color = ButtonPrimary,

                shape =
                    RoundedCornerShape(28.dp)
            )
            .padding(

                horizontal = 20.dp,
                vertical = 24.dp
            ),

        horizontalArrangement =
            Arrangement.SpaceBetween,

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Column {

            Text(

                text = "Auto Backup",

                color = TextDark,

                fontSize = 18.sp,

                fontWeight =
                    FontWeight.Bold
            )

            Text(

                text =
                    "Automatically backup your data",

                color = TextDark,

                fontSize = 15.sp
            )
        }

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
                        Color.White.copy(alpha = 0.35f)
                )
        )
    }
}