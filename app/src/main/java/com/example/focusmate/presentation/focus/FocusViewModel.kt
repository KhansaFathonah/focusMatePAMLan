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

    /*
    ====================================
    UI STATE
    ====================================
    */

    private val _uiState =

        MutableStateFlow(
            FocusUiState()
        )

    val uiState:
            StateFlow<FocusUiState> =

        _uiState.asStateFlow()

    private var timerJob: Job? = null

    /*
    ====================================
    INIT
    ====================================
    */

    init {

        loadTasks()
    }

    /*
    ====================================
    LOAD TASKS
    ====================================
    */

    private fun loadTasks() {

        viewModelScope.launch {

            getAllTasksUseCase()
                .collect { taskList ->

                    /*
                    ================================
                    UPDATE OVERDUE TASKS
                    ================================
                    */

                    checkOverdueTasks(taskList)

                    /*
                    ================================
                    UPDATE UI TASKS
                    ================================
                    */

                    val updatedTasks =

                        taskList.map { task ->

                            /*
                            ========================
                            COMPLETED
                            ========================
                            */

                            if (
                                task.status == "Completed"
                            ) {

                                task
                            }

                            /*
                            ========================
                            IN PROGRESS
                            tetap in progress
                            ========================
                            */

                            else if (
                                task.status == "In Progress"
                            ) {

                                task
                            }

                            /*
                            ========================
                            OVERDUE
                            ========================
                            */

                            else if (

                                TimeUtils.isTaskOverdue(
                                    task.deadline
                                )

                            ) {

                                task.copy(
                                    status = "Overdue"
                                )
                            }

                            /*
                            ========================
                            NORMAL TASK
                            ========================
                            */

                            else {

                                /*
                                reset overdue
                                jika deadline berubah
                                */

                                if (
                                    task.status == "Overdue"
                                ) {

                                    task.copy(
                                        status =
                                            "Not Started"
                                    )

                                } else {

                                    task
                                }
                            }
                        }

                    /*
                    ================================
                    FILTER COMPLETED TASKS
                    ================================
                    */

                    val availableTasks =

                        updatedTasks.filter { task ->

                            task.status != "Completed"
                        }

                    /*
                    ================================
                    SORT TASKS
                    overdue first
                    nearest deadline
                    ================================
                    */

                    val sortedTasks =

                        TaskUtils.sortTasksByPriority(
                            availableTasks
                        )

                    /*
                    ================================
                    UPDATE UI STATE
                    ================================
                    */

                    _uiState.update { currentState ->

                        currentState.copy(

                            tasks = sortedTasks
                        )
                    }
                }
        }
    }

    /*
    ====================================
    CHECK OVERDUE TASKS
    ====================================
    */

    private suspend fun checkOverdueTasks(

        tasks: List<Task>

    ) {

        tasks.forEach { task ->

            /*
            ================================
            SKIP COMPLETED
            ================================
            */

            if (
                task.status == "Completed"
            ) {

                return@forEach
            }

            /*
            ================================
            SKIP IN PROGRESS
            ================================
            */

            if (
                task.status == "In Progress"
            ) {

                return@forEach
            }

            /*
            ================================
            ALREADY OVERDUE
            ================================
            */

            if (
                task.status == "Overdue"
            ) {

                return@forEach
            }

            /*
            ================================
            CHECK OVERDUE
            ================================
            */

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

    /*
    ====================================
    START TASK FOCUS
    ====================================
    */

    fun startTaskFocus(
        task: Task
    ) {

        val durationSeconds =
            _uiState.value.selectedDuration * 60

        viewModelScope.launch {

            updateTaskStatusUseCase(

                task.copy(
                    status = "In Progress"
                )
            )
        }

        _uiState.update { currentState ->

            currentState.copy(
                isRunning = true,
                isPaused = false,
                isCompleted = false,
                remainingSeconds = durationSeconds,
                totalSeconds = durationSeconds,
                progress = 1f
            )
        }

        startCountdown()
    }

    fun startQuickFocus() {

        val durationSeconds =
            _uiState.value.selectedDuration * 60

        _uiState.update { currentState ->

            currentState.copy(
                isQuickFocus = true,
                isRunning = true,
                isPaused = false,
                isCompleted = false,
                remainingSeconds = durationSeconds,
                totalSeconds = durationSeconds,
                progress = 1f
            )
        }

        startCountdown()
    }

    private fun startCountdown() {

        timerJob?.cancel()

        timerJob = viewModelScope.launch {

            while (true) {

                delay(1000)

                _uiState.update { currentState ->

                    if (
                        !currentState.isRunning ||
                        currentState.isPaused ||
                        currentState.remainingSeconds <= 0
                    ) {

                        currentState

                    } else {

                        val newRemainingSeconds =
                            currentState.remainingSeconds - 1

                        val newProgress =
                            if (currentState.totalSeconds > 0) {
                                newRemainingSeconds.toFloat() /
                                        currentState.totalSeconds.toFloat()
                            } else {
                                0f
                            }

                        currentState.copy(
                            remainingSeconds = newRemainingSeconds,
                            progress = newProgress.coerceIn(0f, 1f),
                            isRunning = newRemainingSeconds > 0,
                            isCompleted = newRemainingSeconds == 0
                        )
                    }
                }

                if (_uiState.value.isCompleted) {
                    timerJob?.cancel()
                    break
                }
            }
        }
    }

    fun pauseOrResumeSession() {

        _uiState.update { currentState ->

            currentState.copy(
                isPaused =
                    if (currentState.isRunning) {
                        !currentState.isPaused
                    } else {
                        false
                    }
            )
        }
    }

    /*
    ====================================
    COMPLETE TASK
    ====================================
    */

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

    /*
    ====================================
    SELECT TASK
    ====================================
    */

    fun selectTask(
        task: Task
    ) {

        _uiState.update { currentState ->

            currentState.copy(

                selectedTask = task
            )
        }
    }

    /*
    ====================================
    SELECT DURATION
    ====================================
    */

    fun selectDuration(
        minutes: Int
    ) {

        _uiState.update { currentState ->

            currentState.copy(

                selectedDuration = minutes,

                remainingSeconds =
                    minutes * 60,

                totalSeconds =
                    minutes * 60,

                progress = 1f
            )
        }
    }

    fun extendDuration(
        minutes: Int
    ) {

        val addedSeconds =
            minutes * 60

        _uiState.update { currentState ->

            val newRemainingSeconds =
                currentState.remainingSeconds + addedSeconds

            val newTotalSeconds =
                currentState.totalSeconds + addedSeconds

            currentState.copy(
                selectedDuration = currentState.selectedDuration + minutes,
                remainingSeconds = newRemainingSeconds,
                totalSeconds = newTotalSeconds,
                progress =
                    if (newTotalSeconds > 0) {
                        newRemainingSeconds.toFloat() /
                                newTotalSeconds.toFloat()
                    } else {
                        0f
                    }
            )
        }
    }

    /*
    ====================================
    QUICK FOCUS
    ====================================
    */

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

    /*
    ====================================
    RESET SESSION
    ====================================
    */

    fun resetSession() {

        timerJob?.cancel()
        timerJob = null

        _uiState.update { currentState ->

            currentState.copy(

                activeSession = null,

                selectedTask = null,

                isRunning = false,

                isPaused = false,

                isCompleted = false,

                selectedDuration = 25,

                remainingSeconds = 25 * 60,

                totalSeconds = 25 * 60,

                progress = 1f
            )
        }
    }

    override fun onCleared() {

        timerJob?.cancel()
        super.onCleared()
    }
}
