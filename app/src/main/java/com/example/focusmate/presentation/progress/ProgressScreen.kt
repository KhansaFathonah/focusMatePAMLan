package com.example.focusmate.presentation.progress

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ShowChart
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.TrackChanges
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.focusmate.presentation.components.BottomNavbar
import com.example.focusmate.presentation.navigation.Screen
import com.example.focusmate.presentation.theme.BackgroundDark
import com.example.focusmate.presentation.theme.ButtonPrimary
import com.example.focusmate.presentation.theme.CardDark
import com.example.focusmate.presentation.theme.TextMuted
import com.example.focusmate.presentation.theme.TextSecondary

@Composable
fun ProgressScreen(

    navController: NavController,

    viewModel: ProgressViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    val completedTasks =
        if (uiState.totalTasks == 0) 1 else uiState.completedTasks

    val totalTasks =
        if (uiState.totalTasks == 0) 4 else uiState.totalTasks

    val totalMinutes =
        if (uiState.totalFocusMinutes == 0) 125 else uiState.totalFocusMinutes

    val totalSessions =
        if (uiState.totalSessions == 0) 14 else uiState.totalSessions

    val weekTasks =
        if (uiState.totalTasks == 0) 1 else uiState.weeklyMinutes.count { it > 0 }

    val weeklyValues =
        if (uiState.sessions.isEmpty()) {
            listOf(65, 25, 55, 55, 70, 58, 95)
        } else {
            uiState.weeklyMinutes
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 33.dp),
            contentPadding = PaddingValues(
                top = 54.dp,
                bottom = 132.dp
            )
        ) {

            item {
                ProgressHeader(
                    onHistoryClick = {
                        navController.navigate(Screen.History.route)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(25.dp))
                TasksCompletedCard(
                    completed = completedTasks,
                    total = totalTasks
                )
            }

            item {
                Spacer(modifier = Modifier.height(26.dp))
                ProgressStatCard(
                    icon = Icons.Outlined.Schedule,
                    title = "Total Focus\nTime",
                    value = "$totalMinutes",
                    subtitle = "All time",
                    tall = true
                )
            }

            item {
                Spacer(modifier = Modifier.height(15.dp))
                ProgressStatCard(
                    icon = Icons.Outlined.TrackChanges,
                    title = "Focus Sessions",
                    value = "$totalSessions",
                    subtitle = "Completed sessions"
                )
            }

            item {
                Spacer(modifier = Modifier.height(15.dp))
                ProgressStatCard(
                    icon = Icons.Outlined.CheckCircle,
                    title = "This Week",
                    value = "$weekTasks",
                    subtitle = "Tasks completed"
                )
            }

            item {
                Spacer(modifier = Modifier.height(15.dp))
                WeeklyActivityCard(
                    values = weeklyValues
                )
            }

            item {
                Spacer(modifier = Modifier.height(15.dp))
                OverallCard(
                    completedTasks = completedTasks,
                    totalMinutes = totalMinutes
                )
            }
        }

        BottomNavbar(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    horizontal = 30.dp,
                    vertical = 30.dp
                )
        )
    }
}

@Composable
private fun ProgressHeader(
    onHistoryClick: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {

        Column {
            Text(
                text = "Your Progress",
                color = Color.White,
                fontSize = 30.sp,
                lineHeight = 45.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = "Track your productivity\njourney",
                color = Color.White,
                fontSize = 15.sp,
                lineHeight = 24.sp
            )
        }

        Surface(
            onClick = onHistoryClick,
            color = ButtonPrimary,
            shape = RoundedCornerShape(13.dp),
            modifier = Modifier.padding(top = 31.dp)
        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = 7.dp,
                    vertical = 5.dp
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.History,
                    contentDescription = "History",
                    tint = BackgroundDark,
                    modifier = Modifier.size(10.dp)
                )
                Text(
                    text = "History",
                    color = BackgroundDark,
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun TasksCompletedCard(
    completed: Int,
    total: Int
) {

    val progress =
        if (total == 0) 0f else completed.toFloat() / total.toFloat()

    Surface(
        color = CardDark,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(164.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Tasks Completed",
                        color = Color.White,
                        fontSize = 18.sp,
                        lineHeight = 30.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "Overall progress",
                        color = TextMuted,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "$completed",
                        color = Color.White,
                        fontSize = 30.sp,
                        lineHeight = 36.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "of $total",
                        color = TextMuted,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp),
                color = ButtonPrimary,
                trackColor = Color(0xFF394871)
            )

            Text(
                text = "${(progress * 100).toInt()}% Complete",
                color = TextSecondary,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
private fun ProgressStatCard(
    icon: ImageVector,
    title: String,
    value: String,
    subtitle: String,
    tall: Boolean = false
) {

    Surface(
        color = CardDark,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(if (tall) 114.dp else 88.dp)
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 20.dp,
                vertical = 20.dp
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color(0xFF31436F),
                        shape = RoundedCornerShape(18.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = ButtonPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 18.sp,
                    lineHeight = 27.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = subtitle,
                    color = TextMuted,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }

            Text(
                text = value,
                color = Color.White,
                fontSize = 24.sp,
                lineHeight = 32.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
private fun WeeklyActivityCard(
    values: List<Int>
) {

    val days =
        listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

    Surface(
        color = CardDark,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(331.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ShowChart,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Weekly Activity",
                    color = Color.White,
                    fontSize = 18.sp,
                    lineHeight = 27.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                val maxValue =
                    (values.maxOrNull() ?: 1).coerceAtLeast(1).toFloat()
                val barWidth = size.width / 15f
                val gap =
                    (size.width - (barWidth * values.size)) /
                            (values.size - 1).coerceAtLeast(1)

                values.forEachIndexed { index, value ->
                    val barHeight =
                        (value / maxValue) * (size.height - 28f)
                    val x = index * (barWidth + gap)
                    drawRoundRect(
                        color =
                            if (index == values.lastIndex) {
                                ButtonPrimary
                            } else {
                                Color(0xFF637AC6)
                            },
                        topLeft = Offset(
                            x = x,
                            y = size.height - barHeight - 22f
                        ),
                        size = Size(
                            width = barWidth,
                            height = barHeight
                        ),
                        cornerRadius = CornerRadius(8f, 8f)
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                days.forEach { day ->
                    Text(
                        text = day,
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun OverallCard(
    completedTasks: Int,
    totalMinutes: Int
) {

    Surface(
        color = Color(0xFF31436F),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(108.dp)
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 20.dp,
                vertical = 20.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Great Work!",
                color = Color.White,
                fontSize = 15.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "You've completed $completedTasks task and focused for $totalMinutes minute",
                color = TextSecondary,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}
