package com.shatytskyi.munchcounter.ui.screens

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.data.Character
import com.shatytskyi.munchcounter.data.Gender
import com.shatytskyi.munchcounter.ui.components.CharacterListItem
import com.shatytskyi.munchcounter.ui.components.MunchkinIcon
import com.shatytskyi.munchcounter.ui.components.MunchkinIconButton
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.components.MunchkinTopAppBar
import com.shatytskyi.munchcounter.ui.components.icons.MunchkinIcons
import com.shatytskyi.munchcounter.ui.components.icons.Timer
import com.shatytskyi.munchcounter.ui.components.icons.dice.Dice5
import com.shatytskyi.munchcounter.ui.dialogs.DiceDialog
import com.shatytskyi.munchcounter.ui.screens.details.LevelControlCard
import com.shatytskyi.munchcounter.ui.screens.fight.FightInterface
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme
import com.shatytskyi.munchcounter.viewmodel.CommonViewModel
import com.shatytskyi.munchcounter.analytics.AnalyticsManager
import com.shatytskyi.munchcounter.analytics.AnalyticsEvents
import com.shatytskyi.munchcounter.analytics.ScreenNames
import com.shatytskyi.munchcounter.analytics.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.compose.koinInject

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FightScreen(
    viewModel: CommonViewModel,
    playerId: Long,
    onBack: () -> Unit,
    onTimerClick: () -> Unit = {},
    animatedContentScope: AnimatedContentScope? = null,
    sharedTransitionScope: SharedTransitionScope? = null,
    modifier: Modifier = Modifier
) {
    val characters by viewModel.characters.collectAsState()
    val player = characters.find { it.id == playerId }
    val analyticsManager = koinInject<AnalyticsManager>()

    var showDiceDialog by remember { mutableStateOf(false) }

    LaunchedEffect(player) {
        viewModel.loadCharacters()
        // Log screen view
        player?.let {
            analyticsManager.logScreenView("Fight", "FightScreen")
            analyticsManager.logEvent(
                "fight_session_started",
                bundleOf(
                    "player_level" to it.level,
                    "player_items" to it.items,
                    "player_total_power" to (it.level + it.items)
                )
            )
        }
    }

    if (player == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars),
            contentAlignment = Alignment.Center
        ) {
            MunchkinText(
                text = stringResource(R.string.loading_fight),
                style = MunchkinTheme.typography.bodyLarge,
                color = MunchkinTheme.colors.onBackground
            )
        }
        return
    }

    FightScreenContent(
        character = player,
        onBackClick = {
            analyticsManager.logEvent(
                "fight_session_ended",
                bundleOf(
                    "player_level" to player.level,
                    "player_items" to player.items,
                    "player_total_power" to (player.level + player.items)
                )
            )
            onBack()
        },
        onTimerClick = {
            analyticsManager.logEvent(AnalyticsEvents.TIMER_USED,
                bundleOf("source" to "fight_screen"))
            onTimerClick()
        },
        onDiceClick = { 
            analyticsManager.logEvent(AnalyticsEvents.DICE_ROLLED,
                bundleOf("source" to "fight_screen"))
            showDiceDialog = true 
        },
        onLevelChange = { delta ->
            analyticsManager.logEvent(
                AnalyticsEvents.LEVEL_CHANGED,
                bundleOf(
                    "source" to "fight_screen",
                    "direction" to if (delta > 0) "up" else "down",
                    "button_value" to delta,
                    "new_level" to (player.level + delta),
                    "during_fight" to true,
                    FirebaseAnalytics.Param.VALUE to delta.toLong()
                )
            )
            viewModel.updateCharacter(
                id = playerId,
                name = player.name,
                level = player.level + delta,
                power = player.items,
                gender = player.gender
            )
        },
        onPowerChange = { delta ->
            analyticsManager.logEvent(
                AnalyticsEvents.ITEMS_CHANGED,
                bundleOf(
                    "source" to "fight_screen",
                    "direction" to if (delta > 0) "increase" else "decrease",
                    "button_value" to delta,
                    "new_items" to (player.items + delta),
                    "during_fight" to true,
                    FirebaseAnalytics.Param.VALUE to delta.toLong()
                )
            )
            viewModel.updateCharacter(
                id = playerId,
                name = player.name,
                level = player.level,
                power = player.items + delta,
                gender = player.gender
            )
        },
        onGenderToggle = { 
            analyticsManager.logEvent(AnalyticsEvents.GENDER_TOGGLED,
                bundleOf(
                    "source" to "fight_screen",
                    "during_fight" to true
                ))
            viewModel.toggleGender(playerId) 
        },
        animatedContentScope = animatedContentScope,
        sharedTransitionScope = sharedTransitionScope,
        modifier = modifier
    )

    if (showDiceDialog) {
        DiceDialog(
            onDismiss = { showDiceDialog = false }
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun FightScreenContent(
    character: Character,
    onBackClick: () -> Unit,
    onTimerClick: () -> Unit,
    onDiceClick: () -> Unit,
    onLevelChange: (Int) -> Unit,
    onPowerChange: (Int) -> Unit,
    onGenderToggle: () -> Unit,
    animatedContentScope: AnimatedContentScope?,
    sharedTransitionScope: SharedTransitionScope?,
    modifier: Modifier = Modifier
) {
    val haptic: HapticFeedback = LocalHapticFeedback.current

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Top app bar with Timer and Dice buttons (no Fight button)
        MunchkinTopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top)),
            title = stringResource(R.string.fight_title),
            onBack = onBackClick,
            animatedContentScope = animatedContentScope,
            sharedTransitionScope = sharedTransitionScope,
            titleSharedKey = null,
            actions = {
                FightAppBarActions(onTimerClick, onDiceClick)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Character card with shared transition
        Box(
            modifier = if (sharedTransitionScope != null && animatedContentScope != null) {
                with(sharedTransitionScope) {
                    Modifier.sharedBounds(
                        sharedContentState = rememberSharedContentState(key = "character-card-${character.id}"),
                        animatedVisibilityScope = animatedContentScope
                    )
                }
            } else {
                Modifier
            }.padding(horizontal = 16.dp)
        ) {
            CharacterListItem(
                character = character,
                hideName = false,
                showLevelButtons = false,
                showItemsButtons = false,
                onLevelChange = onLevelChange,
                onItemsChange = onPowerChange,
                onGenderToggle = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    onGenderToggle()
                },
                animatedContentScope = null,
                sharedTransitionScope = null
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Level control buttons with shared transition
        Box(
            modifier = if (sharedTransitionScope != null && animatedContentScope != null) {
                with(sharedTransitionScope) {
                    Modifier.sharedBounds(
                        sharedContentState = rememberSharedContentState(key = "level-controls-${character.id}"),
                        animatedVisibilityScope = animatedContentScope
                    )
                }
            } else {
                Modifier
            }.padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LevelControlCard(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                        onLevelChange(-1)
                    },
                    isNegative = true,
                    modifier = Modifier
                        .weight(1f)
                        .height(64.dp)
                )
                LevelControlCard(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                        onLevelChange(+1)
                    },
                    isNegative = false,
                    modifier = Modifier
                        .weight(1f)
                        .height(64.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Fight interface takes all remaining space
        FightInterface(
            character = character,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}

@Composable
private fun FightAppBarActions(
    onTimerClick: () -> Unit,
    onDiceClick: () -> Unit
) {
    val haptic: HapticFeedback = LocalHapticFeedback.current

    MunchkinIconButton(onClick = {
        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
        onTimerClick()
    }) {
        MunchkinIcon(
            imageVector = MunchkinIcons.Timer,
            tint = MunchkinTheme.colors.onBackground
        )
    }

    MunchkinIconButton(onClick = {
        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
        onDiceClick()
    }) {
        MunchkinIcon(
            imageVector = MunchkinIcons.Dice.Dice5,
            tint = MunchkinTheme.colors.onBackground
        )
    }
}

@Preview(
    name = "Fight Screen - Pixel 4",
    device = Devices.PIXEL_4,
    showSystemUi = true,
    showBackground = true
)
@Preview(
    name = "Fight Screen - Pixel 6 Pro",
    device = Devices.PIXEL_6_PRO,
    showSystemUi = true,
    showBackground = true
)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun FightScreenPreview() {
    val mockCharacter = Character(
        id = 1,
        name = "Aragorn the King",
        level = 8,
        items = 15,
        gender = Gender.MALE
    )

    MunchkinTheme {
        FightScreenContent(
            character = mockCharacter,
            onBackClick = { },
            onTimerClick = { },
            onDiceClick = { },
            onLevelChange = { },
            onPowerChange = { },
            onGenderToggle = { },
            animatedContentScope = null,
            sharedTransitionScope = null
        )
    }
}
