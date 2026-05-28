package com.example.focusmate.presentation.progressPage.history

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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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

private data class HistoryDateKey(
    val label: String,
    val sortTime: Long
)

private data class HistoryDateGroup(
    val key: HistoryDateKey,
    val sessions: List<FocusSession>
)

@Composable
fun HistoryScreen(

    navController: NavController,

    viewModel: HistoryViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    val completedSessions =
        remember(uiState.sessions) {
            uiState.sessions
                .filter { session ->
                    session.isCompleted
                }
        }

    val displaySessions =
        remember(
            completedSessions,
            uiState.selectedTab
        ) {
            completedSessions
                .filter { session ->
                    session.isInSelectedHistoryTab(
                        uiState.selectedTab
                    )
                }
        }

    val totalMinutes =
        displaySessions.sumOf { session ->
            session.durationMinutes
        }

    val sessionGroups =
        remember(displaySessions) {
            groupSessionsByDate(
                displaySessions
            )
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

        sessionGroups.forEach { group ->

            item(
                key = "header-${group.key.label}-${group.key.sortTime}"
            ) {

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                DateHeader(
                    label = group.key.label
                )
            }

            items(
                items = group.sessions,
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

        if (sessionGroups.isEmpty()) {

            item {

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                EmptyHistoryMessage(
                    selectedTab = uiState.selectedTab
                )
            }
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
        listOf("Today", "Weekly", "Monthly")

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
private fun EmptyHistoryMessage(
    selectedTab: String
) {

    Text(
        text = "No completed sessions for $selectedTab",
        color = HistoryMuted,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun DateHeader(
    label: String
) {

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
            text = label,
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
                    status = session.displayStatus()
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

private fun FocusSession.displayStatus(): String {

    return when {
        isCompleted -> "Completed"
        else -> "Not Started"
    }
}

private fun FocusSession.timeRange(): String {

    return if (endTime.isNullOrBlank()) {
        "$startTime - Active"
    } else {
        "$startTime - $endTime"
    }
}

private fun FocusSession.isInSelectedHistoryTab(
    selectedTab: String
): Boolean {

    val calendar =
        parseHistoryDate(
            date
        ) ?: return false

    return when (selectedTab.lowercase(Locale.getDefault())) {
        "today" -> calendar.isToday()
        "weekly" -> calendar.isThisWeek()
        "monthly" -> calendar.isThisMonth()
        else -> true
    }
}

private fun groupSessionsByDate(
    sessions: List<FocusSession>
): List<HistoryDateGroup> {

    return sessions
        .groupBy { session ->
            historyDateKey(
                session.date
            )
        }
        .map { (key, groupedSessions) ->
            HistoryDateGroup(
                key = key,
                sessions = groupedSessions
            )
        }
        .sortedByDescending { group ->
            group.key.sortTime
        }
}

private fun historyDateKey(
    value: String
): HistoryDateKey {

    val calendar =
        parseHistoryDate(
            value
        )

    if (calendar == null) {

        return HistoryDateKey(
            label =
                value.ifBlank {
                    "Unknown Date"
                },
            sortTime = Long.MIN_VALUE
        )
    }

    return HistoryDateKey(
        label =
            if (calendar.isToday()) {
                "Today"
            } else {
                SimpleDateFormat(
                    "dd MMMM yyyy",
                    Locale.ENGLISH
                ).format(
                    calendar.time
                )
            },
        sortTime = calendar.timeInMillis
    )
}

private fun parseHistoryDate(
    value: String
): Calendar? {

    val formats =
        listOf(
            "yyyy-MM-dd",
            "dd MMM yyyy",
            "dd MMMM yyyy"
        )

    formats.forEach { pattern ->

        val parsed =
            runCatching {
                SimpleDateFormat(
                    pattern,
                    Locale.getDefault()
                ).apply {
                    isLenient = false
                }.parse(value)
            }.getOrNull()

        if (parsed != null) {

            return Calendar.getInstance()
                .apply {
                    time = parsed
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
        }
    }

    if (value.equals("Today", ignoreCase = true)) {

        return Calendar.getInstance()
            .apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
    }

    return null
}

private fun Calendar.isToday(): Boolean {

    val today =
        Calendar.getInstance()

    return get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
            get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)
}

private fun Calendar.isThisWeek(): Boolean {

    val weekStart =
        Calendar.getInstance()
            .apply {
                firstDayOfWeek =
                    Calendar.SUNDAY
                set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

    val weekEnd =
        (weekStart.clone() as Calendar)
            .apply {
                add(Calendar.DAY_OF_YEAR, 7)
            }

    return !before(weekStart) &&
            before(weekEnd)
}

private fun Calendar.isThisMonth(): Boolean {

    val today =
        Calendar.getInstance()

    return get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
            get(Calendar.MONTH) == today.get(Calendar.MONTH)
}
