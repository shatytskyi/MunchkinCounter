package com.shatytskyi.munchcounter.analytics

import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics

/**
 * Analytics manager for tracking user behavior and app usage patterns
 * Following Firebase Analytics best practices for 2025
 */
interface AnalyticsManager {
    fun logScreenView(screenName: String, screenClass: String? = null)
    fun logEvent(eventName: String, params: Bundle? = null)
    fun setUserProperty(name: String, value: String?)
    fun setUserId(userId: String?)
}

class AnalyticsManagerImpl : AnalyticsManager {
    
    private val firebaseAnalytics: FirebaseAnalytics = Firebase.analytics
    
    init {
        // Enable analytics collection
        firebaseAnalytics.setAnalyticsCollectionEnabled(true)
        Log.d("AnalyticsManager", "Firebase Analytics initialized")
    }
    
    /**
     * Log screen view events for tracking user navigation patterns
     */
    override fun logScreenView(screenName: String, screenClass: String?) {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            screenClass?.let { putString(FirebaseAnalytics.Param.SCREEN_CLASS, it) }
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
        Log.d("AnalyticsManager", "Screen view: $screenName")
    }
    
    /**
     * Log custom or recommended events
     * Use FirebaseAnalytics.Event constants for standard events
     */
    override fun logEvent(eventName: String, params: Bundle?) {
        firebaseAnalytics.logEvent(eventName, params)
        Log.d("AnalyticsManager", "Event: $eventName, params: $params")
    }
    
    /**
     * Set user properties for audience segmentation
     * Examples: preferred_game_mode, player_level_range, active_features
     */
    override fun setUserProperty(name: String, value: String?) {
        firebaseAnalytics.setUserProperty(name, value)
        Log.d("AnalyticsManager", "User property: $name = $value")
    }
    
    /**
     * Set user ID for cross-device tracking (optional)
     */
    override fun setUserId(userId: String?) {
        firebaseAnalytics.setUserId(userId)
        Log.d("AnalyticsManager", "User ID set: ${userId?.take(5)}...")
    }
}

/**
 * Extension functions for easier parameter building
 */
fun bundleOf(vararg pairs: Pair<String, Any?>): Bundle = Bundle().apply {
    pairs.forEach { (key, value) ->
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Double -> putDouble(key, value)
            is Float -> putFloat(key, value)
            is Boolean -> putBoolean(key, value)
            null -> {} // Skip null values
            else -> putString(key, value.toString())
        }
    }
}