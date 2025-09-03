package com.shatytskyi.munchcounter.ui.screens

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.data.Character
import com.shatytskyi.munchcounter.data.Gender
import com.shatytskyi.munchcounter.ui.components.APP_BAR_HEIGHT
import com.shatytskyi.munchcounter.ui.components.AnimatedNumber
import com.shatytskyi.munchcounter.ui.components.CharacterListItem
import com.shatytskyi.munchcounter.ui.components.MunchkinCard
import com.shatytskyi.munchcounter.ui.components.MunchkinIcon
import com.shatytskyi.munchcounter.ui.components.MunchkinIconButton
import com.shatytskyi.munchcounter.ui.components.MunchkinIconTextButton
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.components.MunchkinTopAppBar
import com.shatytskyi.munchcounter.ui.components.icons.Edit
import com.shatytskyi.munchcounter.ui.components.icons.Fight
import com.shatytskyi.munchcounter.ui.components.icons.MunchkinIcons
import com.shatytskyi.munchcounter.ui.components.icons.Remove
import com.shatytskyi.munchcounter.ui.components.icons.Reset
import com.shatytskyi.munchcounter.ui.components.icons.Timer
import com.shatytskyi.munchcounter.ui.components.icons.dice.Dice5
import com.shatytskyi.munchcounter.ui.dialogs.DiceDialog
import com.shatytskyi.munchcounter.ui.dialogs.EditCharacterDialog
import com.shatytskyi.munchcounter.ui.dialogs.WarningDialog
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme
import com.shatytskyi.munchcounter.viewmodel.CommonViewModel

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

    LaunchedEffect(Unit) {
        viewModel.loadCharacters()
    }

    if (character == null) {
        return
    }

    val density = LocalDensity.current
    val statusBarHeight = WindowInsets.systemBars.getTop(density)
    val topPadding = remember(statusBarHeight) {
        with(density) { statusBarHeight.toDp() + APP_BAR_HEIGHT.dp + 16.dp }
    }

    DetailsScreenContent(
        character = character,
        topPadding = topPadding,
        onLevelChange = { delta -> viewModel.changeLevel(characterId, delta) },
        onPowerChange = { delta -> viewModel.changePower(characterId, delta) },
        onGenderToggle = { id -> viewModel.toggleGender(id) },
        onFightClick = onFight,
        onResetClick = { showResetDialog = true },
        onEditClick = { showEditDialog = true },
        onDeleteClick = { showDeleteDialog = true },
        onBackClick = onBack,
        onTimerClick = onTimerClick,
        onDiceClick = { showDiceDialog = true },
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
            onDismiss = { showDiceDialog = false }
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun DetailsScreenContent(
    character: Character,
    topPadding: Dp,
    onLevelChange: (Int) -> Unit,
    onPowerChange: (Int) -> Unit,
    onGenderToggle: (Long) -> Unit,
    onFightClick: () -> Unit,
    onResetClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit,
    onTimerClick: () -> Unit,
    onDiceClick: () -> Unit,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    val lazyListState = rememberLazyListState()

    val isCharacterCardVisible by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0 &&
                    lazyListState.firstVisibleItemScrollOffset < 300
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = topPadding,
                bottom = 140.dp
            )
        ) {
            item {
                CharacterListItem(
                    character = character,
                    hideName = true,
                    showLevelButtons = false,
                    showItemsButtons = false,
                    onLevelChange = onLevelChange,
                    onItemsChange = onPowerChange,
                    onGenderToggle = {
                        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                        onGenderToggle(character.id)
                    },
                    animatedContentScope = animatedContentScope,
                    sharedTransitionScope = sharedTransitionScope
                )
            }


            item {
                Spacer(modifier = Modifier.height(16.dp))
                LevelControlWidget(
                    onLevelChange = { delta ->
                        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                        onLevelChange(delta)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                PowerControlWidget(
                    onPowerChange = { delta ->
                        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                        onPowerChange(delta)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Reset button
                    MunchkinIconTextButton(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                            onResetClick()
                        },
                        icon = MunchkinIcons.Reset,
                        text = stringResource(R.string.reset),
                        modifier = Modifier.weight(1f),
                        textStyle = MunchkinTheme.typography.labelMedium,
                        contentPadding = 16.dp,
                        rippleColor = MunchkinTheme.colors.secondary,
                        bounded = false
                    )

                    // Edit button (slightly larger)
                    MunchkinIconTextButton(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                            onEditClick()
                        },
                        icon = MunchkinIcons.Edit,
                        text = stringResource(R.string.edit),
                        modifier = Modifier.weight(1.3f),
                        textStyle = MunchkinTheme.typography.labelMedium,
                        contentPadding = 16.dp,
                        rippleColor = MunchkinTheme.colors.primary,
                        bounded = false
                    )

                    // Delete button
                    MunchkinIconTextButton(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onDeleteClick()
                        },
                        icon = MunchkinIcons.Remove,
                        text = stringResource(R.string.delete),
                        modifier = Modifier.weight(1f),
                        textStyle = MunchkinTheme.typography.labelMedium,
                        contentPadding = 16.dp,
                        rippleColor = MunchkinTheme.colors.red,
                        bounded = false
                    )
                }
            }
        }

        // Top App Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MunchkinTheme.colors.background.copy(alpha = 0.95f))
        ) {
            MunchkinTopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top)),
                title = character.name,
                onBack = onBackClick,
                animatedContentScope = animatedContentScope,
                sharedTransitionScope = sharedTransitionScope,
                titleSharedKey = "character-name-${character.id}",
                actions = {
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
                    MunchkinIconButton(onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                        onFightClick()
                    }) {
                        MunchkinIcon(
                            imageVector = MunchkinIcons.Fight,
                            tint = MunchkinTheme.colors.red
                        )
                    }
                }
            )
        }

        // Compact bottom widget - appears when character card is scrolled out of view
        AnimatedVisibility(
            visible = !isCharacterCardVisible,
            enter = slideInVertically { it },
            exit = slideOutVertically { it },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            CompactCharacterWidget(character = character)
        }
    }
}

@Composable
private fun CompactCharacterWidget(
    character: Character,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(MunchkinTheme.colors.background.copy(alpha = 0.95f))
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(top = 20.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Level section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                MunchkinText(
                    text = stringResource(R.string.level),
                    style = MunchkinTheme.typography.labelSmall,
                    color = MunchkinTheme.colors.grey
                )
                Spacer(modifier = Modifier.height(4.dp))
                AnimatedNumber(
                    value = character.level,
                    style = MunchkinTheme.typography.titleLarge,
                    color = MunchkinTheme.colors.primary
                )
            }

            // Total power display
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                MunchkinText(
                    text = stringResource(R.string.power),
                    style = MunchkinTheme.typography.labelSmall,
                    color = MunchkinTheme.colors.grey
                )
                Spacer(modifier = Modifier.height(4.dp))
                AnimatedNumber(
                    value = character.level + character.items,
                    style = MunchkinTheme.typography.headlineLarge,
                    color = MunchkinTheme.colors.secondary
                )
            }

            // Items section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                MunchkinText(
                    text = stringResource(R.string.items),
                    style = MunchkinTheme.typography.labelSmall,
                    color = MunchkinTheme.colors.grey
                )
                Spacer(modifier = Modifier.height(4.dp))
                AnimatedNumber(
                    value = character.items,
                    style = MunchkinTheme.typography.titleLarge,
                    color = MunchkinTheme.colors.primary
                )
            }
        }
    }
}

@Composable
private fun LevelControlWidget(
    onLevelChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Title
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            MunchkinText(
                text = stringResource(R.string.level),
                style = MunchkinTheme.typography.titleMedium,
                color = MunchkinTheme.colors.onBackground
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Minus button
            LevelControlCard(
                onClick = { onLevelChange(-1) },
                isNegative = true,
                modifier = Modifier.weight(1f)
            )

            // Plus button
            LevelControlCard(
                onClick = { onLevelChange(+1) },
                isNegative = false,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun LevelControlCard(
    onClick: () -> Unit,
    isNegative: Boolean,
    modifier: Modifier = Modifier
) {
    MunchkinCard(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        color = MunchkinTheme.colors.onBackground,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            MunchkinIcon(
                imageVector = if (isNegative) Icons.Default.Remove else Icons.Default.Add,
                tint = MunchkinTheme.colors.onBackground,
                size = 36.dp
            )
        }
    }
}

@Composable
private fun PowerControlWidget(
    onPowerChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Title
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            MunchkinText(
                text = stringResource(R.string.items),
                style = MunchkinTheme.typography.titleMedium,
                color = MunchkinTheme.colors.onBackground
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Minus buttons column
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (value in listOf(-1, -2, -3, -4, -5)) {
                    PowerControlCard(
                        value = value,
                        onClick = { onPowerChange(value) },
                        isNegative = true
                    )
                }
            }

            // Plus buttons column
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (value in listOf(+1, +2, +3, +4, +5)) {
                    PowerControlCard(
                        value = value,
                        onClick = { onPowerChange(value) },
                        isNegative = false
                    )
                }
            }
        }
    }
}

@Composable
private fun PowerControlCard(
    value: Int,
    onClick: () -> Unit,
    isNegative: Boolean,
    modifier: Modifier = Modifier
) {
    MunchkinCard(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        color = MunchkinTheme.colors.onBackground,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            MunchkinIcon(
                imageVector = if (isNegative) Icons.Default.Remove else Icons.Default.Add,
                tint = MunchkinTheme.colors.onBackground,
                size = 24.dp
            )
            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            MunchkinText(
                text = kotlin.math.abs(value).toString(),
                style = MunchkinTheme.typography.headlineLarge,
                color = MunchkinTheme.colors.onBackground
            )
        }
    }
}

@Preview(
    name = "Details Screen",
    device = Devices.PIXEL_4,
    showSystemUi = true,
    showBackground = true
)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun DetailsScreenPreview() {
    val mockCharacter = Character(1, "Aragorn", 5, 8, Gender.MALE)

    MunchkinTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            CharacterListItem(
                character = mockCharacter,
                hideName = true,
                showLevelButtons = false,
                showItemsButtons = false
            )
        }
    }
}
