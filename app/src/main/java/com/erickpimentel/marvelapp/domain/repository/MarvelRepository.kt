package com.erickpimentel.marvelapp.domain.repository

import com.erickpimentel.marvelapp.data.dto.CharactersDTO

interface MarvelRepository {
    suspend fun getAllCharacters(offset: Int): CharactersDTO
}