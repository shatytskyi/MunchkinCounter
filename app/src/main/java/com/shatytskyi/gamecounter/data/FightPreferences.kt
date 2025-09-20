package com.shatytskyi.gamecounter.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

interface FightPreferences {
    fun hasShownFightInfoMessage(): Boolean
    fun setFightInfoMessageShown(shown: Boolean)
}

class FightPreferencesImpl(
    private val context: Context
) : FightPreferences {

    companion object {
        private const val PREFS_NAME = "fight_preferences"
        private const val KEY_INFO_MESSAGE_SHOWN = "info_message_shown"
    }

    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun hasShownFightInfoMessage(): Boolean {
        return sharedPrefs.getBoolean(KEY_INFO_MESSAGE_SHOWN, false)
    }

    override fun setFightInfoMessageShown(shown: Boolean) {
        sharedPrefs.edit { putBoolean(KEY_INFO_MESSAGE_SHOWN, shown) }
    }
}