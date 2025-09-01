package com.shatytskyi.munchcounter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shatytskyi.munchcounter.data.ThemePreferences
import com.shatytskyi.munchcounter.ui.screens.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themePreferences: ThemePreferences
) : ViewModel() {
    
    val themeMode: StateFlow<ThemeMode> = themePreferences.themeMode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = ThemeMode.AUTO
    )
    
    fun setThemeMode(mode: ThemeMode) {
        themePreferences.setThemeMode(mode)
    }
}