package com.erickpimentel.marvelapp.presentation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.erickpimentel.marvelapp.domain.model.Character
import com.erickpimentel.marvelapp.domain.usecases.GetCharactersUseCase
import com.erickpimentel.marvelapp.presentation.ui.adapter.CharacterAdapter
import com.erickpimentel.marvelapp.presentation.paging.CharacterPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _currentQuery = MutableLiveData<String?>()
    val currentQuery: LiveData<String?> get() = _currentQuery

    private val _suggestionsList = arrayListOf<String>()
    val suggestionsList: ArrayList<String> = _suggestionsList

    private fun insertCurrentQuery(query: String?){
        _currentQuery.value = query
    }

    fun updateQuery(query: String?){
        insertCurrentQuery(query)
    }

    private fun insertSuggestion(suggestion: String){
        _suggestionsList.add(suggestion)
    }

    fun addSuggestion(suggestion: String){
        if (!suggestionsList.contains(suggestion)) insertSuggestion(suggestion)
    }

    private fun getSearchResultStream(nameStartsWith: String?): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(20),
            pagingSourceFactory = { CharacterPagingSource(getCharactersUseCase, nameStartsWith) }
        ).flow
    }

    var charactersListSearch = getSearchResultStream(nameStartsWith = currentQuery.value).cachedIn(viewModelScope)
    fun refreshCharactersList(characterAdapter: CharacterAdapter){
        viewModelScope.launch {
            charactersListSearch = getSearchResultStream(
                nameStartsWith = currentQuery.value
            ).cachedIn(viewModelScope)
            charactersListSearch.collect{
                characterAdapter.submitData(it)
            }
        }
    }
}