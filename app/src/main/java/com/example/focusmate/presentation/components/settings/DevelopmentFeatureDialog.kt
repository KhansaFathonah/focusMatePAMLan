package com.example.focusmate.presentation.components.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Construction
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.focusmate.presentation.theme.ButtonPrimary
import com.example.focusmate.presentation.theme.CardDark
import com.example.focusmate.presentation.theme.PrimaryBlue
import com.example.focusmate.presentation.theme.TextPrimary
import com.example.focusmate.presentation.theme.TextSecondary

@Composable
fun DevelopmentFeatureDialog(

    title: String,

    message: String,

    onDismiss: () -> Unit
) {

    AlertDialog(

        onDismissRequest = onDismiss,

        containerColor = CardDark,

        shape =
            RoundedCornerShape(28.dp),

        title = null,

        text = {

            Column(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(

                        horizontal = 6.dp,
                        vertical = 2.dp
                    ),

                horizontalAlignment =
                    Alignment.CenterHorizontally,

                verticalArrangement =
                    Arrangement.Center
            ) {

                Surface(

                    modifier = Modifier
                        .size(74.dp),

                    shape = CircleShape,

                    color = PrimaryBlue
                ) {

                    Column(

                        horizontalAlignment =
                            Alignment.CenterHorizontally,

                        verticalArrangement =
                            Arrangement.Center
                    ) {

                        Icon(

                            imageVector =
                                Icons.Outlined.Construction,

                            contentDescription = null,

                            tint = ButtonPrimary,

                            modifier = Modifier
                                .size(34.dp)
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(22.dp)
                )

                Text(

                    text = title,

                    color = TextPrimary,

                    fontSize = 22.sp,

                    fontWeight =
                        FontWeight.Bold,

                    textAlign =
                        TextAlign.Center
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(

                    text = message,

                    color = TextSecondary,

                    fontSize = 14.sp,

                    lineHeight = 22.sp,

                    textAlign =
                        TextAlign.Center
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                TextButton(

                    onClick = onDismiss
                ) {

                    Text(

                        text = "Got It",

                        color = ButtonPrimary,

                        fontSize = 20.sp,

                        fontWeight =
                            FontWeight.SemiBold
                    )
                }
            }
        },

        confirmButton = {}
    )
}