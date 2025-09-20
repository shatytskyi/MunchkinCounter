package com.shatytskyi.gamecounter.rate

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RateAppPreferences(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("rate_app_prefs", Context.MODE_PRIVATE)

    private val _userChoiceFlow = MutableStateFlow(getUserChoice())
    val userChoiceFlow: Flow<RateAppChoice> = _userChoiceFlow.asStateFlow()

    private val _hasShownLevel10DialogFlow = MutableStateFlow(hasShownLevel10Dialog())
    val hasShownLevel10DialogFlow: Flow<Boolean> = _hasShownLevel10DialogFlow.asStateFlow()

    companion object {
        private const val KEY_HAS_SHOWN_LEVEL_10_DIALOG = "has_shown_level_10_dialog"
        private const val KEY_USER_CHOICE = "user_rate_choice"
    }

    fun hasShownLevel10Dialog(): Boolean {
        return prefs.getBoolean(KEY_HAS_SHOWN_LEVEL_10_DIALOG, false)
    }

    suspend fun setHasShownLevel10Dialog(shown: Boolean) {
        prefs.edit().putBoolean(KEY_HAS_SHOWN_LEVEL_10_DIALOG, shown).apply()
        _hasShownLevel10DialogFlow.value = shown
    }

    fun getUserChoice(): RateAppChoice {
        val choice = prefs.getString(KEY_USER_CHOICE, RateAppChoice.NONE.name) ?: RateAppChoice.NONE.name
        return try {
            RateAppChoice.valueOf(choice)
        } catch (e: IllegalArgumentException) {
            RateAppChoice.NONE
        }
    }

    suspend fun setUserChoice(choice: RateAppChoice) {
        prefs.edit().putString(KEY_USER_CHOICE, choice.name).apply()
        _userChoiceFlow.value = choice
    }

    fun observeUserChoice(): Flow<RateAppChoice> = userChoiceFlow

    fun observeHasShownLevel10Dialog(): Flow<Boolean> = hasShownLevel10DialogFlow
}
