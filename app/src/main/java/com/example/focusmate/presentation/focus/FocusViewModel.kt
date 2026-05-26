package com.example.focusmate.presentation.focus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focusmate.domain.model.Task
import com.example.focusmate.domain.usecase.task.GetAllTasksUseCase
import com.example.focusmate.domain.usecase.task.UpdateTaskStatusUseCase
import com.example.focusmate.utils.TaskUtils
import com.example.focusmate.utils.TimeUtils
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
    val uiState:
            StateFlow<FocusUiState> =

        _uiState.asStateFlow()
    private var timerJob: Job? = null
    init {
        loadTasks()
    }
    private fun loadTasks() {
        viewModelScope.launch {
            getAllTasksUseCase()
                .collect { taskList ->
                    checkOverdueTasks(taskList)
                    val updatedTasks =
                        taskList.map { task ->
                            when {
                                task.status ==
                                        "Completed" -> {

                                    task
                                }
                                task.status ==
                                        "In Progress" -> {

                                    task
                                }
                                TimeUtils.isTaskOverdue(
                                    task.deadline
                                ) -> {
                                    task.copy(
                                        status = "Overdue"
                                    )
                                }
                                task.status ==
                                        "Overdue" -> {
                                    task.copy(
                                        status =
                                            "Not Started"
                                    )
                                }
                                else -> {

                                    task
                                }
                            }
                        }
                    val availableTasks =
                        updatedTasks.filter { task ->
                            task.status != "Completed"
                        }
                    val sortedTasks =
                        TaskUtils.sortTasksByPriority(
                            availableTasks
                        )
                    _uiState.update { currentState ->
                        currentState.copy(
                            tasks = sortedTasks
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
                task.status == "Completed"
            ) {
                return@forEach
            }
            if (
                task.status == "In Progress"
            ) {
                return@forEach
            }
            if (
                task.status == "Overdue"
            ) {
                return@forEach
            }
            if (
                TimeUtils.isTaskOverdue(
                    task.deadline
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
        viewModelScope.launch {
            updateTaskStatusUseCase(
                task.copy(
                    status = "In Progress"
                )
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
        val durationInSeconds = minutes * 60
        _uiState.update { currentState ->
            currentState.copy(
                selectedDuration = minutes,
                remainingSeconds =
                    durationInSeconds,
                totalSeconds =
                    durationInSeconds,
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
        _uiState.value.selectedTask?.let { task ->
            startTaskFocus(task)
        }
        startTimer()
    }
    private fun startTimer() {
        timerJob?.cancel()
        timerJob =
            viewModelScope.launch {
                while (
                    _uiState.value.remainingSeconds > 0 &&
                    _uiState.value.isRunning
                ) {
                    delay(1000)
                    val currentSeconds =
                        _uiState.value.remainingSeconds - 1
                    val totalSeconds =
                        _uiState.value.totalSeconds
                    val updatedProgress =
                        if (totalSeconds > 0)
                            currentSeconds
                                .toFloat() /
                                    totalSeconds
                                        .toFloat()
                        else
                            0f
                    _uiState.update { currentState ->
                        currentState.copy(
                            remainingSeconds =
                                currentSeconds,
                            progress =
                                updatedProgress
                        )
                    }
                }
                if (
                    _uiState.value.remainingSeconds <= 0
                ) {
                    completeSession()
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
    fun stopTimer() {
        timerJob?.cancel()
        _uiState.update { currentState ->
            currentState.copy(
                isRunning = false,
                isPaused = false
            )
        }
    }
    fun addExtraTime() {
        val extraTime = 600
        _uiState.update { currentState ->
            val updatedTotal =
                currentState.totalSeconds +
                        extraTime
            val updatedRemaining =
                currentState.remainingSeconds +
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
        _uiState.update { currentState ->
            currentState.copy(
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
                progress = 1f,
                showExtendDialog = false
            )
        }
    }
}