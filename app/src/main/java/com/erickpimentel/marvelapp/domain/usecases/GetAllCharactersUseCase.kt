package com.erickpimentel.marvelapp.domain.usecases

import com.erickpimentel.marvelapp.data.dto.CharactersDTO
import com.erickpimentel.marvelapp.domain.repository.MarvelRepository
import retrofit2.Response
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(private val marvelRepository: MarvelRepository) {
    suspend operator fun invoke(nameStartsWith: String?,offset: Int, limit: Int): Response<CharactersDTO> {
        return marvelRepository.getAllCharacters(nameStartsWith, offset, limit)
    }
}