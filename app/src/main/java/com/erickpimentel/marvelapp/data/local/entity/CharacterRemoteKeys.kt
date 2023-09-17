package com.erickpimentel.marvelapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterRemoteKeys(

    @PrimaryKey(autoGenerate = false)
    val id: Int,

    val prevPage: Int?,
    val nextPage: Int?,
    val endOfPaginationReached: Boolean
)