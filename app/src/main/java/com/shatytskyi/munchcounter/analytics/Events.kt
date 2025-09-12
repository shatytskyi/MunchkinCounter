package com.shatytskyi.munchcounter.analytics

/**
 * Analytics events organized by screens
 */
object Events {
    
    /**
     * Home/List Screen - Main screen with players list
     */
    object HomeScreen {
        const val VIEWED = "home_screen_viewed"
        
        // Player card interactions
        const val PLAYER_CARD_CLICKED = "home_player_card_clicked"
        const val PLAYER_LEVEL_UP = "home_player_level_up"
        const val PLAYER_LEVEL_DOWN = "home_player_level_down"
        const val PLAYER_ITEMS_INCREASED = "home_player_items_increased"
        const val PLAYER_ITEMS_DECREASED = "home_player_items_decreased"
        const val PLAYER_GENDER_TOGGLED = "home_player_gender_toggled"
        const val PLAYER_EDIT_CLICKED = "home_player_edit_clicked"
        const val PLAYER_DELETE_CLICKED = "home_player_delete_clicked"
        const val PLAYER_RESET_CLICKED = "home_player_reset_clicked"
        
        // Bottom bar actions
        const val ADD_PLAYER_CLICKED = "home_add_player_clicked"
        const val FIGHT_BUTTON_CLICKED = "home_fight_button_clicked"
        const val TIMER_BUTTON_CLICKED = "home_timer_button_clicked"
        const val DICE_BUTTON_CLICKED = "home_dice_button_clicked"
        
        // Top bar actions
        const val SETTINGS_CLICKED = "home_settings_clicked"
        const val MENU_CLICKED = "home_menu_clicked"
        const val RESET_ALL_CLICKED = "home_reset_all_clicked"
        const val DELETE_ALL_CLICKED = "home_delete_all_clicked"
        
        // Dialog confirmations
        const val PLAYER_DELETE_CONFIRMED = "home_player_delete_confirmed"
        const val PLAYER_DELETE_CANCELLED = "home_player_delete_cancelled"
        const val PLAYER_RESET_CONFIRMED = "home_player_reset_confirmed"
        const val PLAYER_RESET_CANCELLED = "home_player_reset_cancelled"
        const val RESET_ALL_CONFIRMED = "home_reset_all_confirmed"
        const val RESET_ALL_CANCELLED = "home_reset_all_cancelled"
        const val DELETE_ALL_CONFIRMED = "home_delete_all_confirmed"
        const val DELETE_ALL_CANCELLED = "home_delete_all_cancelled"
    }
    
    /**
     * Timer Screen
     */
    object TimerScreen {
        const val VIEWED = "timer_screen_viewed"
        const val BACK_CLICKED = "timer_back_clicked"
        const val SECONDS_SELECTED = "timer_seconds_selected"
        const val START_CLICKED = "timer_start_clicked"
        const val STOP_CLICKED = "timer_stop_clicked"
        const val COMPLETED = "timer_completed"
    }
    
    /**
     * Fight Screen
     */
    object FightScreen {
        const val VIEWED = "fight_screen_viewed"
        const val BACK_CLICKED = "fight_back_clicked"
        
        // Player selection
        const val PLAYER_SELECTED = "fight_player_selected"
        const val HELPER_SELECTED = "fight_helper_selected"
        const val HELPER_REMOVED = "fight_helper_removed"
        
        // Power controls
        const val PLAYER_POWER_INCREASED = "fight_player_power_increased"
        const val PLAYER_POWER_DECREASED = "fight_player_power_decreased"
        const val MONSTER_POWER_INCREASED = "fight_monster_power_increased"
        const val MONSTER_POWER_DECREASED = "fight_monster_power_decreased"
        
        // Fight outcomes
        const val WIN_CLICKED = "fight_win_clicked"
        const val ESCAPE_CLICKED = "fight_escape_clicked"
        const val ESCAPE_DICE_ROLLED = "fight_escape_dice_rolled"
        const val ESCAPE_SUCCESS = "fight_escape_success"
        const val ESCAPE_FAILED = "fight_escape_failed"
        const val ESCAPE_RETRY = "fight_escape_retry"
        const val ESCAPE_STAY = "fight_escape_stay"
    }
    
    /**
     * Edit Player Screen
     */
    object EditPlayerScreen {
        const val VIEWED = "edit_player_screen_viewed"
        const val BACK_CLICKED = "edit_player_back_clicked"
        const val NAME_CHANGED = "edit_player_name_changed"
        const val LEVEL_CHANGED = "edit_player_level_changed"
        const val ITEMS_CHANGED = "edit_player_items_changed"
        const val GENDER_CHANGED = "edit_player_gender_changed"
        const val SAVE_CLICKED = "edit_player_save_clicked"
        const val CANCEL_CLICKED = "edit_player_cancel_clicked"
    }
    
    /**
     * Add Player Screen
     */
    object AddPlayerScreen {
        const val VIEWED = "add_player_screen_viewed"
        const val BACK_CLICKED = "add_player_back_clicked"
        const val NAME_ENTERED = "add_player_name_entered"
        const val GENDER_SELECTED = "add_player_gender_selected"
        const val ADD_CLICKED = "add_player_add_clicked"
        const val CANCEL_CLICKED = "add_player_cancel_clicked"
    }
    
    /**
     * Settings Screen
     */
    object SettingsScreen {
        const val VIEWED = "settings_screen_viewed"
        const val BACK_CLICKED = "settings_back_clicked"
        const val THEME_CHANGED = "settings_theme_changed"
        const val DYNAMIC_COLORS_TOGGLED = "settings_dynamic_colors_toggled"
        const val SYSTEM_FONT_TOGGLED = "settings_system_font_toggled"
        const val LANGUAGE_CLICKED = "settings_language_clicked"
        const val LANGUAGE_SELECTED = "settings_language_selected"
    }
    
    /**
     * Dice Screen (if exists)
     */
    object DiceScreen {
        const val VIEWED = "dice_screen_viewed"
        const val BACK_CLICKED = "dice_back_clicked"
        const val DICE_ROLLED = "dice_rolled"
        const val DICE_COUNT_CHANGED = "dice_count_changed"
    }
}

/**
 * Common parameter keys for analytics events
 */
object EventParams {
    // Common
    const val SCREEN_NAME = "screen_name"
    const val BUTTON_NAME = "button_name"
    const val VALUE = "value"
    const val COUNT = "count"
    const val SUCCESS = "success"
    const val ERROR_MESSAGE = "error_message"
    const val DURATION = "duration"
    
    // Player
    const val PLAYER_NAME = "player_name"
    const val PLAYER_ID = "player_id"
    const val PLAYER_LEVEL = "player_level"
    const val PLAYER_ITEMS = "player_items"
    const val PLAYER_POWER = "player_power"
    const val PLAYER_GENDER = "player_gender"
    const val PLAYER_COUNT = "player_count"
    
    // Timer
    const val SECONDS = "seconds"
    const val SECONDS_REMAINING = "seconds_remaining"
    
    // Fight
    const val MONSTER_POWER = "monster_power"
    const val TOTAL_PLAYER_POWER = "total_player_power"
    const val HELPER_NAME = "helper_name"
    const val HELPER_POWER = "helper_power"
    const val POWER_DIFFERENCE = "power_difference"
    const val IS_WINNING = "is_winning"
    
    // Dice
    const val DICE_RESULT = "dice_result"
    const val DICE_COUNT = "dice_count"
    const val ESCAPE_SUCCESS = "escape_success"
    
    // Settings
    const val THEME_MODE = "theme_mode"
    const val DYNAMIC_COLORS = "dynamic_colors"
    const val SYSTEM_FONT = "system_font"
    const val LANGUAGE = "language"
    const val SETTING_NAME = "setting_name"
    const val OLD_VALUE = "old_value"
    const val NEW_VALUE = "new_value"
    
    // Dialog
    const val DIALOG_TYPE = "dialog_type"
    const val DIALOG_ACTION = "dialog_action"
}