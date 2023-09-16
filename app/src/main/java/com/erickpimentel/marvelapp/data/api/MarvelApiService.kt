package com.erickpimentel.marvelapp.data.api

import com.erickpimentel.marvelapp.data.dto.CharactersDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApiService {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("nameStartsWith") nameStartsWith: String?,
        @Query("offset") offset: String,
        @Query("limit") limit: String,
    ): Response<CharactersDTO>

}