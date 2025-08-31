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
import com.shatytskyi.munchcounter.data.Character
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
    modifier: Modifier = Modifier
) {
    val characters by viewModel.characters.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    ListScreenContent(
        characters = characters,
        isLoading = isLoading,
        onCharacterClick = onCharacterClick,
        onAddCharacter = { name ->
            viewModel.addCharacter(name)
        },
        onLevelChange = { characterId, delta ->
            viewModel.changeLevel(characterId, delta)
        },
        onPowerChange = { characterId, delta ->
            viewModel.changePower(characterId, delta)
        },
        onResetAll = {
            viewModel.resetAllCharacters()
        },
        onRemoveAll = {
            viewModel.removeAllCharacters()
        },
        modifier = modifier
    )
}

@Composable
private fun ListScreenContent(
    characters: List<Character>,
    isLoading: Boolean,
    onCharacterClick: (Long) -> Unit,
    onAddCharacter: (String) -> Unit,
    onLevelChange: (Long, Int) -> Unit,
    onPowerChange: (Long, Int) -> Unit,
    onResetAll: () -> Unit,
    onRemoveAll: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                        onDiceClick = { showDiceDialog = true }
                    )
                }

                ScreenState.Empty -> {
                    ListScreenEmptyContent(
                        onAddCharacterClick = {
                            showAddDialog = true
                        },
                        onDiceClick = { showDiceDialog = true }
                    )
                }

                ScreenState.Content -> {
                    ListScreenContent(
                        characters = characters,
                        onCharacterClick = onCharacterClick,
                        onAddCharacterClick = {
                            showAddDialog = true
                        },
                        onLevelChange = onLevelChange,
                        onPowerChange = onPowerChange,
                        onResetAllClick = {
                            showResetAllDialog = true
                        },
                        onRemoveAllClick = {
                            showRemoveAllDialog = true
                        },
                        onDiceClick = { showDiceDialog = true }
                    )
                }
            }
        }
    }

    // Dialogs
    if (showAddDialog) {
        AddCharacterDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name ->
                onAddCharacter(name)
                showAddDialog = false
            }
        )
    }

    if (showResetAllDialog) {
        WarningDialog(
            title = "Reset All Players?",
            message = "All players will be reset to level 1 with 0 power",
            onDismiss = { showResetAllDialog = false },
            onConfirm = {
                onResetAll()
                showResetAllDialog = false
            }
        )
    }

    if (showRemoveAllDialog) {
        WarningDialog(
            title = "Delete All Players?",
            message = "All players will be permanently deleted",
            onDismiss = { showRemoveAllDialog = false },
            onConfirm = {
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
