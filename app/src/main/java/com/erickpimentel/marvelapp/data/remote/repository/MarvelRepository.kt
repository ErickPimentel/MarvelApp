package com.erickpimentel.marvelapp.data.remote.repository

import com.erickpimentel.marvelapp.data.remote.dto.CharactersDTO
import retrofit2.Response

interface MarvelRepository {
    suspend fun getCharacters(nameStartsWith: String?, offset: Int, limit: Int): CharactersDTO
}