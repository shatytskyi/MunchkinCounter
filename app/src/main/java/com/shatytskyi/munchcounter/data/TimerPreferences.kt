package com.shatytskyi.munchcounter.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface TimerPreferences {
    val selectedSeconds: Flow<Int>
    fun setSelectedSeconds(seconds: Int)
}

class TimerPreferencesImpl(
    private val context: Context
) : TimerPreferences {
    
    companion object {
        private const val PREFS_NAME = "timer_preferences"
        private const val KEY_SELECTED_SECONDS = "selected_seconds"
        private const val DEFAULT_SECONDS = 5
    }
    
    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    private val _selectedSeconds = MutableStateFlow(getCurrentSelectedSeconds())
    override val selectedSeconds: Flow<Int> = _selectedSeconds.asStateFlow()
    
    private fun getCurrentSelectedSeconds(): Int {
        return sharedPrefs.getInt(KEY_SELECTED_SECONDS, DEFAULT_SECONDS)
    }
    
    override fun setSelectedSeconds(seconds: Int) {
        sharedPrefs.edit { putInt(KEY_SELECTED_SECONDS, seconds) }
        _selectedSeconds.value = seconds
    }
}