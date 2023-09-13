package com.erickpimentel.marvelapp.domain.repository

import com.erickpimentel.marvelapp.data.dto.CharactersDTO
import retrofit2.Response

interface MarvelRepository {
    suspend fun getAllCharacters(offset: Int, limit: Int): Response<CharactersDTO>
}