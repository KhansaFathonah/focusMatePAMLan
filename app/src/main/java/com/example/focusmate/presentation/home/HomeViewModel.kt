package com.example.focusmate.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focusmate.domain.model.Task
import com.example.focusmate.domain.usecase.motivation.GetRandomQuoteUseCase
import com.example.focusmate.domain.usecase.motivation.RefreshQuoteUseCase
import com.example.focusmate.domain.usecase.task.DeleteTaskUseCase
import com.example.focusmate.domain.usecase.task.GetAllTasksUseCase
import com.example.focusmate.domain.usecase.task.UpdateTaskStatusUseCase
import com.example.focusmate.utils.TimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

    private val getAllTasksUseCase:
    GetAllTasksUseCase,

    private val deleteTaskUseCase:
    DeleteTaskUseCase,

    private val getRandomQuoteUseCase:
    GetRandomQuoteUseCase,

    private val refreshQuoteUseCase:
    RefreshQuoteUseCase,

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
            HomeUiState()
        )

    val uiState:
            StateFlow<HomeUiState> =

        _uiState.asStateFlow()

    /*
    ====================================
    INIT
    ====================================
    */

    init {

        loadHomeData()

        refreshQuote()
    }

    /*
    ====================================
    LOAD HOME DATA
    ====================================
    */

    private fun loadHomeData() {

        viewModelScope.launch {

            combine(

                getAllTasksUseCase(),

                getRandomQuoteUseCase()

            ) { tasks, quote ->

                /*
                ================================
                UPDATE OVERDUE TASKS
                ================================
                */

                checkOverdueTasks(tasks)

                /*
                ================================
                UPDATE UI TASKS
                ================================
                */

                val updatedTasks =

                    tasks.map { task ->

                        /*
                        ============================
                        COMPLETED
                        ============================
                        */

                        if (
                            task.status == "Completed"
                        ) {

                            task

                        }

                        /*
                        ============================
                        IN PROGRESS
                        tetap in progress
                        ============================
                        */

                        else if (
                            task.status == "In Progress"
                        ) {

                            task
                        }

                        /*
                        ============================
                        OVERDUE
                        ============================
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
                        ============================
                        NORMAL
                        ============================
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
                RETURN UI STATE
                ================================
                */

                HomeUiState(

                    tasks = updatedTasks,

                    motivation = quote
                )

            }.collect { state ->

                _uiState.value = state
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
            SKIP
            ================================
            */

            if (

                task.status == "Completed"
                ||
                task.status == "In Progress"
                ||
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
    EVENT
    ====================================
    */

    fun onEvent(
        event: HomeEvent
    ) {

        when (event) {

            is HomeEvent.RefreshQuote -> {

                refreshQuote()
            }

            is HomeEvent.CompleteTask -> {

                completeTask(
                    event.task
                )
            }

            is HomeEvent.DeleteTask -> {

                deleteTask(
                    event.task
                )
            }
        }
    }

    /*
    ====================================
    REFRESH QUOTE
    ====================================
    */

    private fun refreshQuote() {

        viewModelScope.launch {

            refreshQuoteUseCase()
        }
    }

    /*
    ====================================
    COMPLETE TASK
    ====================================
    */

    private fun completeTask(
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
    UPDATE TASK
    ====================================
    */

    fun updateTask(
        task: Task
    ) {

        viewModelScope.launch {

            updateTaskStatusUseCase(task)
        }
    }

    /*
    ====================================
    DELETE TASK
    ====================================
    */

    fun deleteTask(
        task: Task
    ) {

        viewModelScope.launch {

            deleteTaskUseCase(task)
        }
    }
}