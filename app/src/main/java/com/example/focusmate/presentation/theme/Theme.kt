package com.example.focusmate.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val FocusMateColorScheme =

    darkColorScheme(

        primary = PrimaryBlue,

        secondary = SecondaryBlue,

        background = BackgroundDark,

        surface = CardDark,

        onPrimary = TextPrimary,

        onSecondary = TextPrimary,

        onBackground = TextPrimary,

        onSurface = TextPrimary
    )

@Composable
fun FocusMateTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(

        colorScheme = FocusMateColorScheme,

        typography = Typography,

        content = content
    )
}