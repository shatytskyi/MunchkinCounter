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

/**
 * Debug implementation - only logs events to console
 */
class DebugAnalyticsManager : AnalyticsManager {
    
    override fun logScreenView(screenName: String, screenClass: String?) {
        Log.d("AnalyticsManager", "Track event: SCREEN_VIEW - screenName: $screenName, screenClass: $screenClass")
    }
    
    override fun logEvent(eventName: String, params: Bundle?) {
        Log.d("AnalyticsManager", "Track event: $eventName - params: $params")
    }
    
    override fun setUserProperty(name: String, value: String?) {
        Log.d("AnalyticsManager", "Track event: SET_USER_PROPERTY - name: $name, value: $value")
    }
    
    override fun setUserId(userId: String?) {
        Log.d("AnalyticsManager", "Track event: SET_USER_ID - userId: ${userId?.take(5)}...")
    }
}

/**
 * Release implementation - sends real events to Firebase
 */
class ReleaseAnalyticsManager : AnalyticsManager {
    
    private val firebaseAnalytics: FirebaseAnalytics = Firebase.analytics
    
    init {
        firebaseAnalytics.setAnalyticsCollectionEnabled(true)
        Log.d("AnalyticsManager", "Firebase Analytics initialized for Release")
    }
    
    override fun logScreenView(screenName: String, screenClass: String?) {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            screenClass?.let { putString(FirebaseAnalytics.Param.SCREEN_CLASS, it) }
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }
    
    override fun logEvent(eventName: String, params: Bundle?) {
        firebaseAnalytics.logEvent(eventName, params)
    }
    
    override fun setUserProperty(name: String, value: String?) {
        firebaseAnalytics.setUserProperty(name, value)
    }
    
    override fun setUserId(userId: String?) {
        firebaseAnalytics.setUserId(userId)
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