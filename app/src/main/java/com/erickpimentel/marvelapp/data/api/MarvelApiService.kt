package com.erickpimentel.marvelapp.data.api

import com.erickpimentel.marvelapp.data.dto.CharactersDTO
import com.erickpimentel.marvelapp.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApiService {

    @GET("/v1/public/characters")
    suspend fun getAllCharacters(
        @Query("apikey") apikey: String = Constants.API_KEY,
        @Query("ts") ts: String = Constants.timeStamp,
        @Query("hash") hash: String = Constants.hash(),
        @Query("offset") offset: String,
        @Query("limit") limit: String,
    ): Response<CharactersDTO>

}