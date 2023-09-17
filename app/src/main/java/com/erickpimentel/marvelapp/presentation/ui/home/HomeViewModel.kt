package com.erickpimentel.marvelapp.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.erickpimentel.marvelapp.data.network.ApiResult
import com.erickpimentel.marvelapp.domain.model.Character
import com.erickpimentel.marvelapp.domain.usecases.GetCharactersUseCase
import com.erickpimentel.marvelapp.domain.usecases.GetFirstFiveCharactersUseCase
import com.erickpimentel.marvelapp.presentation.paging.CharacterPagingSource
import com.erickpimentel.marvelapp.util.SingleUseException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getFirstFiveCharactersUseCase: GetFirstFiveCharactersUseCase
) : ViewModel() {

    init {
        fetchFirstFiveCharacters()
    }

    private val _errorMessage = MutableLiveData<SingleUseException<String>>()
    val errorMessage: LiveData<SingleUseException<String>> get() = _errorMessage

    private var _charactersList = getCharactersPagingLiveData().cachedIn(viewModelScope)
    val charactersList get() = _charactersList

    private fun getCharactersPagingLiveData(): LiveData<PagingData<Character>> {
        return Pager(
            config = PagingConfig(20),
            pagingSourceFactory = { CharacterPagingSource(getCharactersUseCase, null) }
        ).liveData
    }

    fun refreshCharactersList(){
        viewModelScope.launch {
            _charactersList = getCharactersPagingLiveData().cachedIn(viewModelScope)
        }
    }

    private var _firstFiveCharactersList: MutableLiveData<List<Character>> = MutableLiveData(emptyList())
    val firstFiveCharactersList get() = _firstFiveCharactersList

    fun fetchFirstFiveCharacters() {
        viewModelScope.launch {
            when (val apiResult = getFirstFiveCharactersUseCase.invoke()) {
                is ApiResult.Success -> {
                    val characters = apiResult.response.body()?.data?.results?.map { it.toCharacter() } ?: emptyList()
                    _firstFiveCharactersList.value = characters
                }

                is ApiResult.Error -> {
                    setErrorMessage(apiResult.exception.message)
                }
            }
        }
    }

    fun setErrorMessage(errorMessage: String?){
        _errorMessage.value = SingleUseException(errorMessage)
    }

}