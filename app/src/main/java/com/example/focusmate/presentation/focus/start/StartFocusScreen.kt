package com.example.focusmate.presentation.focus.start

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.StopCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.focusmate.presentation.components.startfocus.AnimatedTimerRing
import com.example.focusmate.presentation.focus.FocusViewModel
import com.example.focusmate.presentation.theme.BackgroundDark
import com.example.focusmate.presentation.theme.ButtonPrimary
import com.example.focusmate.presentation.theme.CardDark
import com.example.focusmate.presentation.theme.TextMuted
import com.example.focusmate.presentation.theme.TextSecondary

@Composable
fun StartFocusScreen(

    navController: NavController,

    viewModel: FocusViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    val selectedTask =
        uiState.selectedTask

    val minutes =
        uiState.remainingSeconds / 60

    val seconds =
        uiState.remainingSeconds % 60

    val formattedTime =
        String.format(
            "%02d:%02d",
            minutes,
            seconds
        )

    Scaffold(
        containerColor = BackgroundDark
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundDark)
                .padding(paddingValues)
                .padding(
                    horizontal = 33.dp,
                    vertical = 50.dp
                )
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            StartFocusTopBar(
                title =
                    if (uiState.isRunning) {
                        "Focus Mode"
                    } else {
                        "Start Focus"
                    },
                subtitle =
                    if (uiState.isRunning) {
                        "Stay Focused"
                    } else {
                        null
                    },
                centered = uiState.isRunning,
                onBackClick = {
                    navController.popBackStack()
                }
            )

            if (uiState.isRunning) {

                Spacer(
                    modifier = Modifier.height(58.dp)
                )

                FocusTaskPreview(
                    title =
                        selectedTask?.title ?: "Quick Focus",
                    subtitle = null,
                    showWorkingLabel = true
                )

                Spacer(
                    modifier = Modifier.height(72.dp)
                )

                AnimatedTimerRing(
                    progress = uiState.progress,
                    timeText = formattedTime,
                    modifier = Modifier.size(288.dp)
                )

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                ActiveFocusControls(
                    onStopClick = {
                        viewModel.stopCurrentSession()
                        navController.popBackStack()
                    },
                    onExtendClick = {
                        viewModel.extendDuration(10)
                    }
                )

            } else {

                if (selectedTask == null) {

                    Spacer(
                        modifier = Modifier.height(35.dp)
                    )

                    QuickFocusNotice()

                    Spacer(
                        modifier = Modifier.height(13.dp)
                    )

                } else {

                    Spacer(
                        modifier = Modifier.height(34.dp)
                    )

                    Text(
                        text = "Working on",
                        color = Color.White,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    FocusTaskPreview(
                        title = selectedTask.title,
                        subtitle = selectedTask.deadline,
                        showWorkingLabel = false
                    )

                    Spacer(
                        modifier = Modifier.height(33.dp)
                    )
                }

                Text(
                    text = "Focus Duration",
                    color = Color.White,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(
                    modifier = Modifier.height(15.dp)
                )

                DurationSelector(
                    selectedDuration = uiState.selectedDuration,
                    onSelectDuration = viewModel::selectDuration
                )

                Spacer(
                    modifier = Modifier.height(50.dp)
                )

                Button(
                    onClick = {
                        selectedTask?.let { task ->
                            viewModel.startTaskFocus(task)
                        } ?: viewModel.startQuickFocus()
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.72f)
                        .height(50.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonPrimary,
                        contentColor = BackgroundDark
                    )
                ) {

                    Text(
                        text = "Start Focus Session",
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(
                    modifier = Modifier.height(11.dp)
                )

                Text(
                    text = "Stay focused and track your productivity",
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 15.sp,
                    lineHeight = 24.sp
                )
            }
        }
    }
}

@Composable
private fun StartFocusTopBar(
    title: String,
    subtitle: String?,
    centered: Boolean,
    onBackClick: () -> Unit
) {

    if (centered) {

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )

                subtitle?.let {

                    Text(
                        text = it,
                        color = Color.White,
                        fontSize = 14.sp,
                        lineHeight = 24.sp
                    )
                }
            }
        }

    } else {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(24.dp)
            ) {

                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(
                modifier = Modifier.size(11.dp)
            )

            Text(
                text = title,
                color = Color.White,
                fontSize = 20.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun FocusTaskPreview(
    title: String,
    subtitle: String?,
    showWorkingLabel: Boolean
) {

    Surface(
        color = Color(0xFFF2F2F2),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(
                if (showWorkingLabel) {
                    88.dp
                } else {
                    88.dp
                }
            )
    ) {

        Column(
            modifier = Modifier.padding(
                horizontal = 18.dp,
                vertical = 17.dp
            )
        ) {

            if (showWorkingLabel) {

                Text(
                    text = "Working on",
                    color = Color(0xFF546071),
                    fontSize = 14.sp,
                    lineHeight = 27.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(
                    modifier = Modifier.height(0.dp)
                )
            }

            Text(
                text = title,
                color = BackgroundDark,
                fontSize = 18.sp,
                lineHeight = 27.sp,
                fontWeight = FontWeight.Bold
            )

            subtitle?.let {

                Text(
                    text = it,
                    color = Color(0xFF6B7280),
                    fontSize = 14.sp,
                    lineHeight = 27.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun QuickFocusNotice() {

    Surface(
        color = Color(0xFFF2F2F2),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = 2.dp,
            color = ButtonPrimary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {

        Text(
            text = "Quick focus session without task tracking",
            color = BackgroundDark,
            fontSize = 15.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(
                horizontal = 14.dp,
                vertical = 13.dp
            )
        )
    }
}

@Composable
private fun DurationSelector(
    selectedDuration: Int,
    onSelectDuration: (Int) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        listOf(15, 25, 45).forEach { duration ->

            val selected =
                selectedDuration == duration

            Column(
                modifier = Modifier
                    .width(100.dp)
                    .background(
                        color =
                            if (selected) {
                                ButtonPrimary
                            } else {
                                CardDark
                            },
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable {
                        onSelectDuration(duration)
                    }
                    .height(84.dp)
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "$duration",
                    color =
                        if (selected) {
                            BackgroundDark
                        } else {
                            TextSecondary
                        },
                    fontSize = 20.sp,
                    lineHeight = 32.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = "minutes",
                    color =
                        if (selected) {
                            Color(0xFF425071)
                        } else {
                            TextMuted
                        },
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun ActiveFocusControls(
    onStopClick: () -> Unit,
    onExtendClick: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        ActiveFocusButton(
            label = "Stop",
            icon = Icons.Outlined.StopCircle,
            modifier = Modifier.weight(1f),
            onClick = onStopClick
        )

        ActiveFocusButton(
            label = "10 min",
            icon = Icons.Outlined.Add,
            modifier = Modifier.weight(1f),
            onClick = onExtendClick
        )
    }

    Spacer(
        modifier = Modifier.height(18.dp)
    )

    Text(
        text = "* Active Session",
        color = Color.White,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun ActiveFocusButton(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Surface(
        onClick = onClick,
        color = CardDark,
        shape = RoundedCornerShape(14.dp),
        modifier = modifier.height(52.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {

            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = TextSecondary,
                modifier = Modifier.size(16.dp)
            )

            Spacer(
                modifier = Modifier.size(8.dp)
            )

            Text(
                text = label,
                color = TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
