package com.erickpimentel.marvelapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class Character(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String,
    val thumbnailExtension: String
)