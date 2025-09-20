package com.shatytskyi.gamecounter.rate

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RateAppManager(
    private val context: Context,
    private val preferences: RateAppPreferences,
) {
    private val _shouldShowDialog = MutableStateFlow(false)
    val shouldShowDialog: Flow<Boolean> = _shouldShowDialog.asStateFlow()

    suspend fun checkLevel10Achievement(): Boolean {
        if (preferences.hasShownLevel10Dialog()) {
            return false
        }

        if (preferences.getUserChoice() == RateAppChoice.NEVER) {
            return false
        }

        return true
    }

    suspend fun onLevel10Achieved() {
        if (checkLevel10Achievement()) {
            // Show our custom dialog
            _shouldShowDialog.value = true
            preferences.setHasShownLevel10Dialog(true)
        }
    }

    fun dismissDialog() {
        _shouldShowDialog.value = false
    }

    suspend fun onRateNowClicked() {
        preferences.setUserChoice(RateAppChoice.RATED)
        preferences.setHasShownLevel10Dialog(true)
        dismissDialog()
        // Open Play Store directly
        openPlayStore()
    }

    suspend fun onRemindLaterClicked() {
        preferences.setUserChoice(RateAppChoice.LATER)
        preferences.setHasShownLevel10Dialog(true)
        dismissDialog()
    }

    suspend fun onNeverClicked() {
        preferences.setUserChoice(RateAppChoice.NEVER)
        preferences.setHasShownLevel10Dialog(true)
        dismissDialog()
    }

    fun openPlayStore() {
        // Hardcoded package name to work in debug builds
        val packageName = "com.shatytskyi.gamecounter"
        try {
            // Try to open Play Store app
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("market://details?id=$packageName")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // If Play Store app is not available, open in browser
            try {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(context, "Unable to open Play Store", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun shareApp() {
        val packageName = "com.shatytskyi.gamecounter"
        val shareText = "Check out Munchkin Counter - the best way to track your Munchkin game!\n\nhttps://play.google.com/store/apps/details?id=$packageName"

        try {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            val chooserIntent = Intent.createChooser(shareIntent, "Share Munchkin Counter").apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(chooserIntent)
        } catch (e: Exception) {
            Toast.makeText(context, "Unable to share app", Toast.LENGTH_SHORT).show()
        }
    }
}

enum class RateAppChoice {
    NONE,
    RATED,
    LATER,
    NEVER
}
