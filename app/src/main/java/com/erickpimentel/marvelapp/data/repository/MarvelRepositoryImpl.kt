package com.erickpimentel.marvelapp.data.repository

import com.erickpimentel.marvelapp.data.api.MarvelApiService
import com.erickpimentel.marvelapp.data.dto.CharactersDTO
import com.erickpimentel.marvelapp.domain.repository.MarvelRepository
import javax.inject.Inject

class MarvelRepositoryImpl @Inject constructor(private val marvelApiService: MarvelApiService): MarvelRepository{
    override suspend fun getAllCharacters(offset: Int): CharactersDTO {
        return marvelApiService.getAllCharacters(offset = offset.toString())
    }

}