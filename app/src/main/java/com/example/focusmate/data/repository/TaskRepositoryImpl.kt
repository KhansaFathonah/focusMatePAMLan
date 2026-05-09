package com.example.focusmate.data.repository

import com.example.focusmate.data.local.dao.TaskDao
import com.example.focusmate.data.mapper.toDomain
import com.example.focusmate.data.mapper.toEntity
import com.example.focusmate.domain.model.Task
import com.example.focusmate.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(

    private val taskDao: TaskDao

) : TaskRepository {

    override fun getAllTasks():
            Flow<List<Task>> {

        return taskDao
            .getAllTasks()
            .map { entityList ->

                entityList.map { entity ->

                    entity.toDomain()
                }
            }
    }

    /*
    ========================================
    INSERT TASK
    ========================================
    */

    override suspend fun insertTask(
        task: Task
    ) {

        taskDao.insertTask(
            task.toEntity()
        )
    }

    /*
    ========================================
    UPDATE TASK
    ========================================
    */

    override suspend fun updateTask(
        task: Task
    ) {

        taskDao.updateTask(
            task.toEntity()
        )
    }

    /*
    ========================================
    DELETE TASK
    ========================================
    */

    override suspend fun deleteTask(
        task: Task
    ) {

        taskDao.deleteTask(
            task.toEntity()
        )
    }
}