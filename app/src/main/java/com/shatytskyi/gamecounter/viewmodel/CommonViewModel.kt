package com.shatytskyi.gamecounter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shatytskyi.gamecounter.data.Character
import com.shatytskyi.gamecounter.data.CharacterRepository
import com.shatytskyi.gamecounter.data.Gender
import com.shatytskyi.gamecounter.rate.RateAppManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class CommonViewModel(
    private val repository: CharacterRepository,
    private val rateAppManager: RateAppManager
) : ViewModel() {

    private val _isInitialLoading = MutableStateFlow(true)
    
    val characters: StateFlow<List<Character>> = repository.characters.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )
    
    val isLoading: StateFlow<Boolean> = combine(
        _isInitialLoading,
        characters
    ) { isInitialLoading, characterList ->
        isInitialLoading
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = true
    )

    val shouldShowRateDialog: StateFlow<Boolean> = rateAppManager.shouldShowDialog
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            delay(300)
            _isInitialLoading.value = false
        }
    }

    fun addCharacter(name: String, gender: Gender = Gender.MALE) {
        if (name.isBlank()) {
            return
        }

        viewModelScope.launch {
            repository.addCharacter(name, gender)
        }
    }

    fun removeCharacter(id: Long) {
        viewModelScope.launch {
            repository.removeCharacter(id)
        }
    }

    fun updateCharacter(id: Long, name: String, level: Int, power: Int, gender: Gender) {
        viewModelScope.launch {
            repository.updateCharacter(id, name, level, power, gender)
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
            repository.removeAllCharacters()
        }
    }

    fun changePower(id: Long, value: Int) {
        viewModelScope.launch {
            repository.changePower(id, value)
        }
    }

    fun changeLevel(id: Long, value: Int) {
        viewModelScope.launch {
            // First change the level
            repository.changeLevel(id, value)

            // Then check if we reached level 10
            val character = characters.value.find { it.id == id }
            if (character != null) {
                val newLevel = character.level + value
                if (newLevel == 10 && character.level < 10) {
                    // Character just reached level 10
                    // Small delay to ensure UI updates first
                    kotlinx.coroutines.delay(500)
                    rateAppManager.onLevel10Achieved()
                }
            }
        }
    }

    fun onRateNowClicked() {
        viewModelScope.launch {
            rateAppManager.onRateNowClicked()
        }
    }

    fun onRateLaterClicked() {
        viewModelScope.launch {
            rateAppManager.onRemindLaterClicked()
        }
    }

    fun onRateNeverClicked() {
        viewModelScope.launch {
            rateAppManager.onNeverClicked()
        }
    }

    fun dismissRateDialog() {
        rateAppManager.dismissDialog()
    }

    fun openPlayStore() {
        rateAppManager.openPlayStore()
    }

    fun shareApp() {
        rateAppManager.shareApp()
    }
    
    fun toggleGender(id: Long) {
        viewModelScope.launch {
            val character = characters.value.find { it.id == id }
            character?.let {
                val newGender = when (it.gender) {
                    Gender.MALE -> Gender.FEMALE
                    Gender.FEMALE -> Gender.MALE
                }
                repository.updateCharacter(id, it.name, it.level, it.items, newGender)
            }
        }
    }
}
