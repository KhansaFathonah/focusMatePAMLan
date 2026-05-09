package com.example.focusmate.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "motivations")
data class MotivationEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val quote: String,

    val author: String = ""
)