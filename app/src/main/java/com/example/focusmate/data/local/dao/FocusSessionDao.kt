package com.example.focusmate.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.focusmate.data.local.entity.FocusSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FocusSessionDao {

    /*
    ====================================
    INSERT SESSION
    ====================================
    */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(
        session: FocusSessionEntity
    ): Long

    /*
    ====================================
    UPDATE SESSION
    ====================================
    */

    @Update
    suspend fun updateSession(
        session: FocusSessionEntity
    )

    /*
    ====================================
    DELETE SESSION
    ====================================
    */

    @Delete
    suspend fun deleteSession(
        session: FocusSessionEntity
    )

    /*
    ====================================
    ACTIVE SESSION
    ====================================
    */

    @Query(
        """
        SELECT * FROM focus_sessions
        WHERE isRunning = 1
        LIMIT 1
        """
    )
    fun getActiveSession():
            Flow<FocusSessionEntity?>

    /*
    ====================================
    ALL SESSIONS
    ====================================
    */

    @Query(
        """
        SELECT * FROM focus_sessions
        ORDER BY id DESC
        """
    )
    fun getAllSessions():
            Flow<List<FocusSessionEntity>>

    /*
    ====================================
    COMPLETED SESSIONS
    ====================================
    */

    @Query(
        """
        SELECT * FROM focus_sessions
        WHERE isCompleted = 1
        ORDER BY id DESC
        """
    )
    fun getCompletedSessions():
            Flow<List<FocusSessionEntity>>

    /*
    ====================================
    COMPLETED TASK SESSIONS
    ====================================
    */

    @Query(
        """
        SELECT * FROM focus_sessions
        WHERE isCompleted = 1
        AND taskId IS NOT NULL
        ORDER BY id DESC
        """
    )
    fun getCompletedTaskSessions():
            Flow<List<FocusSessionEntity>>

    /*
    ====================================
    TOTAL FOCUS MINUTES
    ====================================
    */

    @Query(
        """
        SELECT SUM(durationMinutes)
        FROM focus_sessions
        WHERE isCompleted = 1
        """
    )
    fun getTotalFocusMinutes():
            Flow<Int?>

    /*
    ====================================
    TOTAL SESSIONS
    ====================================
    */

    @Query(
        """
        SELECT COUNT(*)
        FROM focus_sessions
        WHERE isCompleted = 1
        """
    )
    fun getTotalSessions():
            Flow<Int>
}
