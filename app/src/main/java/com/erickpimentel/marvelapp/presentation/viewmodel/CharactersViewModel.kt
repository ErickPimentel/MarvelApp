package com.erickpimentel.marvelapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.erickpimentel.marvelapp.domain.model.Character
import com.erickpimentel.marvelapp.domain.usecases.GetAllCharactersUseCase
import com.erickpimentel.marvelapp.presentation.paging.CharacterPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase
) : ViewModel() {

//    private val _marvelValue = MutableStateFlow(MarvelListState())
//    var marvelValue : StateFlow<MarvelListState> = _marvelValue
//    fun getAllCharactersData(offset: Int) = viewModelScope.launch(Dispatchers.IO) {
//        getAllCharactersUseCase.invoke(offset = offset).collect {
//            when(it){
//                is ApiResult.Success -> {
//                    _marvelValue.value = MarvelListState(characterList = it.data?: emptyList())
//                    Log.d("CharactersViewModel", "Success - getAllCharactersData - it.data: ${it.data}")
//                }
//                is ApiResult.Loading -> {
//                    _marvelValue.value = MarvelListState(isLoading = true)
//                    Log.d("CharactersViewModel", "Loading")
//                }
//                is ApiResult.Error -> {
//                    _marvelValue.value = MarvelListState(error = it.message?: "An Unexpected Error")
//                    Log.d("CharactersViewModel", "getAllCharactersData - it.message: ${it.message}")
//                }
//            }
//        }
//    }

    val charactersList = getSearchResultStream().cachedIn(viewModelScope)
    fun getSearchResultStream(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(1),
            pagingSourceFactory = { CharacterPagingSource(getAllCharactersUseCase) }
        ).flow
    }
}