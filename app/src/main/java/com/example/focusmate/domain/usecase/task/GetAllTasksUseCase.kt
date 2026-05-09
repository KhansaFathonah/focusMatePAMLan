package com.example.focusmate.domain.usecase.task

import com.example.focusmate.domain.repository.TaskRepository
import javax.inject.Inject

class GetAllTasksUseCase @Inject constructor(

    private val repository: TaskRepository

) {

    operator fun invoke() =

        repository.getAllTasks()
}