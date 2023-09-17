package com.erickpimentel.marvelapp.data.repository

import com.erickpimentel.marvelapp.data.dto.CharactersDTO
import retrofit2.Response

interface MarvelRepository {
    suspend fun getCharacters(nameStartsWith: String?, offset: Int, limit: Int): Response<CharactersDTO>
}