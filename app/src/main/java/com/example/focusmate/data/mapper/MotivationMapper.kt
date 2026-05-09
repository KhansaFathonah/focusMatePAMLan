package com.example.focusmate.data.mapper

import com.example.focusmate.data.local.entity.MotivationEntity
import com.example.focusmate.domain.model.Motivation

fun MotivationEntity.toDomain(): Motivation {

    return Motivation(
        id = id,
        quote = quote,
        author = author
    )
}

fun Motivation.toEntity(): MotivationEntity {

    return MotivationEntity(
        id = id,
        quote = quote,
        author = author
    )
}