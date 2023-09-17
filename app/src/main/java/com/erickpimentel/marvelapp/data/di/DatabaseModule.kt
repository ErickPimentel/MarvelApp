package com.erickpimentel.marvelapp.data.di

import android.content.Context
import androidx.room.Room
import com.erickpimentel.marvelapp.data.local.database.CharacterDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : CharacterDataBase {
        return Room.databaseBuilder(context, CharacterDataBase::class.java, "characterDB")
            .fallbackToDestructiveMigration()
            .build()
    }
}