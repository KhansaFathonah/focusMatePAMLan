package com.example.focusmate.presentation.progressPage.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focusmate.domain.model.FocusSession
import com.example.focusmate.domain.usecase.focus.GetFocusHistoryUseCase
import com.example.focusmate.domain.usecase.task.GetAllTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(

    private val getAllTasksUseCase: GetAllTasksUseCase,

    private val getFocusHistoryUseCase: GetFocusHistoryUseCase

) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            ProgressUiState()
        )

    val uiState =
        _uiState.asStateFlow()

    init {

        loadProgress()
    }

    private fun loadProgress() {

        viewModelScope.launch {

            combine(
                getAllTasksUseCase(),
                getFocusHistoryUseCase()
            ) { tasks, sessions ->

                val completedSessions =
                    sessions.filter { session ->
                        session.isCompleted
                    }

                ProgressUiState(
                    tasks = tasks,
                    sessions = sessions,
                    completedTasks =
                        tasks.count { task ->
                            task.status == "Completed"
                        },
                    totalTasks = tasks.size,
                    totalFocusMinutes =
                        completedSessions.sumOf { session ->
                            session.durationMinutes
                        },
                    totalSessions =
                        completedSessions.size,
                    weeklyMinutes =
                        buildWeeklySessionCounts(
                            completedSessions
                        )
                )

            }.collect { progressState ->

                _uiState.update {
                    progressState
                }
            }
        }
    }

    private fun buildWeeklySessionCounts(
        sessions: List<FocusSession>
    ): List<Int> {

        if (sessions.isEmpty()) {
            return List(7) { 0 }
        }

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

        val counts =
            MutableList(7) { 0 }

        sessions.forEach { session ->

            val completedDate =
                parseSessionDate(
                    session.date
                ) ?: return@forEach

            if (
                completedDate.before(weekStart) ||
                !completedDate.before(weekEnd)
            ) {
                return@forEach
            }

            val dayIndex =
                completedDate.get(Calendar.DAY_OF_WEEK) -
                        Calendar.SUNDAY

            counts[dayIndex] =
                counts[dayIndex] + 1
        }

        return counts
    }

    private fun parseSessionDate(
        value: String
    ): Calendar? {

        val formats =
            listOf(
                "dd MMM yyyy",
                "dd MMMM yyyy",
                "yyyy-MM-dd"
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
}
