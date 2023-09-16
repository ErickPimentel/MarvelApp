package com.erickpimentel.marvelapp.data.remote.repository

import com.erickpimentel.marvelapp.data.remote.api.CharacterApiService
import com.erickpimentel.marvelapp.data.remote.dto.CharactersDTO
import retrofit2.Response
import javax.inject.Inject

class MarvelRepositoryImpl @Inject constructor(private val characterApiService: CharacterApiService):
    MarvelRepository {
    override suspend fun getCharacters(nameStartsWith: String?, offset: Int, limit: Int): CharactersDTO {
        return characterApiService.getCharacters(nameStartsWith = nameStartsWith, offset = offset, limit = limit)
    }

}