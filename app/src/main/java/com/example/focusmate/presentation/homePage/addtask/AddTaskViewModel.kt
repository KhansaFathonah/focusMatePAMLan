package com.example.focusmate.presentation.homePage.addtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focusmate.domain.model.Task
import com.example.focusmate.domain.usecase.task.AddTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(

    private val addTaskUseCase: AddTaskUseCase

) : ViewModel() {

    /*
    =========================================
    UI STATE
    =========================================
    */

    private val _uiState =

        MutableStateFlow(
            AddTaskUiState()
        )

    val uiState =
        _uiState.asStateFlow()

    /*
    =========================================
    EVENT HANDLER
    =========================================
    */

    fun onEvent(
        event: AddTaskEvent
    ) {

        when (event) {

            /*
            =====================================
            TITLE CHANGE
            =====================================
            */

            is AddTaskEvent.OnTitleChange -> {

                _uiState.update {

                    val isDeadlineValid =
                        it.deadlineMillis?.let { deadlineMillis ->
                            isTodayOrFuture(deadlineMillis)
                        } == true

                    it.copy(

                        title = event.title,

                        errorMessage =
                            if (isDeadlineValid || it.deadline.isBlank()) {
                                null
                            } else {
                                "Deadline must be today or later"
                            },

                        isSaveEnabled =

                            event.title.isNotBlank()
                                    &&
                                    isDeadlineValid
                    )
                }
            }

            /*
            =====================================
            DEADLINE CHANGE
            =====================================
            */

            is AddTaskEvent.OnDeadlineChange -> {

                _uiState.update {

                    val isDeadlineValid =
                        isTodayOrFuture(
                            event.deadlineMillis
                        )

                    it.copy(

                        deadline = event.deadline,

                        deadlineMillis =
                            event.deadlineMillis,

                        errorMessage =
                            if (isDeadlineValid) {
                                null
                            } else {
                                "Deadline must be today or later"
                            },

                        isSaveEnabled =

                            it.title.isNotBlank()
                                    &&
                                    isDeadlineValid
                    )
                }
            }

            /*
            =====================================
            SAVE TASK
            =====================================
            */

            AddTaskEvent.SaveTask -> {

                saveTask()
            }
        }
    }

    /*
    =========================================
    SAVE TASK
    =========================================
    */

    private fun saveTask() {

        viewModelScope.launch {

            val currentState =
                uiState.value

            /*
            =====================================
            VALIDATION
            =====================================
            */

            if (
                !currentState.isSaveEnabled
                ||
                currentState.deadlineMillis?.let {
                    !isTodayOrFuture(it)
                } != false
            ) {

                return@launch
            }

            /*
            =====================================
            INSERT TASK
            =====================================
            */

            addTaskUseCase(

                Task(

                    id = 0,

                    title =
                        currentState.title,

                    deadline =
                        currentState.deadline,

                    status =
                        "Not Started",

                    focusMinutes = 0
                )
            )

            /*
            =====================================
            RESET FORM
            =====================================
            */

            _uiState.value =
                AddTaskUiState()
        }
    }

    private fun isTodayOrFuture(
        deadlineMillis: Long
    ): Boolean {

        val todayStart =
            Calendar.getInstance()
                .apply {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                .timeInMillis

        return deadlineMillis >= todayStart
    }
}
