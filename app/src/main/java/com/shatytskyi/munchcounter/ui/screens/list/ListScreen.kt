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
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.data.Character
import com.shatytskyi.munchcounter.data.Gender
import com.shatytskyi.munchcounter.ui.dialogs.AddCharacterDialog
import com.shatytskyi.munchcounter.ui.dialogs.DiceDialog
import com.shatytskyi.munchcounter.ui.dialogs.WarningDialog
import com.shatytskyi.munchcounter.analytics.AnalyticsManager
import com.shatytskyi.munchcounter.analytics.Events
import com.shatytskyi.munchcounter.analytics.EventParams
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
    
    // Log screen view
    LaunchedEffect(Unit) {
        analyticsManager.trackEvent(Events.HomeScreen.VIEWED)
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (isLoading) {
            ListScreenLoadingContent(
                onDiceClick = { 
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    analyticsManager.trackEvent(Events.HomeScreen.DICE_BUTTON_CLICKED)
                    showDiceDialog = true 
                },
                onTimerClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    analyticsManager.trackEvent(Events.HomeScreen.TIMER_BUTTON_CLICKED)
                    onTimerClick()
                },
                onSettingsClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    analyticsManager.trackEvent(Events.HomeScreen.SETTINGS_CLICKED)
                    onSettingsClick()
                }
            )
        } else {
            ListScreenUnified(
                characters = characters,
                onCharacterClick = { id ->
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    val character = characters.find { it.id == id }
                    analyticsManager.trackEvent(
                        Events.HomeScreen.PLAYER_CARD_CLICKED,
                        mapOf(EventParams.PLAYER_NAME to (character?.name ?: "Unknown"))
                    )
                    onCharacterClick(id)
                },
                onAddCharacterClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    analyticsManager.trackEvent(Events.HomeScreen.ADD_PLAYER_CLICKED)
                    showAddDialog = true
                },
                onLevelChange = { id, delta ->
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    val character = characters.find { it.id == id }
                    val eventName = if (delta > 0) Events.HomeScreen.PLAYER_LEVEL_UP else Events.HomeScreen.PLAYER_LEVEL_DOWN
                    analyticsManager.trackEvent(
                        eventName,
                        mapOf(
                            EventParams.PLAYER_NAME to (character?.name ?: "Unknown"),
                            EventParams.PLAYER_LEVEL to ((character?.level ?: 1) + delta)
                        )
                    )
                    onLevelChange(id, delta)
                },
                onPowerChange = { id, delta ->
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    val character = characters.find { it.id == id }
                    val eventName = if (delta > 0) Events.HomeScreen.PLAYER_ITEMS_INCREASED else Events.HomeScreen.PLAYER_ITEMS_DECREASED
                    analyticsManager.trackEvent(
                        eventName,
                        mapOf(
                            EventParams.PLAYER_NAME to (character?.name ?: "Unknown"),
                            EventParams.PLAYER_ITEMS to ((character?.power ?: 0) + delta)
                        )
                    )
                    onPowerChange(id, delta)
                },
                onGenderToggle = { id ->
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    val character = characters.find { it.id == id }
                    analyticsManager.trackEvent(
                        Events.HomeScreen.PLAYER_GENDER_TOGGLED,
                        mapOf(
                            EventParams.PLAYER_NAME to (character?.name ?: "Unknown"),
                            EventParams.PLAYER_GENDER to (character?.gender?.name ?: "Unknown")
                        )
                    )
                    onGenderToggle(id)
                },
                onResetAllClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    analyticsManager.trackEvent(Events.HomeScreen.RESET_ALL_CLICKED)
                    showResetAllDialog = true
                },
                onRemoveAllClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    analyticsManager.trackEvent(Events.HomeScreen.DELETE_ALL_CLICKED)
                    showRemoveAllDialog = true
                },
                onDiceClick = { 
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    analyticsManager.trackEvent(Events.HomeScreen.DICE_BUTTON_CLICKED)
                    showDiceDialog = true 
                },
                onTimerClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    analyticsManager.trackEvent(Events.HomeScreen.TIMER_BUTTON_CLICKED)
                    onTimerClick()
                },
                onSettingsClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    analyticsManager.trackEvent(Events.HomeScreen.SETTINGS_CLICKED)
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
                analyticsManager.trackEvent(Events.AddPlayerScreen.CANCEL_CLICKED)
                showAddDialog = false 
            },
            onConfirm = { name, gender ->
                haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                analyticsManager.trackEvent(
                    Events.AddPlayerScreen.ADD_CLICKED,
                    mapOf(
                        EventParams.PLAYER_NAME to name,
                        EventParams.PLAYER_GENDER to gender.name
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
                analyticsManager.trackEvent(Events.HomeScreen.RESET_ALL_CANCELLED)
                showResetAllDialog = false 
            },
            onConfirm = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                analyticsManager.trackEvent(
                    Events.HomeScreen.RESET_ALL_CONFIRMED,
                    mapOf(EventParams.PLAYER_COUNT to characters.size)
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
                analyticsManager.trackEvent(Events.HomeScreen.DELETE_ALL_CANCELLED)
                showRemoveAllDialog = false 
            },
            onConfirm = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                analyticsManager.trackEvent(
                    Events.HomeScreen.DELETE_ALL_CONFIRMED,
                    mapOf(EventParams.PLAYER_COUNT to characters.size)
                )
                onRemoveAll()
                showRemoveAllDialog = false
            }
        )
    }

    if (showDiceDialog) {
        DiceDialog(
            onDismiss = { 
                analyticsManager.trackEvent(Events.DiceScreen.BACK_CLICKED)
                showDiceDialog = false 
            }
        )
    }
}
