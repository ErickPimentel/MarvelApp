package com.erickpimentel.marvelapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erickpimentel.marvelapp.data.local.entity.CharacterRemoteKeys

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM CharacterRemoteKeys WHERE id =:id")
    suspend fun getRemoteKeys(id: Int): CharacterRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<CharacterRemoteKeys>)

    @Query("DELETE FROM CharacterRemoteKeys")
    suspend fun deleteAllRemoteKeys()
}