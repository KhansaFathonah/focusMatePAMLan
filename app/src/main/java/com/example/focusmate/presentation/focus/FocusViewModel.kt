package com.example.focusmate.presentation.focus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focusmate.domain.model.FocusSession
import com.example.focusmate.domain.model.Task
import com.example.focusmate.domain.usecase.focus.StartFocusSessionUseCase
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class FocusViewModel @Inject constructor(

    private val getAllTasksUseCase:
    GetAllTasksUseCase,

    private val updateTaskStatusUseCase:
    UpdateTaskStatusUseCase,

    private val startFocusSessionUseCase:
    StartFocusSessionUseCase

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

    private var sessionStartTime: String? = null

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

                            tasks = sortedTasks,

                            selectedTask =
                                currentState.selectedTask
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

        _uiState.update { currentState ->
            currentState.copy(
                selectedTask = task,
                isQuickFocus = false
            )
        }

        startTimer()
    }

    fun startQuickFocus() {

        _uiState.update { currentState ->
            currentState.copy(
                selectedTask = null,
                isQuickFocus = true
            )
        }

        startTimer()
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

    /*
    ====================================
    TIMER CONTROLS
    ====================================
    */

    fun toggleTimer() {

        val currentState =
            _uiState.value

        when {

            !currentState.isRunning -> {
                startTimer()
            }

            currentState.isPaused -> {
                resumeTimer()
            }

            else -> {
                pauseTimer()
            }
        }
    }

    fun stopTimer(
        onStopped: () -> Unit = {}
    ) {

        timerJob?.cancel()
        timerJob = null

        val currentState =
            _uiState.value

        val elapsedSeconds =
            currentState.totalSeconds -
                    currentState.remainingSeconds

        if (
            elapsedSeconds <= 0 ||
            !currentState.isRunning
        ) {

            resetSession()

            onStopped()
            return
        }

        viewModelScope.launch {

            saveSession(
                shouldCompleteTask = false
            )

            resetSession()

            onStopped()
        }
    }

    fun resetTimerState() {

        timerJob?.cancel()
        timerJob = null

        _uiState.update { currentState ->

            currentState.copy(
                isRunning = false,
                isPaused = false
            )
        }
    }

    fun extendSession() {

        _uiState.update { currentState ->

            val addedSeconds =
                10 * 60

            currentState.copy(
                selectedDuration =
                    currentState.selectedDuration + 10,
                remainingSeconds =
                    currentState.remainingSeconds + addedSeconds,
                totalSeconds =
                    currentState.totalSeconds + addedSeconds,
                progress =
                    calculateProgress(
                        remainingSeconds =
                            currentState.remainingSeconds + addedSeconds,
                        totalSeconds =
                            currentState.totalSeconds + addedSeconds
                    )
            )
        }
    }

    fun extendDuration(
        minutes: Int
    ) {

        repeat(minutes / 10) {
            extendSession()
        }
    }

    fun stopCurrentSession() {

        stopTimer()
    }

    private fun startTimer() {

        val currentState =
            _uiState.value

        sessionStartTime =
            currentTime()

        currentState.selectedTask?.let { task ->
            markTaskInProgress(
                task
            )
        }

        _uiState.update {
            it.copy(
                isRunning = true,
                isPaused = false,
                isCompleted = false,
                sessionStartedAtMillis =
                    System.currentTimeMillis()
            )
        }

        runTimer()
    }

    private fun pauseTimer() {

        timerJob?.cancel()
        timerJob = null

        _uiState.update {
            it.copy(
                isPaused = true
            )
        }
    }

    private fun resumeTimer() {

        _uiState.update {
            it.copy(
                isPaused = false
            )
        }

        runTimer()
    }

    private fun runTimer() {

        timerJob?.cancel()

        timerJob =
            viewModelScope.launch {

                while (
                    _uiState.value.isRunning &&
                    !_uiState.value.isPaused &&
                    _uiState.value.remainingSeconds > 0
                ) {

                    delay(1000)

                    _uiState.update { currentState ->

                        val remainingSeconds =
                            (currentState.remainingSeconds - 1)
                                .coerceAtLeast(0)

                        currentState.copy(
                            remainingSeconds = remainingSeconds,
                            progress =
                                calculateProgress(
                                    remainingSeconds =
                                        remainingSeconds,
                                    totalSeconds =
                                        currentState.totalSeconds
                                )
                        )
                    }
                }

                if (
                    _uiState.value.isRunning &&
                    _uiState.value.remainingSeconds == 0
                ) {

                    completeTimer()
                }
            }
    }

    private fun completeTimer() {

        timerJob = null

        viewModelScope.launch {

            val completedSession =
                saveSession(
                    shouldCompleteTask = true
                )

            _uiState.update {
                it.copy(
                    isRunning = false,
                    isPaused = false,
                isCompleted = true,
                    sessionStartedAtMillis = null,
                    activeSession =
                        completedSession
                )
            }

            resetSession()
        }
    }

    private suspend fun saveSession(
        shouldCompleteTask: Boolean
    ): FocusSession {

        val currentState =
            _uiState.value

        val selectedTask =
            currentState.selectedTask

        val elapsedSeconds =
            (currentState.totalSeconds -
                    currentState.remainingSeconds)
                .coerceAtLeast(0)

        val elapsedMinutes =
            if (currentState.remainingSeconds == 0) {
                currentState.totalSeconds / 60
            } else {
                ((elapsedSeconds + 59) / 60)
                    .coerceAtLeast(1)
            }

        val completedSession =
            FocusSession(
                taskId = selectedTask?.id,
                taskTitle = selectedTask?.title,
                durationMinutes =
                    elapsedMinutes,
                remainingSeconds =
                    currentState.remainingSeconds,
                totalSeconds =
                    currentState.totalSeconds,
                isRunning = false,
                isCompleted = true,
                sessionStatus =
                    resolveSessionStatus(
                        selectedTask
                    ),
                date = currentDate(),
                startTime =
                    sessionStartTime ?: currentTime(),
                endTime = currentTime()
            )

        startFocusSessionUseCase(
            completedSession
        )

        selectedTask?.let { task ->

            updateTaskStatusUseCase(
                task.copy(
                    status =
                        if (shouldCompleteTask) {
                            "Completed"
                        } else {
                            "In Progress"
                        },
                    focusMinutes =
                        task.focusMinutes +
                                completedSession.durationMinutes
                )
            )
        }

        return completedSession
    }

    private fun markTaskInProgress(
        task: Task
    ) {

        viewModelScope.launch {

            val isOverdue =
                task.status == "Overdue" ||
                        TimeUtils.isTaskOverdue(
                            task.deadline
                        )

            updateTaskStatusUseCase(

                task.copy(
                    status =
                        if (isOverdue) {
                            "Overdue"
                        } else {
                            "In Progress"
                        }
                )
            )
        }
    }

    private fun resolveSessionStatus(
        task: Task?
    ): String {

        return if (
            task != null &&
            (
                    task.status == "Overdue" ||
                            TimeUtils.isTaskOverdue(
                                task.deadline
                            )
                    )
        ) {
            "Overdue"
        } else {
            "Success"
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
                    isQuickFocus,

                selectedTask =
                    if (isQuickFocus) {
                        null
                    } else {
                        currentState.selectedTask
                    }
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
        sessionStartTime = null

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

                progress = 1f,

                sessionStartedAtMillis = null
            )
        }
    }

    private fun calculateProgress(
        remainingSeconds: Int,
        totalSeconds: Int
    ): Float {

        if (totalSeconds <= 0) {
            return 0f
        }

        return remainingSeconds.toFloat() /
                totalSeconds.toFloat()
    }

    private fun currentDate(): String {

        return SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        ).format(Date())
    }

    private fun currentTime(): String {

        return SimpleDateFormat(
            "HH:mm",
            Locale.getDefault()
        ).format(Date())
    }

    override fun onCleared() {

        timerJob?.cancel()
        super.onCleared()
    }
}
