package com.erickpimentel.marvelapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erickpimentel.marvelapp.data.network.ApiResult
import com.erickpimentel.marvelapp.domain.usecases.GetAllCharactersUseCase
import com.erickpimentel.marvelapp.presentation.ui.MarvelListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase
) : ViewModel() {
    private val _marvelValue = MutableStateFlow(MarvelListState())
    var marvelValue : StateFlow<MarvelListState> = _marvelValue
    fun getAllCharactersData(offset: Int) = viewModelScope.launch(Dispatchers.IO) {
        getAllCharactersUseCase.invoke(offset = offset).collect {
            when(it){
                is ApiResult.Success -> {
                    _marvelValue.value = MarvelListState(characterList = it.data?: emptyList())
                }
                is ApiResult.Loading -> {
                    _marvelValue.value = MarvelListState(isLoading = true)
                }
                is ApiResult.Error -> {
                    _marvelValue.value = MarvelListState(error = it.message?: "An Unexpected Error")
                }
            }
        }
    }
}