package com.shatytskyi.munchcounter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shatytskyi.munchcounter.data.Character
import com.shatytskyi.munchcounter.data.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    val characters: StateFlow<List<Character>> = repository.characters.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            repository.loadCharacters()
        }
    }

    fun addCharacter(name: String) {
        if (name.isBlank()) {
            return
        }

        viewModelScope.launch {
            repository.addCharacter(name)
        }
    }

    fun removeCharacter(id: Long) {
        viewModelScope.launch {
            repository.removeCharacter(id)
        }
    }

    fun updateCharacter(id: Long, name: String, level: Int, power: Int) {
        viewModelScope.launch {
            repository.updateCharacter(id, name, level, power)
        }
    }

    fun resetCharacter(id: Long) {
        viewModelScope.launch {
            repository.resetCharacter(id)
        }
    }

    fun resetAllCharacters() {
        viewModelScope.launch {
            repository.resetAllCharacters()
        }
    }

    fun removeAllCharacters() {
        viewModelScope.launch {
            repository.createBackup()
            repository.removeAllCharacters()
        }
    }

    fun undoRemoveAll() {
        viewModelScope.launch {
            repository.restoreFromBackup()
        }
    }

    fun changePower(id: Long, value: Int) {
        viewModelScope.launch {
            repository.changePower(id, value)
        }
    }

    fun changeLevel(id: Long, value: Int) {
        viewModelScope.launch {
            repository.changeLevel(id, value)
        }
    }

    fun findCharacterById(id: Long, callback: (Character?) -> Unit) {
        viewModelScope.launch {
            val character = repository.findCharacterById(id)
            callback(character)
        }
    }
}
