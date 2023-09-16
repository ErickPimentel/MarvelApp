package com.erickpimentel.marvelapp.domain.usecases

import com.erickpimentel.marvelapp.data.dto.CharactersDTO
import com.erickpimentel.marvelapp.data.repository.MarvelRepository
import retrofit2.Response
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val marvelRepository: MarvelRepository) {
    suspend operator fun invoke(nameStartsWith: String?,offset: Int, limit: Int): Response<CharactersDTO> {
        return marvelRepository.getCharacters(nameStartsWith, offset, limit)
    }
}