package com.erickpimentel.marvelapp.presentation.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.erickpimentel.marvelapp.data.local.database.CharacterDataBase
import com.erickpimentel.marvelapp.data.local.entity.CharacterRemoteKeys
import com.erickpimentel.marvelapp.data.remote.api.CharacterApiService
import com.erickpimentel.marvelapp.domain.model.Character
import java.lang.Exception

@ExperimentalPagingApi
class CharacterRemoteMediator(
    private val characterApi: CharacterApiService,
    private val characterDataBase: CharacterDataBase
) : RemoteMediator<Int, Character>() {

    private val characterDao = characterDataBase.characterDao()
    private val characterRemoteKeysDao = characterDataBase.remoteKeysDao()

    private var limit = 10

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Character>): MediatorResult {
        return try {

            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    Log.d("CharacterRemoteMediator", "LoadType.PREPEND - prevPage: $prevPage")
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    Log.d("CharacterRemoteMediator", "LoadType.APPEND - remoteKeys: ${remoteKeys.toString()}")
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    Log.d("CharacterRemoteMediator", "LoadType.APPEND - nextPage: $nextPage")
                    nextPage
                }
            }

            Log.d("CharacterRemoteMediator", "currentPage: $currentPage")

            val offset = currentPage.times(limit)
            Log.d("CharacterRemoteMediator", "offset: $offset")


            val response = characterApi.getCharacters(null, offset = offset, limit = limit)

            val endOfPaginationReached = response.data.total <= (offset)

            val prevPage = if(currentPage == 1) null else currentPage -1
            val nextPage = if(endOfPaginationReached) null else currentPage + 1

            characterDataBase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    characterDao.deleteCharacters()
                    characterRemoteKeysDao.deleteAllRemoteKeys()
                }

                val characters = response.data.results.map { it.toCharacter() } ?: emptyList()

                characterDao.addCharacter(characters)

                Log.d("CharacterRemoteMediator", "prevPage: $prevPage")
                Log.d("CharacterRemoteMediator", "nextPage: $nextPage")

                val keys = response.data.results.map { character ->
                    CharacterRemoteKeys(
                        id = character.id,
                        prevPage = prevPage,
                        nextPage = nextPage,
                        endOfPaginationReached = endOfPaginationReached
                    )
                }

                characterRemoteKeysDao.addAllRemoteKeys(keys)
            }

            MediatorResult.Success(endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Character>
    ): CharacterRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                characterRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Character>
    ): CharacterRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { character ->
                characterRemoteKeysDao.getRemoteKeys(id = character.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Character>,
    ): CharacterRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { character ->
                characterRemoteKeysDao.getRemoteKeys(id = character.id)
            }
    }

}