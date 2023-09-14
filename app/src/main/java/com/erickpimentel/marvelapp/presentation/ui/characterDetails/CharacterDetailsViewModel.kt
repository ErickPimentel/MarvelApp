package com.erickpimentel.marvelapp.presentation.ui.characterDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erickpimentel.marvelapp.domain.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor() : ViewModel() {

    private val _currentCharacter = MutableLiveData<Character>()
    val currentCharacter: LiveData<Character> get() = _currentCharacter

    private fun insertCurrentCharacter(character: Character){
        _currentCharacter.value = character
    }

    fun setCurrentCharacter(character: Character){
        insertCurrentCharacter(character)
    }

}