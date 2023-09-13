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

    val charactersList = getSearchResultStream().cachedIn(viewModelScope)
    fun getSearchResultStream(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(1),
            pagingSourceFactory = { CharacterPagingSource(getAllCharactersUseCase, null) }
        ).flow
    }
}