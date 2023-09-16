package com.erickpimentel.marvelapp.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.erickpimentel.marvelapp.data.local.database.CharacterDataBase
import com.erickpimentel.marvelapp.data.remote.api.CharacterApiService
import com.erickpimentel.marvelapp.data.remote.dto.CharactersDTO
import com.erickpimentel.marvelapp.data.remote.network.ApiResult
import com.erickpimentel.marvelapp.data.remote.repository.CharacterRepository
import com.erickpimentel.marvelapp.domain.model.Character
import com.erickpimentel.marvelapp.domain.usecases.GetCharactersUseCase
import com.erickpimentel.marvelapp.domain.usecases.GetFirstFiveCharactersUseCase
import com.erickpimentel.marvelapp.presentation.ui.adapter.CharacterAdapter
import com.erickpimentel.marvelapp.presentation.paging.CharacterPagingSource
import com.erickpimentel.marvelapp.presentation.paging.CharacterRemoteMediator
import com.erickpimentel.marvelapp.util.SingleUseException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getFirstFiveCharactersUseCase: GetFirstFiveCharactersUseCase,
    private val characterApiService: CharacterApiService,
    private val characterDataBase: CharacterDataBase,
    private val charactersRepository: CharacterRepository
) : ViewModel() {

    private val _errorMessage = MutableLiveData<SingleUseException<String>>()
    val errorMessage: LiveData<SingleUseException<String>> get() = _errorMessage

//    var charactersList = getSearchResultStream().cachedIn(viewModelScope)
//    private fun getSearchResultStream(): Flow<PagingData<Character>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = 20,
//                maxSize = 100,
//                prefetchDistance = 5
//            ),
//            remoteMediator = CharacterRemoteMediator(characterApiService, characterDataBase),
//            pagingSourceFactory = { characterDataBase.characterDao().getCharacter() },
//        ).flow
//    }
//    fun refreshCharactersList(characterAdapter: CharacterAdapter){
//        viewModelScope.launch {
//            charactersList = getSearchResultStream().cachedIn(viewModelScope)
//            charactersList.collect{
//                characterAdapter.submitData(it)
//            }
//        }
//    }

    suspend fun getFirstFiveCharacters(): ApiResult<CharactersDTO> {
        return getFirstFiveCharactersUseCase.invoke()
    }

    fun setErrorMessage(errorMessage: String?){
        _errorMessage.value = SingleUseException(errorMessage)
    }

    val charactersList = charactersRepository.getCharacters().cachedIn(viewModelScope)

}