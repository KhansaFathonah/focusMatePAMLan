package com.example.focusmate.domain.repository

import com.example.focusmate.domain.model.FocusSession
import kotlinx.coroutines.flow.Flow

interface FocusRepository {

    /*
    ====================================
    START SESSION
    ====================================
    */

    suspend fun startSession(
        session: FocusSession
    ): Long

    /*
    ====================================
    UPDATE SESSION
    ====================================
    */

    suspend fun updateSession(
        session: FocusSession
    )

    /*
    ====================================
    DELETE SESSION
    ====================================
    */

    suspend fun deleteSession(
        session: FocusSession
    )

    /*
    ====================================
    ACTIVE SESSION
    ====================================
    */

    fun getActiveSession():
            Flow<FocusSession?>

    /*
    ====================================
    ALL SESSIONS
    ====================================
    */

    fun getAllSessions():
            Flow<List<FocusSession>>

    fun getCompletedSessions():
            Flow<List<FocusSession>>

    fun getCompletedTaskSessions():
            Flow<List<FocusSession>>

    /*
    ====================================
    STATS
    ====================================
    */

    fun getTotalFocusMinutes():
            Flow<Int?>

    fun getTotalSessions():
            Flow<Int>
}
