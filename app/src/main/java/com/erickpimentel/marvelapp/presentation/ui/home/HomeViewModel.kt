package com.erickpimentel.marvelapp.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.erickpimentel.marvelapp.data.dto.CharactersDTO
import com.erickpimentel.marvelapp.data.network.ApiResult
import com.erickpimentel.marvelapp.domain.model.Character
import com.erickpimentel.marvelapp.domain.usecases.GetCharactersUseCase
import com.erickpimentel.marvelapp.domain.usecases.GetFirstFiveCharactersUseCase
import com.erickpimentel.marvelapp.presentation.paging.CharacterPagingSource
import com.erickpimentel.marvelapp.util.SingleUseException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getFirstFiveCharactersUseCase: GetFirstFiveCharactersUseCase
) : ViewModel() {

    val charactersList = getSearchResultStream().cachedIn(viewModelScope)
    private fun getSearchResultStream(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(20),
            pagingSourceFactory = { CharacterPagingSource(getCharactersUseCase, null) }
        ).flow
    }

    suspend fun getFirstFiveCharacters(): ApiResult<Response<CharactersDTO>> {
        return getFirstFiveCharactersUseCase.invoke()
    }
}