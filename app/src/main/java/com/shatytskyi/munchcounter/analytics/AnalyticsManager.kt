package com.shatytskyi.munchcounter.analytics

import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent

/**
 * Analytics manager for tracking user interactions and app events
 */
interface AnalyticsManager {
    fun trackEvent(eventName: String, params: Map<String, Any>? = null)
}

class AnalyticsManagerImpl : AnalyticsManager {
    
    private val firebaseAnalytics: FirebaseAnalytics = Firebase.analytics
    
    init {
        // Enable debug logging
        firebaseAnalytics.setAnalyticsCollectionEnabled(true)
        Log.d("AnalyticsManager", "Firebase Analytics initialized")
    }
    
    /**
     * Track an event with optional parameters
     * @param eventName The name of the event (use constants from Events object)
     * @param params Optional parameters as key-value pairs (use constants from EventParams object for keys)
     */
    override fun trackEvent(eventName: String, params: Map<String, Any>?) {
        Log.d("AnalyticsManager", "Tracking event: $eventName with params: $params")
        
        // Special handling for screen view events
        if (eventName.startsWith("screen_") || eventName.contains("screen_viewed")) {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
                param(FirebaseAnalytics.Param.SCREEN_NAME, eventName.removePrefix("screen_"))
                param(FirebaseAnalytics.Param.SCREEN_CLASS, eventName)
                params?.forEach { (key, value) ->
                    when (value) {
                        is String -> param(key, value)
                        is Long -> param(key, value)
                        is Double -> param(key, value)
                        is Int -> param(key, value.toLong())
                        is Float -> param(key, value.toDouble())
                        is Boolean -> param(key, if (value) 1L else 0L)
                        else -> param(key, value.toString())
                    }
                }
            }
        } else {
            firebaseAnalytics.logEvent(eventName) {
                params?.forEach { (key, value) ->
                    when (value) {
                        is String -> param(key, value)
                        is Long -> param(key, value)
                        is Double -> param(key, value)
                        is Int -> param(key, value.toLong())
                        is Float -> param(key, value.toDouble())
                        is Boolean -> param(key, if (value) 1L else 0L)
                        else -> param(key, value.toString())
                    }
                }
            }
        }
    }
}