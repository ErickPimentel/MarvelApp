package com.erickpimentel.marvelapp.presentation.ui

import com.erickpimentel.marvelapp.domain.model.Character

class MarvelListState(
    val isLoading: Boolean = false,
    val characterList: List<Character> = emptyList(),
    val error: String = ""
)