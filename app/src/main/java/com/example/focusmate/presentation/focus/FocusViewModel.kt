package com.example.focusmate.presentation.focus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focusmate.domain.model.Task
import com.example.focusmate.domain.usecase.task.GetAllTasksUseCase
import com.example.focusmate.domain.usecase.task.UpdateTaskStatusUseCase
import com.example.focusmate.utils.TaskUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FocusViewModel @Inject constructor(

    private val getAllTasksUseCase:
    GetAllTasksUseCase,

    private val updateTaskStatusUseCase:
    UpdateTaskStatusUseCase

) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            FocusUiState()
        )

    val uiState: StateFlow<FocusUiState> =
        _uiState.asStateFlow()

    private var timerJob: Job? = null

    init {

        loadTasks()
    }

    private fun loadTasks() {

        viewModelScope.launch {

            getAllTasksUseCase()
                .collect { taskList ->

                    checkOverdueTasks(
                        taskList
                    )

                    val activeTasks =

                        TaskUtils.getActiveTasks(
                            taskList
                        )

                    _uiState.update { currentState ->

                        val updatedSelectedTask =

                            taskList.find { task ->

                                task.id ==
                                        currentState
                                            .selectedTask
                                            ?.id
                            }

                        currentState.copy(

                            tasks = activeTasks,

                            selectedTask =
                                updatedSelectedTask
                        )
                    }
                }
        }
    }

    private suspend fun checkOverdueTasks(
        tasks: List<Task>
    ) {

        tasks.forEach { task ->

            if (
                TaskUtils.isTaskCompleted(
                    task
                )
            ) {

                return@forEach
            }

            if (
                TaskUtils.isTaskOverdue(
                    task
                )
            ) {

                updateTaskStatusUseCase(

                    task.copy(
                        status = "Overdue"
                    )
                )
            }
        }
    }

    fun startTaskFocus(
        task: Task
    ) {

        _uiState.update { currentState ->

            currentState.copy(
                selectedTask = task
            )
        }
    }

    fun completeTask(
        task: Task
    ) {

        viewModelScope.launch {

            updateTaskStatusUseCase(

                task.copy(
                    status = "Completed"
                )
            )
        }
    }

    fun selectTask(
        task: Task
    ) {

        _uiState.update { currentState ->

            currentState.copy(
                selectedTask = task
            )
        }
    }

    fun selectDuration(
        minutes: Int
    ) {

        val durationInSeconds =
            minutes * 60

        _uiState.update { currentState ->

            currentState.copy(

                selectedDuration =
                    minutes,

                remainingSeconds =
                    durationInSeconds,

                totalSeconds =
                    durationInSeconds,

                focusedSeconds = 0,

                progress = 1f
            )
        }
    }

    fun startFocusSession() {

        timerJob?.cancel()

        _uiState.update { currentState ->

            currentState.copy(

                isRunning = true,

                isPaused = false,

                isCompleted = false
            )
        }

        startTimer()
    }

    private fun startTimer() {

        timerJob?.cancel()

        timerJob = viewModelScope.launch {

            while (_uiState.value.isRunning) {

                delay(1000)

                val currentSeconds =

                    _uiState.value.remainingSeconds - 1

                if (currentSeconds <= 0) {

                    _uiState.update { currentState ->

                        currentState.copy(

                            remainingSeconds = 0,

                            progress = 0f,

                            focusedSeconds =
                                currentState.focusedSeconds + 1
                        )
                    }

                    completeSession()

                    break
                }

                val totalSeconds =
                    _uiState.value.totalSeconds

                val updatedProgress =

                    currentSeconds.toFloat() /
                            totalSeconds.toFloat()

                _uiState.update { currentState ->

                    currentState.copy(

                        remainingSeconds =
                            currentSeconds,

                        progress =
                            updatedProgress,

                        focusedSeconds =
                            currentState.focusedSeconds + 1
                    )
                }
            }
        }
    }

    fun pauseTimer() {

        timerJob?.cancel()

        _uiState.update { currentState ->

            currentState.copy(

                isRunning = false,

                isPaused = true
            )
        }
    }

    fun resumeTimer() {

        _uiState.update { currentState ->

            currentState.copy(

                isRunning = true,

                isPaused = false
            )
        }

        startTimer()
    }

    fun addExtraTime() {

        val extraTime = 600

        _uiState.update { currentState ->

            val updatedRemaining =

                currentState.remainingSeconds +
                        extraTime

            val updatedTotal =

                currentState.totalSeconds +
                        extraTime

            currentState.copy(

                remainingSeconds =
                    updatedRemaining,

                totalSeconds =
                    updatedTotal,

                progress =

                    updatedRemaining
                        .toFloat() /

                            updatedTotal
                                .toFloat(),

                showExtendDialog = false,

                isRunning = true,

                isPaused = false,

                isCompleted = false
            )
        }

        startTimer()
    }

    fun showExtendDialog() {

        _uiState.update { currentState ->

            currentState.copy(
                showExtendDialog = true
            )
        }
    }

    fun hideExtendDialog() {

        _uiState.update { currentState ->

            currentState.copy(
                showExtendDialog = false
            )
        }
    }

    private fun completeSession() {

        timerJob?.cancel()

        val currentState =
            _uiState.value

        val selectedTask =
            currentState.selectedTask

        val completedMinutes =

            currentState.focusedSeconds / 60

        if (
            !currentState.isQuickFocus &&
            selectedTask != null
        ) {

            viewModelScope.launch {

                val updatedTask =

                    selectedTask.copy(

                        status = "Completed",

                        focusMinutes =

                            selectedTask.focusMinutes +
                                    completedMinutes,

                        focusSessions =

                            selectedTask.focusSessions + 1
                    )

                updateTaskStatusUseCase(
                    updatedTask
                )
            }
        }

        _uiState.update {

            it.copy(

                isRunning = false,

                isPaused = false,

                isCompleted = true,

                progress = 0f
            )
        }
    }

    fun setQuickFocus(
        isQuickFocus: Boolean
    ) {

        _uiState.update { currentState ->

            currentState.copy(
                isQuickFocus =
                    isQuickFocus
            )
        }
    }

    fun resetSession() {

        timerJob?.cancel()

        _uiState.update { currentState ->

            currentState.copy(

                activeSession = null,

                selectedTask = null,

                isRunning = false,

                isPaused = false,

                isCompleted = false,

                selectedDuration = null,

                remainingSeconds = 0,

                totalSeconds = 0,

                focusedSeconds = 0,

                progress = 1f,

                showExtendDialog = false,

                isQuickFocus = false
            )
        }
    }

    override fun onCleared() {

        super.onCleared()

        timerJob?.cancel()
    }
}