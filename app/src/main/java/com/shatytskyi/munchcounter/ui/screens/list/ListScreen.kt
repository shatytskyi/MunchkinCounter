package com.shatytskyi.munchcounter.ui.screens.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.data.Character
import com.shatytskyi.munchcounter.data.Gender
import com.shatytskyi.munchcounter.ui.dialogs.AddCharacterDialog
import com.shatytskyi.munchcounter.ui.dialogs.DiceDialog
import com.shatytskyi.munchcounter.ui.dialogs.WarningDialog
import com.shatytskyi.munchcounter.viewmodel.CommonViewModel

private enum class ScreenState {
    Loading,
    Empty,
    Content
}

@Composable
fun ListScreen(
    viewModel: CommonViewModel,
    onCharacterClick: (Long) -> Unit,
    onSettingsClick: () -> Unit = {},
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
        onSettingsClick = onSettingsClick,
        modifier = modifier
    )
}

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
    onSettingsClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    var showAddDialog by remember { mutableStateOf(false) }
    var showResetAllDialog by remember { mutableStateOf(false) }
    var showRemoveAllDialog by remember { mutableStateOf(false) }
    var showDiceDialog by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        val currentState = when {
            isLoading -> ScreenState.Loading
            characters.isEmpty() -> ScreenState.Empty
            else -> ScreenState.Content
        }

        AnimatedContent(
            targetState = currentState,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) togetherWith
                        fadeOut(animationSpec = tween(300))
            },
            label = "screen_state_animation"
        ) { state ->
            when (state) {
                ScreenState.Loading -> {
                    ListScreenLoadingContent(
                        onDiceClick = { 
                            haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                            showDiceDialog = true 
                        }
                    )
                }

                ScreenState.Empty -> {
                    ListScreenEmptyContent(
                        onAddCharacterClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                            showAddDialog = true
                        },
                        onDiceClick = { 
                            haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                            showDiceDialog = true 
                        },
                        onSettingsClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                            onSettingsClick()
                        }
                    )
                }

                ScreenState.Content -> {
                    ListScreenContent(
                        characters = characters,
                        onCharacterClick = { id ->
                            haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                            onCharacterClick(id)
                        },
                        onAddCharacterClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
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
                            onGenderToggle(id)
                        },
                        onResetAllClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            showResetAllDialog = true
                        },
                        onRemoveAllClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            showRemoveAllDialog = true
                        },
                        onDiceClick = { 
                            haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                            showDiceDialog = true 
                        },
                        onSettingsClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                            onSettingsClick()
                        }
                    )
                }
            }
        }
    }

    // Dialogs
    if (showAddDialog) {
        AddCharacterDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name, gender ->
                haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                onAddCharacter(name, gender)
                showAddDialog = false
            }
        )
    }

    if (showResetAllDialog) {
        WarningDialog(
            title = stringResource(R.string.warning_reset_all_title),
            message = stringResource(R.string.warning_reset_all_message),
            onDismiss = { showResetAllDialog = false },
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
            onDismiss = { showRemoveAllDialog = false },
            onConfirm = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onRemoveAll()
                showRemoveAllDialog = false
            }
        )
    }

    if (showDiceDialog) {
        DiceDialog(
            onDismiss = { showDiceDialog = false }
        )
    }
}
