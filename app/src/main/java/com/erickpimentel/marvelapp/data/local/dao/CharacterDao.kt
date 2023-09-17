package com.erickpimentel.marvelapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erickpimentel.marvelapp.domain.model.Character

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character")
    fun getCharacter(): PagingSource<Int, Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCharacter(character: List<Character>)

    @Query("DELETE FROM character")
    suspend fun deleteCharacters()
}

