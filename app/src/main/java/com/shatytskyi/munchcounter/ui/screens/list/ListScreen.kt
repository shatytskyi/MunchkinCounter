package com.shatytskyi.munchcounter.ui.screens.list

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import com.google.firebase.analytics.FirebaseAnalytics
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.analytics.AnalyticsEvents
import com.shatytskyi.munchcounter.analytics.AnalyticsManager
import com.shatytskyi.munchcounter.analytics.ScreenNames
import com.shatytskyi.munchcounter.analytics.UserProperties
import com.shatytskyi.munchcounter.analytics.bundleOf
import com.shatytskyi.munchcounter.data.Character
import com.shatytskyi.munchcounter.data.Gender
import com.shatytskyi.munchcounter.ui.dialogs.AddCharacterDialog
import com.shatytskyi.munchcounter.ui.dialogs.DiceDialog
import com.shatytskyi.munchcounter.ui.dialogs.WarningDialog
import com.shatytskyi.munchcounter.viewmodel.CommonViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ListScreen(
    viewModel: CommonViewModel,
    onCharacterClick: (Long) -> Unit,
    onTimerClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
    modifier: Modifier = Modifier
) {
    val characters by viewModel.characters.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    ListScreenContent(
        characters = characters,
        isLoading = isLoading,
        onCharacterClick = onCharacterClick,
        onAddCharacter = { name, gender ->
            viewModel.addCharacter(name, gender)
        },
        onLevelChange = { characterId, delta ->
            viewModel.changeLevel(characterId, delta)
        },
        onPowerChange = { characterId, delta ->
            viewModel.changePower(characterId, delta)
        },
        onGenderToggle = { characterId ->
            viewModel.toggleGender(characterId)
        },
        onResetAll = {
            viewModel.resetAllCharacters()
        },
        onRemoveAll = {
            viewModel.removeAllCharacters()
        },
        onTimerClick = onTimerClick,
        onSettingsClick = onSettingsClick,
        animatedContentScope = animatedContentScope,
        sharedTransitionScope = sharedTransitionScope,
        modifier = modifier
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ListScreenContent(
    characters: List<Character>,
    isLoading: Boolean,
    onCharacterClick: (Long) -> Unit,
    onAddCharacter: (String, Gender) -> Unit,
    onLevelChange: (Long, Int) -> Unit,
    onPowerChange: (Long, Int) -> Unit,
    onGenderToggle: (Long) -> Unit,
    onResetAll: () -> Unit,
    onRemoveAll: () -> Unit,
    onTimerClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    val analyticsManager = koinInject<AnalyticsManager>()
    var showAddDialog by remember { mutableStateOf(false) }
    var showResetAllDialog by remember { mutableStateOf(false) }
    var showRemoveAllDialog by remember { mutableStateOf(false) }
    var showDiceDialog by remember { mutableStateOf(false) }

    // Log screen view once
    LaunchedEffect(Unit) {
        analyticsManager.logScreenView(ScreenNames.HOME, "ListScreen")
    }

    // Update user properties when character count changes
    LaunchedEffect(characters.size) {
        // Set user property
        analyticsManager.setUserProperty(
            UserProperties.ACTIVE_PLAYER_COUNT,
            when (characters.size) {
                0 -> "0"
                1 -> "1"
                in 2..4 -> "2-4"
                in 5..8 -> "5-8"
                else -> "9+"
            }
        )
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (isLoading) {
            ListScreenLoadingContent(
                onDiceClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    showDiceDialog = true
                },
                onTimerClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    analyticsManager.logEvent(AnalyticsEvents.TIMER_USED, null)
                    onTimerClick()
                },
                onSettingsClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    // Navigation events are less critical, skip tracking
                    onSettingsClick()
                }
            )
        } else {
            ListScreenUnified(
                characters = characters,
                onCharacterClick = { id ->
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    // Track player detail views using Firebase's standard event
                    characters.find { it.id == id }
                    analyticsManager.logEvent(
                        FirebaseAnalytics.Event.SELECT_CONTENT,
                        bundleOf(
                            FirebaseAnalytics.Param.CONTENT_TYPE to "player",
                            FirebaseAnalytics.Param.ITEM_ID to id.toString()
                        )
                    )
                    onCharacterClick(id)
                },
                onAddCharacterClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    // Track when dialog opens (actual add is tracked on confirm)
                    showAddDialog = true
                },
                onLevelChange = { id, delta ->
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    val character = characters.find { it.id == id }
                    // Track level changes with context
                    analyticsManager.logEvent(
                        AnalyticsEvents.LEVEL_CHANGED,
                        bundleOf(
                            "source" to "list_screen",
                            "direction" to if (delta > 0) "up" else "down",
                            "button_value" to delta,
                            "new_level" to ((character?.level ?: 1) + delta),
                            FirebaseAnalytics.Param.VALUE to delta.toLong()
                        )
                    )
                    onLevelChange(id, delta)
                },
                onPowerChange = { id, delta ->
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    val character = characters.find { it.id == id }
                    // Track item/power changes
                    analyticsManager.logEvent(
                        AnalyticsEvents.ITEMS_CHANGED,
                        bundleOf(
                            "source" to "list_screen",
                            "direction" to if (delta > 0) "increase" else "decrease",
                            "button_value" to delta,
                            "new_items" to ((character?.power ?: 0) + delta),
                            FirebaseAnalytics.Param.VALUE to delta.toLong()
                        )
                    )
                    onPowerChange(id, delta)
                },
                onGenderToggle = { id ->
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    // Track gender toggle with source
                    analyticsManager.logEvent(
                        AnalyticsEvents.GENDER_TOGGLED,
                        bundleOf("source" to "list_screen")
                    )
                    onGenderToggle(id)
                },
                onResetAllClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    // Track on confirm, not on click
                    showResetAllDialog = true
                },
                onRemoveAllClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    // Track on confirm, not on click
                    showRemoveAllDialog = true
                },
                onDiceClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    showDiceDialog = true
                },
                onTimerClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    analyticsManager.logEvent(AnalyticsEvents.TIMER_USED, null)
                    onTimerClick()
                },
                onSettingsClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    // Navigation events are less critical, skip tracking
                    onSettingsClick()
                },
                animatedContentScope = animatedContentScope,
                sharedTransitionScope = sharedTransitionScope
            )
        }
    }

    // Dialogs
    if (showAddDialog) {
        AddCharacterDialog(
            onDismiss = {
                showAddDialog = false
            },
            onConfirm = { name, gender ->
                haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                // Track player addition - key metric
                analyticsManager.logEvent(
                    AnalyticsEvents.PLAYER_ADDED,
                    bundleOf(
                        "gender" to gender.name,
                        "player_count" to (characters.size + 1)
                    )
                )
                onAddCharacter(name, gender)
                showAddDialog = false
            }
        )
    }

    if (showResetAllDialog) {
        WarningDialog(
            title = stringResource(R.string.warning_reset_all_title),
            message = stringResource(R.string.warning_reset_all_message),
            onDismiss = {
                showResetAllDialog = false
            },
            onConfirm = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                // Track reset all - important user action
                analyticsManager.logEvent(
                    AnalyticsEvents.ALL_PLAYERS_RESET,
                    bundleOf(
                        "player_count" to characters.size
                    )
                )
                onResetAll()
                showResetAllDialog = false
            }
        )
    }

    if (showRemoveAllDialog) {
        WarningDialog(
            title = stringResource(R.string.warning_delete_all_title),
            message = stringResource(R.string.warning_delete_all_message),
            onDismiss = {
                showRemoveAllDialog = false
            },
            onConfirm = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                // Track all players deletion
                analyticsManager.logEvent(
                    AnalyticsEvents.ALL_PLAYERS_DELETED,
                    bundleOf(
                        "player_count" to characters.size
                    )
                )
                onRemoveAll()
                showRemoveAllDialog = false
            }
        )
    }

    if (showDiceDialog) {
        DiceDialog(
            onDismiss = {
                showDiceDialog = false
            },
            source = "list_screen"
        )
    }
}
