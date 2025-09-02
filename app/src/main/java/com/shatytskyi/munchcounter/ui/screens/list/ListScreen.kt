package com.shatytskyi.munchcounter.ui.screens.list

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

@Composable
fun ListScreen(
    viewModel: CommonViewModel,
    onCharacterClick: (Long) -> Unit,
    onTimerClick: () -> Unit = {},
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
        onTimerClick = onTimerClick,
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
    onTimerClick: () -> Unit = {},
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
                    onSettingsClick()
                }
            )
        } else {
            ListScreenUnified(
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
                onTimerClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    onTimerClick()
                },
                onSettingsClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    onSettingsClick()
                }
            )
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
