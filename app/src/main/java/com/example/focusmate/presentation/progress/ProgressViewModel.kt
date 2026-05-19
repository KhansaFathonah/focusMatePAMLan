package com.example.focusmate.presentation.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focusmate.domain.usecase.focus.GetFocusHistoryUseCase
import com.example.focusmate.domain.usecase.task.GetAllTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
                        buildWeeklyMinutes(
                            completedSessions.map { session ->
                                session.durationMinutes
                            }
                        )
                )

            }.collect { progressState ->

                _uiState.update {
                    progressState
                }
            }
        }
    }

    private fun buildWeeklyMinutes(
        minutes: List<Int>
    ): List<Int> {

        if (minutes.isEmpty()) {
            return listOf(25, 35, 34, 44, 38, 58, 82)
        }

        val lastSeven =
            minutes.take(7)

        return List(7) { index ->
            lastSeven.getOrNull(index) ?: 0
        }
    }
}
