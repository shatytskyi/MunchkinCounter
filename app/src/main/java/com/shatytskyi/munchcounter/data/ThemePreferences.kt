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
    val dynamicColors: Flow<Boolean>
    val systemFont: Flow<Boolean>
    fun setThemeMode(mode: ThemeMode)
    fun setDynamicColors(enabled: Boolean)
    fun setSystemFont(enabled: Boolean)
}

class ThemePreferencesImpl(
    private val context: Context
) : ThemePreferences {
    
    companion object {
        private const val PREFS_NAME = "theme_preferences"
        private const val KEY_THEME_MODE = "theme_mode"
        private const val KEY_DYNAMIC_COLORS = "dynamic_colors"
        private const val KEY_SYSTEM_FONT = "system_font"
    }
    
    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    private val _themeMode = MutableStateFlow(getCurrentThemeMode())
    override val themeMode: Flow<ThemeMode> = _themeMode.asStateFlow()
    
    private val _dynamicColors = MutableStateFlow(getCurrentDynamicColors())
    override val dynamicColors: Flow<Boolean> = _dynamicColors.asStateFlow()
    
    private val _systemFont = MutableStateFlow(getCurrentSystemFont())
    override val systemFont: Flow<Boolean> = _systemFont.asStateFlow()
    
    private fun getCurrentThemeMode(): ThemeMode {
        val savedMode = sharedPrefs.getString(KEY_THEME_MODE, ThemeMode.AUTO.name)
        return try {
            ThemeMode.valueOf(savedMode ?: ThemeMode.AUTO.name)
        } catch (e: IllegalArgumentException) {
            ThemeMode.AUTO
        }
    }
    
    private fun getCurrentDynamicColors(): Boolean {
        return sharedPrefs.getBoolean(KEY_DYNAMIC_COLORS, false)
    }
    
    private fun getCurrentSystemFont(): Boolean {
        return sharedPrefs.getBoolean(KEY_SYSTEM_FONT, false)
    }
    
    override fun setThemeMode(mode: ThemeMode) {
        sharedPrefs.edit { putString(KEY_THEME_MODE, mode.name) }
        _themeMode.value = mode
    }
    
    override fun setDynamicColors(enabled: Boolean) {
        sharedPrefs.edit { putBoolean(KEY_DYNAMIC_COLORS, enabled) }
        _dynamicColors.value = enabled
    }
    
    override fun setSystemFont(enabled: Boolean) {
        sharedPrefs.edit { putBoolean(KEY_SYSTEM_FONT, enabled) }
        _systemFont.value = enabled
    }
}
