package com.example.focusmate.presentation.history

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.focusmate.domain.model.FocusSession
import com.example.focusmate.presentation.components.StatusBadge
import com.example.focusmate.presentation.theme.BackgroundDark

private val HistoryPanel =
    Color(0x33212E4E)

private val HistoryBorder =
    Color(0x667284B2)

private val HistoryAccent =
    Color(0xFFB1C4FF)

private val HistoryText =
    Color(0xFFDAE1F1)

private val HistoryMuted =
    Color(0x99DAE1F1)

private val HistoryMutedSoft =
    Color(0x80DAE1F1)

private val HistoryTabsBg =
    Color(0xFF7284B2)

@Composable
fun HistoryScreen(

    navController: NavController,

    viewModel: HistoryViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    val displaySessions =
        remember(uiState.sessions) {
            uiState.sessions
                .filter { session ->
                    session.isCompleted
                }
        }

    val totalMinutes =
        displaySessions.sumOf { session ->
            session.durationMinutes
        }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(horizontal = 33.dp),
        contentPadding = PaddingValues(
            top = 50.dp,
            bottom = 44.dp
        )
    ) {

        item {

            HistoryTopBar(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        item {

            Spacer(
                modifier = Modifier.height(36.dp)
            )

            HistorySummary(
                sessions = displaySessions.size,
                totalMinutes = totalMinutes
            )
        }

        item {

            Spacer(
                modifier = Modifier.height(33.dp)
            )

            HistoryTabs(
                selected = uiState.selectedTab,
                onSelect = viewModel::selectTab
            )
        }

        item {

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            TodayHeader()
        }

        items(
            items = displaySessions,
            key = { session ->
                session.id
            }
        ) { session ->

            Spacer(
                modifier = Modifier.height(13.dp)
            )

            HistorySessionCard(
                session = session
            )
        }
    }
}

@Composable
private fun HistoryTopBar(
    onBackClick: () -> Unit
) {

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
            modifier = Modifier.width(11.dp)
        )

        Text(
            text = "Focus History",
            color = Color.White,
            fontSize = 20.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun HistorySummary(
    sessions: Int,
    totalMinutes: Int
) {

    Surface(
        color = HistoryPanel,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = HistoryBorder
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 49.dp,
                    vertical = 20.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            SummaryValue(
                value = "$sessions",
                label = "Sessions",
                modifier = Modifier.width(58.dp)
            )

            Canvas(
                modifier = Modifier
                    .width(1.dp)
                    .height(48.dp)
            ) {

                drawLine(
                    color = Color(0x1ADAE1F1),
                    start = Offset(
                        x = 0f,
                        y = 0f
                    ),
                    end = Offset(
                        x = 0f,
                        y = size.height
                    ),
                    strokeWidth = size.width
                )
            }

            SummaryValue(
                value = "$totalMinutes",
                label = "Total Minutes",
                modifier = Modifier.width(88.dp)
            )
        }
    }
}

@Composable
private fun SummaryValue(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = value,
            color = Color.White,
            fontSize = 30.sp,
            lineHeight = 36.sp,
            fontWeight = FontWeight.Normal
        )

        Text(
            text = label,
            color = HistoryMuted,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
private fun HistoryTabs(
    selected: String,
    onSelect: (String) -> Unit
) {

    val tabs =
        listOf("Daily", "Weekly", "Monthly")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = HistoryTabsBg,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(6.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        tabs.forEach { tab ->

            val isSelected =
                selected.equals(
                    tab,
                    ignoreCase = true
                )

            Surface(
                onClick = {
                    onSelect(tab)
                },
                color =
                    if (isSelected) {
                        BackgroundDark
                    } else {
                        Color.Transparent
                    },
                shape = RoundedCornerShape(12.dp),
                shadowElevation =
                    if (isSelected) {
                        6.dp
                    } else {
                        0.dp
                    },
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
            ) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = tab,
                        color =
                            if (isSelected) {
                                HistoryText
                            } else {
                                BackgroundDark
                            },
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun TodayHeader() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Outlined.Event,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(
                width = 20.dp,
                height = 17.dp
            )
        )

        Spacer(
            modifier = Modifier.width(6.dp)
        )

        Text(
            text = "Today",
            color = Color.White,
            fontSize = 15.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun HistorySessionCard(
    session: FocusSession
) {

    Surface(
        color = HistoryPanel,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = HistoryBorder
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(132.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = session.taskTitle ?: "Quick Focus",
                color = HistoryText,
                fontSize = 18.sp,
                lineHeight = 27.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Outlined.Schedule,
                    contentDescription = null,
                    tint = HistoryAccent,
                    modifier = Modifier.size(16.dp)
                )

                Text(
                    text = "${session.durationMinutes} min focus",
                    color = HistoryAccent,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Normal
                )

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                StatusBadge(
                    status = session.sessionStatus
                )
            }

            Text(
                text = session.timeRange(),
                color = HistoryMutedSoft,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

private fun FocusSession.timeRange(): String {

    return if (endTime.isNullOrBlank()) {
        "$startTime - Active"
    } else {
        "$startTime - $endTime"
    }
}
