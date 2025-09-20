package com.shatytskyi.gamecounter.ui.screens

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
import com.shatytskyi.gamecounter.R
import com.shatytskyi.gamecounter.data.Character
import com.shatytskyi.gamecounter.data.Gender
import com.shatytskyi.gamecounter.ui.components.CharacterListItem
import com.shatytskyi.gamecounter.ui.components.MunchkinIcon
import com.shatytskyi.gamecounter.ui.components.MunchkinIconButton
import com.shatytskyi.gamecounter.ui.components.MunchkinText
import com.shatytskyi.gamecounter.ui.components.MunchkinTopAppBar
import com.shatytskyi.gamecounter.ui.components.icons.MunchkinIcons
import com.shatytskyi.gamecounter.ui.components.icons.Timer
import com.shatytskyi.gamecounter.ui.components.icons.dice.Dice5
import com.shatytskyi.gamecounter.ui.dialogs.DiceDialog
import com.shatytskyi.gamecounter.ui.screens.details.LevelControlCard
import com.shatytskyi.gamecounter.ui.screens.fight.FightInterface
import com.shatytskyi.gamecounter.ui.theme.MunchkinTheme
import com.shatytskyi.gamecounter.viewmodel.CommonViewModel

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

    var showDiceDialog by remember { mutableStateOf(false) }

    LaunchedEffect(player) {
        viewModel.loadCharacters()
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
                style = MunchkinTheme.typography.bodyMedium,
                color = MunchkinTheme.colors.onBackground
            )
        }
        return
    }

    FightScreenContent(
        character = player,
        characters = characters,
        onBackClick = {
            onBack()
        },
        onTimerClick = {
            onTimerClick()
        },
        onDiceClick = {
            showDiceDialog = true
        },
        onLevelChange = { delta ->
            viewModel.updateCharacter(
                id = playerId,
                name = player.name,
                level = player.level + delta,
                power = player.items,
                gender = player.gender
            )
        },
        onPowerChange = { delta ->
            viewModel.updateCharacter(
                id = playerId,
                name = player.name,
                level = player.level,
                power = player.items + delta,
                gender = player.gender
            )
        },
        onGenderToggle = {
            viewModel.toggleGender(playerId)
        },
        animatedContentScope = animatedContentScope,
        sharedTransitionScope = sharedTransitionScope,
        modifier = modifier
    )

    if (showDiceDialog) {
        DiceDialog(
            onDismiss = { showDiceDialog = false },
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun FightScreenContent(
    character: Character,
    characters: List<Character>,
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
            characters = characters,
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
            characters = listOf(mockCharacter),
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
