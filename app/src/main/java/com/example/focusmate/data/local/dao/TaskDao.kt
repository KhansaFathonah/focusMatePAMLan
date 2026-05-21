package com.example.focusmate.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.focusmate.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query(
        "SELECT * FROM tasks ORDER BY id DESC"
    )
    fun getAllTasks():
            Flow<List<TaskEntity>>

    @Query(
        "SELECT * FROM tasks ORDER BY id DESC"
    )
    suspend fun getAllTasksSnapshot():
            List<TaskEntity>

    @Insert(
        onConflict =
            OnConflictStrategy.REPLACE
    )
    suspend fun insertTask(
        task: TaskEntity
    )

    @Insert(
        onConflict =
            OnConflictStrategy.REPLACE
    )
    suspend fun insertTasks(
        tasks: List<TaskEntity>
    )

    @Update
    suspend fun updateTask(
        task: TaskEntity
    )

    @Delete
    suspend fun deleteTask(
        task: TaskEntity
    )

    @Query(
        "DELETE FROM tasks"
    )
    suspend fun clearTasks()
}
