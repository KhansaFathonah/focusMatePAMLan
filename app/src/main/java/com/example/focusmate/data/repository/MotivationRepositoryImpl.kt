package com.example.focusmate.data.repository

import com.example.focusmate.data.local.dao.MotivationDao
import com.example.focusmate.data.local.entity.MotivationEntity
import com.example.focusmate.data.mapper.toDomain
import com.example.focusmate.data.remote.api.MotivationApi
import com.example.focusmate.domain.model.Motivation
import com.example.focusmate.domain.repository.MotivationRepository
import com.example.focusmate.utils.NetworkObserver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MotivationRepositoryImpl @Inject constructor(

    private val motivationDao: MotivationDao,

    private val motivationApi: MotivationApi,

    private val networkObserver: NetworkObserver

) : MotivationRepository {

    override fun getRandomQuote():
            Flow<Motivation> {

        return motivationDao
            .getRandomQuote()
            .map { entity ->

                entity?.toDomain()

                    ?: Motivation(

                        id = 0,

                        quote =
                            "Let's make today productive",

                        author = "FocusMate"
                    )
            }
    }

    override suspend fun saveQuote(
        quote: Motivation
    ) {

        motivationDao.insertQuote(

            MotivationEntity(

                id = quote.id,

                quote = quote.quote,

                author = quote.author
            )
        )
    }

    override suspend fun refreshQuote() {

        if (!networkObserver.hasInternetAccess()) {
            return
        }

        val quote =
            runCatching {
                motivationApi
                    .getRandomQuote()
                    .firstOrNull()
            }.getOrNull()
                ?: return

        if (quote.content.isBlank()) {
            return
        }

        motivationDao.insertQuote(
            MotivationEntity(
                quote = quote.content,
                author = quote.author
            )
        )
    }
}
