package com.shatytskyi.munchcounter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shatytskyi.munchcounter.data.Character
import com.shatytskyi.munchcounter.data.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
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
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
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
            _error.value = "Character name cannot be blank"
            return
        }
        
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.addCharacter(name)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to add character"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun removeCharacter(id: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.removeCharacter(id)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to remove character"
            } finally {
                _isLoading.value = false
            }
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
            _isLoading.value = true
            try {
                repository.resetAllCharacters()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to reset all characters"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun removeAllCharacters() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.createBackup()
                repository.removeAllCharacters()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to remove all characters"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun undoRemoveAll() {
        viewModelScope.launch {
            repository.restoreFromBackup()
        }
    }
    
    fun shuffleCharacters() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.shuffleCharacters()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to shuffle characters"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun changePower(id: Long, value: Int) {
        viewModelScope.launch {
            try {
                repository.changePower(id, value)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to change power"
            }
        }
    }
    
    fun changeLevel(id: Long, value: Int) {
        viewModelScope.launch {
            try {
                repository.changeLevel(id, value)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to change level"
            }
        }
    }
    
    fun findCharacterById(id: Long, callback: (Character?) -> Unit) {
        viewModelScope.launch {
            val character = repository.findCharacterById(id)
            callback(character)
        }
    }
}