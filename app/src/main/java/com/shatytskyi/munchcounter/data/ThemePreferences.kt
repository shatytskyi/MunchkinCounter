package com.shatytskyi.munchcounter.data

import android.content.Context
import android.content.SharedPreferences
import com.shatytskyi.munchcounter.ui.screens.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.core.content.edit

interface ThemePreferences {
    val themeMode: Flow<ThemeMode>
    fun setThemeMode(mode: ThemeMode)
}

class ThemePreferencesImpl(
    private val context: Context
) : ThemePreferences {
    
    companion object {
        private const val PREFS_NAME = "theme_preferences"
        private const val KEY_THEME_MODE = "theme_mode"
    }
    
    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    private val _themeMode = MutableStateFlow(getCurrentThemeMode())
    override val themeMode: Flow<ThemeMode> = _themeMode.asStateFlow()
    
    private fun getCurrentThemeMode(): ThemeMode {
        val savedMode = sharedPrefs.getString(KEY_THEME_MODE, ThemeMode.AUTO.name)
        return try {
            ThemeMode.valueOf(savedMode ?: ThemeMode.AUTO.name)
        } catch (e: IllegalArgumentException) {
            ThemeMode.AUTO
        }
    }
    
    override fun setThemeMode(mode: ThemeMode) {
        sharedPrefs.edit { putString(KEY_THEME_MODE, mode.name) }
        _themeMode.value = mode
    }
}
