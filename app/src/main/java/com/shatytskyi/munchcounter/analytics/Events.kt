package com.shatytskyi.munchcounter.analytics

import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Analytics constants following Firebase best practices
 * Focus on meaningful user behaviors and key engagement metrics
 */
object AnalyticsEvents {
    
    /**
     * Custom events for tracking key user behaviors
     * Using snake_case as per Firebase conventions
     */
    
    // Player management - critical for understanding game usage
    const val PLAYER_ADDED = "player_added"
    const val PLAYER_ADD_CANCELLED = "add_player_cancelled"
    const val PLAYER_MODIFIED = "player_modified"
    const val PLAYER_DELETED = "player_deleted"
    const val ALL_PLAYERS_RESET = "all_players_reset"
    const val ALL_PLAYERS_DELETED = "all_players_deleted"
    
    // Core gameplay actions - track actual game engagement
    const val LEVEL_CHANGED = "level_changed"
    const val ITEMS_CHANGED = "items_changed"
    const val GENDER_TOGGLED = "gender_toggled"
    
    // Feature usage - understand which features provide value
    const val FIGHT_STARTED = "fight_started"
    const val TIMER_USED = "timer_used"
    const val DICE_ROLLED = "dice_rolled"
    
    // User engagement metrics
    const val SESSION_DURATION = "session_duration"
    const val PLAYERS_PER_SESSION = "players_per_session"
}

/**
 * Screen names for screen_view events
 */
object ScreenNames {
    const val HOME = "Home"
    const val FIGHT = "Fight"
    const val TIMER = "Timer"
    const val SETTINGS = "Settings"
    const val PLAYER_DETAILS = "Player Details"
}

/**
 * User properties for audience segmentation
 */
object UserProperties {
    const val ACTIVE_PLAYER_COUNT = "active_player_count"
    const val PREFERRED_FEATURES = "preferred_features"
    const val APP_THEME = "app_theme"
    const val TOTAL_SESSIONS = "total_sessions"
    const val TIMER_SECONDS_PREFERENCE = "timer_seconds_preference"
    const val DYNAMIC_COLORS_ENABLED = "dynamic_colors_enabled"
    const val SYSTEM_FONT_ENABLED = "system_font_enabled"
}