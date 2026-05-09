package com.example.focusmate.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.focusmate.data.local.entity.MotivationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MotivationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(
        motivation: MotivationEntity
    )

    @Query(
        "SELECT * FROM motivations ORDER BY RANDOM() LIMIT 1"
    )
    fun getRandomQuote():
            Flow<MotivationEntity?>

    @Query("DELETE FROM motivations")
    suspend fun clearQuotes()
}