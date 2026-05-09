package com.example.focusmate.data.repository

import com.example.focusmate.data.local.dao.MotivationDao
import com.example.focusmate.data.local.entity.MotivationEntity
import com.example.focusmate.data.mapper.toDomain
import com.example.focusmate.domain.model.Motivation
import com.example.focusmate.domain.repository.MotivationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MotivationRepositoryImpl @Inject constructor(

    private val motivationDao: MotivationDao

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

                quote = quote.quote
            )
        )
    }

    override suspend fun refreshQuote() {

        /*
        ====================================
        API QUOTE
        ====================================

        nanti diisi API call.
        sekarang kosong dulu
        supaya app bisa jalan.
        */
    }
}