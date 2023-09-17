package com.erickpimentel.marvelapp.domain.usecases

import com.erickpimentel.marvelapp.data.remote.dto.CharactersDTO
import com.erickpimentel.marvelapp.data.remote.repository.MarvelRepository
import retrofit2.Response
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val marvelRepository: MarvelRepository) {
    suspend operator fun invoke(nameStartsWith: String?,offset: Int, limit: Int): CharactersDTO {
        return marvelRepository.getCharacters(nameStartsWith, offset, limit)
    }
}