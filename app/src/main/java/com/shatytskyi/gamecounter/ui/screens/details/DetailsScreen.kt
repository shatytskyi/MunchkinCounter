package com.shatytskyi.gamecounter.ui.screens.details

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.shatytskyi.gamecounter.R
import com.shatytskyi.gamecounter.ui.dialogs.DiceDialog
import com.shatytskyi.gamecounter.ui.dialogs.EditCharacterDialog
import com.shatytskyi.gamecounter.ui.dialogs.WarningDialog
import com.shatytskyi.gamecounter.viewmodel.CommonViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailsScreen(
    viewModel: CommonViewModel,
    characterId: Long,
    onBack: () -> Unit,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
    modifier: Modifier = Modifier,
    onFight: () -> Unit = {},
    onTimerClick: () -> Unit = {}
) {
    val characters by viewModel.characters.collectAsState()
    val character = characters.find { it.id == characterId }

    var showEditDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showDiceDialog by remember { mutableStateOf(false) }

    LaunchedEffect(character) {
        viewModel.loadCharacters()
    }

    if (character == null) {
        onBack()
        return
    }

    DetailsScreenContent(
        character = character,
        onLevelChange = { delta ->
            viewModel.changeLevel(characterId, delta)
        },
        onPowerChange = { delta ->
            viewModel.changePower(characterId, delta)
        },
        onGenderToggle = { id ->
            viewModel.toggleGender(id)
        },
        onFightClick = {
            onFight()
        },
        onResetClick = { showResetDialog = true },
        onEditClick = { showEditDialog = true },
        onDeleteClick = { showDeleteDialog = true },
        onBackClick = onBack,
        onTimerClick = {
            onTimerClick()
        },
        onDiceClick = {
            showDiceDialog = true
        },
        animatedContentScope = animatedContentScope,
        sharedTransitionScope = sharedTransitionScope,
        modifier = modifier
    )

    // Dialogs
    if (showEditDialog) {
        EditCharacterDialog(
            character = character,
            onDismiss = { showEditDialog = false },
            onConfirm = { name, level, power, gender ->
                viewModel.updateCharacter(characterId, name, level, power, gender)
                showEditDialog = false
            }
        )
    }

    if (showResetDialog) {
        WarningDialog(
            title = stringResource(R.string.reset_character_title, character.name),
            message = stringResource(R.string.player_will_be_reset),
            onDismiss = { showResetDialog = false },
            onConfirm = {
                viewModel.resetCharacter(characterId)
                showResetDialog = false
            }
        )
    }

    if (showDeleteDialog) {
        WarningDialog(
            title = stringResource(R.string.delete_character_title, character.name),
            message = stringResource(R.string.player_will_be_deleted),
            onDismiss = { showDeleteDialog = false },
            onConfirm = {
                viewModel.removeCharacter(characterId)
                onBack()
            }
        )
    }

    if (showDiceDialog) {
        DiceDialog(
            onDismiss = { showDiceDialog = false },
        )
    }
}
