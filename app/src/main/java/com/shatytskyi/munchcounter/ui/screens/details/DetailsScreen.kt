package com.shatytskyi.munchcounter.ui.screens.details

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
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.ui.dialogs.DiceDialog
import com.shatytskyi.munchcounter.ui.dialogs.EditCharacterDialog
import com.shatytskyi.munchcounter.ui.dialogs.WarningDialog
import com.shatytskyi.munchcounter.viewmodel.CommonViewModel
import com.shatytskyi.munchcounter.analytics.AnalyticsManager
import com.shatytskyi.munchcounter.analytics.AnalyticsEvents
import com.shatytskyi.munchcounter.analytics.ScreenNames
import com.shatytskyi.munchcounter.analytics.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.compose.koinInject

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
    val analyticsManager = koinInject<AnalyticsManager>()

    var showEditDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showDiceDialog by remember { mutableStateOf(false) }

    LaunchedEffect(character) {
        viewModel.loadCharacters()
        // Log screen view
        character?.let {
            analyticsManager.logScreenView(ScreenNames.PLAYER_DETAILS, "DetailsScreen")
        }
    }

    if (character == null) {
        onBack()
        return
    }

    DetailsScreenContent(
        character = character,
        onLevelChange = { delta -> 
            viewModel.changeLevel(characterId, delta)
            analyticsManager.logEvent(
                AnalyticsEvents.LEVEL_CHANGED,
                bundleOf(
                    "source" to "details_screen",
                    "direction" to if (delta > 0) "up" else "down",
                    "button_value" to delta,
                    "new_level" to (character.level + delta),
                    FirebaseAnalytics.Param.VALUE to delta.toLong()
                )
            )
        },
        onPowerChange = { delta -> 
            viewModel.changePower(characterId, delta)
            analyticsManager.logEvent(
                AnalyticsEvents.ITEMS_CHANGED,
                bundleOf(
                    "source" to "details_screen",
                    "direction" to if (delta > 0) "increase" else "decrease",
                    "button_value" to delta,
                    "new_items" to (character.power + delta),
                    FirebaseAnalytics.Param.VALUE to delta.toLong()
                )
            )
        },
        onGenderToggle = { id -> 
            viewModel.toggleGender(id)
            analyticsManager.logEvent(AnalyticsEvents.GENDER_TOGGLED, 
                bundleOf("source" to "details_screen"))
        },
        onFightClick = {
            analyticsManager.logEvent(AnalyticsEvents.FIGHT_STARTED,
                bundleOf("source" to "details_screen"))
            onFight()
        },
        onResetClick = { showResetDialog = true },
        onEditClick = { showEditDialog = true },
        onDeleteClick = { showDeleteDialog = true },
        onBackClick = onBack,
        onTimerClick = {
            analyticsManager.logEvent(AnalyticsEvents.TIMER_USED,
                bundleOf("source" to "details_screen"))
            onTimerClick()
        },
        onDiceClick = { 
            analyticsManager.logEvent(AnalyticsEvents.DICE_ROLLED,
                bundleOf("source" to "details_screen"))
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
                analyticsManager.logEvent(
                    "player_reset",
                    bundleOf(
                        "source" to "details_screen",
                        "player_level" to character.level,
                        "player_items" to character.items
                    )
                )
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
                analyticsManager.logEvent(
                    AnalyticsEvents.PLAYER_DELETED,
                    bundleOf(
                        "source" to "details_screen",
                        "player_level" to character.level,
                        "player_items" to character.items
                    )
                )
                viewModel.removeCharacter(characterId)
                onBack()
            }
        )
    }

    if (showDiceDialog) {
        DiceDialog(
            onDismiss = { showDiceDialog = false }
        )
    }
}
