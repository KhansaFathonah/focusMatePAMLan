package com.example.focusmate.domain.repository

import com.example.focusmate.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getAllTasks():
            Flow<List<Task>>

    suspend fun insertTask(
        task: Task
    )

    suspend fun updateTask(
        task: Task
    )

    suspend fun deleteTask(
        task: Task
    )
}