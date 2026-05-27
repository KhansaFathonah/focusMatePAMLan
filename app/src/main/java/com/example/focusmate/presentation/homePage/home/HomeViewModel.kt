package com.example.focusmate.presentation.homePage.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focusmate.domain.model.Task
import com.example.focusmate.domain.usecase.motivation.GetRandomQuoteUseCase
import com.example.focusmate.domain.usecase.motivation.RefreshQuoteUseCase
import com.example.focusmate.domain.usecase.settings.GetSettingsUseCase
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
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase,
    private val refreshQuoteUseCase: RefreshQuoteUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val updateTaskStatusUseCase: UpdateTaskStatusUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(HomeUiState())

    val uiState: StateFlow<HomeUiState> =
        _uiState.asStateFlow()

    init {
        loadHomeData()
        refreshQuote()
    }

    private fun loadHomeData() {

        viewModelScope.launch {

            combine(
                getAllTasksUseCase(),
                getRandomQuoteUseCase(),
                getSettingsUseCase()
            ) { tasks, quote, settings ->

                checkOverdueTasks(tasks)

                val updatedTasks =
                    tasks.map { task ->

                        when {

                            task.status == "Completed" -> {
                                task
                            }

                            TimeUtils.isTaskOverdue(
                                task.deadline
                            ) -> {

                                task.copy(
                                    status = "Overdue"
                                )
                            }

                            task.status == "Overdue" -> {

                                task.copy(
                                    status = "Not Started"
                                )
                            }

                            else -> {
                                task
                            }
                        }
                    }

                HomeUiState(
                    tasks = updatedTasks,
                    motivation = quote,
                    username =
                        settings?.username.orEmpty()
                )

            }.collect { state ->

                _uiState.value = state
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

    private fun refreshQuote() {

        viewModelScope.launch {

            refreshQuoteUseCase()
        }
    }

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

    fun updateTask(
        task: Task
    ) {

        viewModelScope.launch {

            updateTaskStatusUseCase(task)
        }
    }

    fun deleteTask(
        task: Task
    ) {

        viewModelScope.launch {

            deleteTaskUseCase(task)
        }
    }
}