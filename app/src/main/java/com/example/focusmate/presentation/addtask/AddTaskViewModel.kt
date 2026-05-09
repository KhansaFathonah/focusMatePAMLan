package com.example.focusmate.presentation.addtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focusmate.domain.model.Task
import com.example.focusmate.domain.usecase.task.AddTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

                    it.copy(

                        title = event.title,

                        isSaveEnabled =

                            event.title.isNotBlank()
                                    &&
                                    it.deadline.isNotBlank()
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

                    it.copy(

                        deadline = event.deadline,

                        isSaveEnabled =

                            it.title.isNotBlank()
                                    &&
                                    event.deadline.isNotBlank()
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
}