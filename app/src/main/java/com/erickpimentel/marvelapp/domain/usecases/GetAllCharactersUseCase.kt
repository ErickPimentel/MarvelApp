package com.erickpimentel.marvelapp.domain.usecases

import com.erickpimentel.marvelapp.data.network.ApiResult
import com.erickpimentel.marvelapp.domain.model.Character
import com.erickpimentel.marvelapp.domain.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(private val repository: MarvelRepository){
    operator fun invoke(offset: Int): Flow<ApiResult<List<Character>>> = flow {
        try {
            emit(ApiResult.Loading<List<Character>>())
            val list = repository.getAllCharacters(offset = offset).data.results.map { it.toCharacter() }
            emit(ApiResult.Success<List<Character>>(list))
        } catch (e: HttpException){
            emit(ApiResult.Error<List<Character>>(e.printStackTrace().toString()))
        } catch (e: IOException){
            emit(ApiResult.Error<List<Character>>(e.printStackTrace().toString()))
        }
    }
}