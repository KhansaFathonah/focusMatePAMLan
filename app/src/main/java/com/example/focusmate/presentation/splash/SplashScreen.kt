package com.example.focusmate.presentation.splash

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.focusmate.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {

    LaunchedEffect(Unit) {

        delay(8000)

        navController.navigate("home") {
            popUpTo("splash") {
                inclusive = true
            }
        }
    }

    val infiniteTransition = rememberInfiniteTransition(
        label = "floating_blobs"
    )

    val blob1X by infiniteTransition.animateFloat(
        initialValue = -120f,
        targetValue = 150f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 7500,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blob1X"
    )

    val blob1Y by infiniteTransition.animateFloat(
        initialValue = -200f,
        targetValue = 180f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 8200,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blob1Y"
    )

    val blob2X by infiniteTransition.animateFloat(
        initialValue = 180f,
        targetValue = -140f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 9000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blob2X"
    )

    val blob2Y by infiniteTransition.animateFloat(
        initialValue = 220f,
        targetValue = -160f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 5500,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blob2Y"
    )

    val blob3X by infiniteTransition.animateFloat(
        initialValue = -180f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 6000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blob3X"
    )

    val blob3Y by infiniteTransition.animateFloat(
        initialValue = 140f,
        targetValue = -220f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 4700,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blob3Y"
    )

    val blueBlobX by infiniteTransition.animateFloat(
        initialValue = -150f,
        targetValue = 170f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 8500,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blueBlobX"
    )

    val blueBlobY by infiniteTransition.animateFloat(
        initialValue = 260f,
        targetValue = -220f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 9200,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blueBlobY"
    )

    val blob4X by infiniteTransition.animateFloat(
        initialValue = 220f,
        targetValue = -250f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 10000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blob4X"
    )

    val blob4Y by infiniteTransition.animateFloat(
        initialValue = -250f,
        targetValue = 220f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 9500,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blob4Y"
    )

    val blob5X by infiniteTransition.animateFloat(
        initialValue = -260f,
        targetValue = 180f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 11000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blob5X"
    )

    val blob5Y by infiniteTransition.animateFloat(
        initialValue = 200f,
        targetValue = -180f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 9700,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blob5Y"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C1A33)),
        contentAlignment = Alignment.Center
    ) {

        GlowBlob(
            x = blob1X,
            y = blob1Y,
            size = 260,
            color = Color(0xFF697688)
        )

        GlowBlob(
            x = blob2X,
            y = blob2Y,
            size = 220,
            color = Color(0xFF4D5A6E)
        )

        GlowBlob(
            x = blob3X,
            y = blob3Y,
            size = 280,
            color = Color.White
        )

        GlowBlob(
            x = blueBlobX,
            y = blueBlobY,
            size = 220,
            color = Color(0xFF375CCD)
        )

        GlowBlob(
            x = blob4X,
            y = blob4Y,
            size = 240,
            color = Color(0xFF4D5A6E)
        )

        GlowBlob(
            x = blob5X,
            y = blob5Y,
            size = 260,
            color = Color(0xFFFFFFFF)
        )

        Image(
            painter = painterResource(id = R.drawable.focusmate_logo),
            contentDescription = "FocusMate Logo",
            modifier = Modifier.size(240.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun GlowBlob(
    x: Float,
    y: Float,
    size: Int,
    color: Color
) {

    Box(
        modifier = Modifier
            .offset(
                x = x.dp,
                y = y.dp
            )
            .size(size.dp)
            .alpha(0.7f)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        color.copy(alpha = 0.95f),
                        color.copy(alpha = 0.45f),
                        Color.Transparent
                    )
                ),
                shape = CircleShape
            )
            .blur(80.dp)
    )
}