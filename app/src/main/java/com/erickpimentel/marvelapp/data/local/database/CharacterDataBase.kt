package com.erickpimentel.marvelapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.erickpimentel.marvelapp.data.local.dao.CharacterDao
import com.erickpimentel.marvelapp.data.local.dao.RemoteKeysDao
import com.erickpimentel.marvelapp.data.local.entity.CharacterRemoteKeys
import com.erickpimentel.marvelapp.domain.model.Character

@Database(entities = [Character::class, CharacterRemoteKeys::class], version = 6)
abstract class CharacterDataBase : RoomDatabase() {
    abstract fun characterDao() : CharacterDao
    abstract fun remoteKeysDao() : RemoteKeysDao
}