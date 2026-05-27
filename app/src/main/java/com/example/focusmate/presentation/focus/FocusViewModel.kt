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

    /*
    ====================================
    UI STATE
    ====================================
    */

    private val _uiState =
        MutableStateFlow(
            FocusUiState()
        )

    val uiState: StateFlow<FocusUiState> =
        _uiState.asStateFlow()

    /*
    ====================================
    TIMER JOB
    ====================================
    */

    private var timerJob: Job? = null

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

                    checkOverdueTasks(
                        taskList
                    )

                    val activeTasks =

                        TaskUtils.getActiveTasks(
                            taskList
                        )

                    _uiState.update { currentState ->

                        /*
                        ====================================
                        REFRESH SELECTED TASK
                        ====================================
                        */

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

    /*
    ====================================
    CHECK OVERDUE TASKS
    ====================================
    */

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

    /*
    ====================================
    START TASK FOCUS
    ====================================
    */

    fun startTaskFocus(

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

        val durationInSeconds = minutes * 1

        _uiState.update { currentState ->

            currentState.copy(

                selectedDuration =
                    minutes,

                remainingSeconds =
                    durationInSeconds,

                totalSeconds =
                    durationInSeconds,

                progress = 1f
            )
        }
    }

    /*
    ====================================
    START SESSION
    ====================================
    */

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

    /*
    ====================================
    START TIMER
    ====================================
    */

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

                        _uiState.value
                            .remainingSeconds - 1

                    val totalSeconds =

                        _uiState.value
                            .totalSeconds

                    val updatedProgress =

                        if (totalSeconds > 0) {

                            currentSeconds
                                .toFloat() /

                                    totalSeconds
                                        .toFloat()

                        } else {

                            0f
                        }

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
                    _uiState.value
                        .remainingSeconds <= 0
                ) {

                    completeSession()
                }
            }
    }

    /*
    ====================================
    PAUSE TIMER
    ====================================
    */

    fun pauseTimer() {

        timerJob?.cancel()

        _uiState.update { currentState ->

            currentState.copy(

                isRunning = false,

                isPaused = true
            )
        }
    }

    /*
    ====================================
    RESUME TIMER
    ====================================
    */

    fun resumeTimer() {

        _uiState.update { currentState ->

            currentState.copy(

                isRunning = true,

                isPaused = false
            )
        }

        startTimer()
    }

    /*
    ====================================
    ADD EXTRA TIME
    ====================================
    */

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

    /*
    ====================================
    EXTEND DIALOG
    ====================================
    */

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

    /*
    ====================================
    COMPLETE SESSION
    ====================================
    */

    private fun completeSession() {

        timerJob?.cancel()

        val currentState =
            _uiState.value

        val selectedTask =
            currentState.selectedTask

        val selectedDuration =
            currentState.selectedDuration ?: 0

        /*
        ====================================
        UPDATE TASK STATS
        ====================================
        */

        if (
            !currentState.isQuickFocus &&
            selectedTask != null
        ) {

            viewModelScope.launch {

                updateTaskStatusUseCase(

                    selectedTask.copy(

                        status = "Completed",

                        focusMinutes =

                            selectedTask.focusMinutes +
                                    selectedDuration,

                        focusSessions =

                            selectedTask.focusSessions + 1
                    )
                )
            }
        }

        /*
        ====================================
        UPDATE UI
        ====================================
        */

        _uiState.update {

            it.copy(

                isRunning = false,

                isPaused = false,

                isCompleted = true,

                progress = 0f
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

                showExtendDialog = false,

                isQuickFocus = false
            )
        }
    }

    /*
    ====================================
    CLEAR
    ====================================
    */

    override fun onCleared() {

        super.onCleared()

        timerJob?.cancel()
    }
}