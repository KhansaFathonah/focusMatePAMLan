package com.example.focusmate.domain.usecase.task

import com.example.focusmate.domain.model.Task
import com.example.focusmate.domain.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskStatusUseCase @Inject constructor(

    private val repository: TaskRepository

) {

    suspend operator fun invoke(
        task: Task
    ) {

        repository.updateTask(task)
    }
}