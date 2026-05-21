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

    /*
    ====================================
    TIMER JOB
    ====================================
    */

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

    /*
    ====================================
    START TASK FOCUS
    ====================================
    */

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

        val durationInSeconds =

            if (minutes == 10)

                10

            else

                minutes * 60

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

    /*
    ====================================
    START SESSION
    ====================================
    */

    fun startFocusSession() {

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

    /*
    ====================================
    START TIMER
    ====================================
    */

    fun startTimer() {

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

                        currentSeconds
                            .toFloat() /

                                totalSeconds
                                    .toFloat()

                    _uiState.update { currentState ->

                        currentState.copy(

                            remainingSeconds =
                                currentSeconds,

                            progress =
                                updatedProgress
                        )
                    }
                }

                /*
                ====================================
                SESSION COMPLETE
                ====================================
                */

                if (
                    _uiState.value.remainingSeconds <= 0
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
    STOP TIMER
    ====================================
    */

    fun stopTimer() {

        timerJob?.cancel()

        _uiState.update { currentState ->

            currentState.copy(

                isRunning = false,

                isPaused = false
            )
        }
    }

    /*
    ====================================
    ADD EXTRA TIME
    ====================================
    */

    fun addExtraTime() {

        val extraTime =

            if (
                _uiState.value.selectedDuration == 10
            )

                10

            else

                600

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
    SHOW EXTEND DIALOG
    ====================================
    */

    fun showExtendDialog() {

        _uiState.update { currentState ->

            currentState.copy(

                showExtendDialog = true
            )
        }
    }

    /*
    ====================================
    HIDE EXTEND DIALOG
    ====================================
    */

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

        _uiState.update { currentState ->

            currentState.copy(

                isRunning = false,

                isPaused = false,

                isCompleted = true
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

                showExtendDialog = false
            )
        }
    }
}