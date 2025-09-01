package com.shatytskyi.munchcounter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shatytskyi.munchcounter.data.ThemePreferences
import com.shatytskyi.munchcounter.ui.screens.ThemeMode
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ThemeViewModel(
    private val themePreferences: ThemePreferences
) : ViewModel() {
    
    val themeMode: StateFlow<ThemeMode> = themePreferences.themeMode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = ThemeMode.AUTO
    )
    
    val dynamicColors: StateFlow<Boolean> = themePreferences.dynamicColors.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )
    
    fun setThemeMode(mode: ThemeMode) {
        themePreferences.setThemeMode(mode)
    }
    
    fun setDynamicColors(enabled: Boolean) {
        themePreferences.setDynamicColors(enabled)
    }
}