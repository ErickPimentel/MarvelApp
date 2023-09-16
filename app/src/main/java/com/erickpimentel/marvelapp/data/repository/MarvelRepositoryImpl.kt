package com.erickpimentel.marvelapp.data.repository

import com.erickpimentel.marvelapp.data.api.MarvelApiService
import com.erickpimentel.marvelapp.data.dto.CharactersDTO
import retrofit2.Response
import javax.inject.Inject

class MarvelRepositoryImpl @Inject constructor(private val marvelApiService: MarvelApiService):
    MarvelRepository {
    override suspend fun getCharacters(nameStartsWith: String?, offset: Int, limit: Int): Response<CharactersDTO> {
        return marvelApiService.getCharacters(nameStartsWith = nameStartsWith, offset = offset.toString(), limit = limit.toString())
    }

}