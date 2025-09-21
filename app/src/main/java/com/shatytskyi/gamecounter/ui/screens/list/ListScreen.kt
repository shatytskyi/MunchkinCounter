package com.shatytskyi.gamecounter.ui.screens.list

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import com.shatytskyi.gamecounter.R
import com.shatytskyi.gamecounter.data.Character
import com.shatytskyi.gamecounter.data.Gender
import com.shatytskyi.gamecounter.ui.components.RateAppDialog
import com.shatytskyi.gamecounter.ui.dialogs.AddCharacterDialog
import com.shatytskyi.gamecounter.ui.dialogs.DiceDialog
import com.shatytskyi.gamecounter.ui.dialogs.WarningDialog
import com.shatytskyi.gamecounter.viewmodel.CommonViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ListScreen(
    viewModel: CommonViewModel,
    modifier: Modifier = Modifier,
    onCharacterClick: (Long) -> Unit,
    onTimerClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
) {
    val characters by viewModel.characters.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val shouldShowRateDialog by viewModel.shouldShowRateDialog.collectAsState()

    ListScreenContent(
        characters = characters,
        isLoading = isLoading,
        shouldShowRateDialog = shouldShowRateDialog,
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
        onRateNowClicked = {
            viewModel.onRateNowClicked()
        },
        onRateLaterClicked = {
            viewModel.onRateLaterClicked()
        },
        onRateDismiss = {
            viewModel.dismissRateDialog()
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
    modifier: Modifier = Modifier,
    characters: List<Character>,
    isLoading: Boolean,
    shouldShowRateDialog: Boolean,
    onCharacterClick: (Long) -> Unit,
    onAddCharacter: (String, Gender) -> Unit,
    onLevelChange: (Long, Int) -> Unit,
    onPowerChange: (Long, Int) -> Unit,
    onGenderToggle: (Long) -> Unit,
    onResetAll: () -> Unit,
    onRemoveAll: () -> Unit,
    onRateNowClicked: () -> Unit,
    onRateLaterClicked: () -> Unit,
    onRateDismiss: () -> Unit,
    onTimerClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
) {
    val haptic = LocalHapticFeedback.current
    var showAddDialog by remember { mutableStateOf(false) }
    var showResetAllDialog by remember { mutableStateOf(false) }
    var showRemoveAllDialog by remember { mutableStateOf(false) }
    var showDiceDialog by remember { mutableStateOf(false) }

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
                    onTimerClick()
                },
                onSettingsClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    // Navigation events are less critical, skip tracking
                    onSettingsClick()
                },
            )
        } else {
            ListScreenUnified(
                characters = characters,
                onCharacterClick = { id ->
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    // Track player detail views using Firebase's standard event
                    onCharacterClick(id)
                },
                onAddCharacterClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    // Track when dialog opens (actual add is tracked on confirm)
                    showAddDialog = true
                },
                onLevelChange = { id, delta ->
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    onLevelChange(id, delta)
                },
                onPowerChange = { id, delta ->
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    onPowerChange(id, delta)
                },
                onGenderToggle = { id ->
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    // Track gender toggle with source
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
            onConfirm = { name ->
                haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                onAddCharacter(name, Gender.MALE)
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
        )
    }

    if (shouldShowRateDialog) {
        RateAppDialog(
            onRateNow = onRateNowClicked,
            onRemindLater = onRateLaterClicked,
            onDismiss = onRateDismiss
        )
    }
}
