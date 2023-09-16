package com.erickpimentel.marvelapp.data.remote.api

import com.erickpimentel.marvelapp.data.remote.dto.CharactersDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApiService {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("nameStartsWith") nameStartsWith: String?,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): CharactersDTO

}