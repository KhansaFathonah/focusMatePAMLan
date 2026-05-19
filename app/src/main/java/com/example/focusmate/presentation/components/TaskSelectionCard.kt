package com.example.focusmate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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

@Composable
fun TaskSelectionCard(

    task: Task,

    isSelected: Boolean,

    onClick: () -> Unit,

    modifier: Modifier = Modifier
) {

    /*
    ====================================
    CONTAINER
    ====================================
    */

    Box(

        modifier = modifier
            .fillMaxWidth()
    ) {

        /*
        ====================================
        BLUE SHADOW
        RIGHT + BOTTOM ONLY
        ====================================
        */

        if (isSelected) {

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(

                        start = 10.dp,
                        top = 8.dp,
                        end = 0.dp,
                        bottom = 0.dp
                    )
                    .height(121.dp)
                    .background(

                        color =
                            Color(0xFFB1C4FF)
                                .copy(alpha = 0.9f),

                        shape =
                            RoundedCornerShape(20.dp)
                    )
            )
        }

        /*
        ====================================
        MAIN CARD
        ====================================
        */

        Card(

            modifier = Modifier
                .fillMaxWidth()
                .clickable {

                    onClick()
                },

            shape = RoundedCornerShape(20.dp),

            border =

                if (isSelected)

                    androidx.compose.foundation.BorderStroke(

                        width = 1.5.dp,

                        color =
                            Color(0xFFB1C4FF)
                    )

                else null,

            colors = CardDefaults.cardColors(

                containerColor =
                    Color(0xFFDCDCDC)
            )
        ) {

            /*
            ====================================
            CONTENT
            ====================================
            */

            Column(

                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(
                        minHeight = 121.dp
                    )
                    .padding(

                        horizontal = 20.dp,
                        vertical = 19.dp
                    )
            ) {

                /*
                ====================================
                TOP SECTION
                ====================================
                */

                Row(

                    modifier = Modifier
                        .fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.SpaceBetween,

                    verticalAlignment =
                        Alignment.Top
                ) {

                    /*
                    ====================================
                    TEXT SECTION
                    ====================================
                    */

                    Column(

                        modifier = Modifier
                            .weight(1f)
                    ) {

                        /*
                        ====================================
                        TASK TITLE
                        ====================================
                        */

                        Text(

                            text = task.title,

                            color =
                                Color(0xFF1B2336),

                            fontSize = 18.sp,
                            lineHeight = 27.sp,

                            fontWeight =
                                FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        /*
                        ====================================
                        DEADLINE
                        ====================================
                        */

                        Text(

                            text = task.deadline,

                            color =
                                Color(0xFF5B647B),

                            fontSize = 14.sp,
                            lineHeight = 27.sp,

                            fontWeight =
                                FontWeight.Medium
                        )
                    }

                    /*
                    ====================================
                    CHECK ICON
                    ====================================
                    */

                    if (isSelected) {

                        Box(

                            modifier = Modifier
                                .size(28.dp)
                                .border(

                                    width = 2.dp,

                                    color =
                                        Color(0xFFB1C4FF),

                                    shape =
                                        CircleShape
                                ),

                            contentAlignment =
                                Alignment.Center
                        ) {

                            Icon(

                                imageVector =
                                    Icons.Outlined.Check,

                                contentDescription =
                                    "Selected",

                                tint =
                                    Color(0xFFB1C4FF),

                                modifier = Modifier
                                    .size(14.dp)
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(7.dp)
                )

                /*
                ====================================
                BOTTOM SECTION
                ====================================
                */

                Row(

                    modifier = Modifier
                        .fillMaxWidth(),

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    /*
                    ====================================
                    STATUS BADGE
                    ====================================
                    */

                    StatusBadge(
                        status = task.status
                    )
                }
            }
        }
    }
}
