package com.example.focusmate.data.repository

import com.example.focusmate.data.local.dao.FocusSessionDao
import com.example.focusmate.data.mapper.toDomain
import com.example.focusmate.data.mapper.toEntity
import com.example.focusmate.domain.model.FocusSession
import com.example.focusmate.domain.repository.FocusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FocusRepositoryImpl @Inject constructor(

    private val dao: FocusSessionDao

) : FocusRepository {

    /*
    ====================================
    START SESSION
    ====================================
    */

    override suspend fun startSession(
        session: FocusSession
    ): Long {

        return dao.insertSession(
            session.toEntity()
        )
    }

    /*
    ====================================
    UPDATE SESSION
    ====================================
    */

    override suspend fun updateSession(
        session: FocusSession
    ) {

        dao.updateSession(
            session.toEntity()
        )
    }

    /*
    ====================================
    DELETE SESSION
    ====================================
    */

    override suspend fun deleteSession(
        session: FocusSession
    ) {

        dao.deleteSession(
            session.toEntity()
        )
    }

    /*
    ====================================
    ACTIVE SESSION
    ====================================
    */

    override fun getActiveSession():
            Flow<FocusSession?> {

        return dao.getActiveSession()
            .map { entity ->

                entity?.toDomain()
            }
    }

    /*
    ====================================
    ALL SESSIONS
    ====================================
    */

    override fun getAllSessions():
            Flow<List<FocusSession>> {

        return dao.getAllSessions()
            .map { list ->

                list.map { entity ->

                    entity.toDomain()
                }
            }
    }

    /*
    ====================================
    COMPLETED SESSIONS
    ====================================
    */

    override fun getCompletedSessions():
            Flow<List<FocusSession>> {

        return dao.getCompletedSessions()
            .map { list ->

                list.map { entity ->

                    entity.toDomain()
                }
            }
    }

    /*
    ====================================
    COMPLETED TASK SESSIONS
    ====================================
    */

    override fun getCompletedTaskSessions():
            Flow<List<FocusSession>> {

        return dao.getCompletedTaskSessions()
            .map { list ->

                list.map { entity ->

                    entity.toDomain()
                }
            }
    }

    /*
    ====================================
    TOTAL MINUTES
    ====================================
    */

    override fun getTotalFocusMinutes():
            Flow<Int?> {

        return dao.getTotalFocusMinutes()
    }

    /*
    ====================================
    TOTAL SESSIONS
    ====================================
    */

    override fun getTotalSessions():
            Flow<Int> {

        return dao.getTotalSessions()
    }
}
