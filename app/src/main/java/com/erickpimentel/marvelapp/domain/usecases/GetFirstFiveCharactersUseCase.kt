package com.erickpimentel.marvelapp.domain.usecases

import com.erickpimentel.marvelapp.data.remote.dto.CharactersDTO
import com.erickpimentel.marvelapp.data.remote.network.ApiResult
import com.erickpimentel.marvelapp.data.remote.repository.MarvelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class GetFirstFiveCharactersUseCase @Inject constructor(private val marvelRepository: MarvelRepository) {
    suspend operator fun invoke(): ApiResult<CharactersDTO> {
        return withContext(Dispatchers.IO) {
            try {
                ApiResult.Success(
                    marvelRepository.getCharacters(null, 0, 5)
                )
            } catch (e: Exception) {
                ApiResult.Error(e)
            }
        }
    }
}