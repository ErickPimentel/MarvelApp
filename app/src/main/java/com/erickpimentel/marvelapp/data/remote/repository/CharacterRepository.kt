package com.erickpimentel.marvelapp.data.remote.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.erickpimentel.marvelapp.data.local.database.CharacterDataBase
import com.erickpimentel.marvelapp.data.remote.api.CharacterApiService
import com.erickpimentel.marvelapp.domain.model.Character
import com.erickpimentel.marvelapp.presentation.paging.CharacterPagingSource
import com.erickpimentel.marvelapp.presentation.paging.CharacterRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalPagingApi
class CharacterRepository @Inject constructor(
    private val characterApi: CharacterApiService,
    private val quoteDatabase: CharacterDataBase
) {
    fun getCharacters() = Pager(
        config = PagingConfig(
            pageSize = 10
        ),
        remoteMediator = CharacterRemoteMediator(characterApi, quoteDatabase),
        pagingSourceFactory = { quoteDatabase.characterDao().getCharacter() }
    ).liveData
}
