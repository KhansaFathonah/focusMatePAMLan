package com.example.focusmate.presentation.components.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.focusmate.presentation.theme.ButtonPrimary
import com.example.focusmate.presentation.theme.PrimaryBlue
import com.example.focusmate.presentation.theme.TextDark

@Composable
fun BackupFrequencyCard(

    selectedFrequency: String,

    onFrequencySelected:
        (String) -> Unit
) {

    /*
    ====================================
    FREQUENCY CARD
    ====================================
    */

    Column(

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

        verticalArrangement =
            Arrangement.spacedBy(8.dp)
    ) {

        /*
        ====================================
        TITLE
        ====================================
        */

        Text(

            text = "Backup Frequency",

            color = TextDark,

            fontSize = 18.sp,

            fontWeight =
                FontWeight.Bold
        )

        /*
        ====================================
        RADIO OPTIONS
        ====================================
        */

        listOf(

            "Daily",
            "Weekly",
            "Monthly"

        ).forEach { frequency ->

            Row(

                modifier = Modifier
                    .fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                /*
                ====================================
                RADIO BUTTON
                ====================================
                */

                RadioButton(

                    selected =

                        selectedFrequency ==
                                frequency,

                    onClick = {

                        onFrequencySelected(
                            frequency
                        )
                    },

                    modifier = Modifier
                        .size(22.dp),

                    colors =
                        RadioButtonDefaults.colors(

                            selectedColor =
                                PrimaryBlue,

                            unselectedColor =
                                TextDark.copy(
                                    alpha = 0.35f
                                )
                        )
                )

                /*
                ====================================
                SPACE
                ====================================
                */

                androidx.compose.foundation.layout.Spacer(
                    modifier = Modifier.size(14.dp)
                )

                /*
                ====================================
                TEXT
                ====================================
                */

                Text(

                    text = frequency,

                    color = TextDark,

                    fontSize = 17.sp,

                    fontWeight =
                        FontWeight.Medium
                )
            }
        }
    }
}