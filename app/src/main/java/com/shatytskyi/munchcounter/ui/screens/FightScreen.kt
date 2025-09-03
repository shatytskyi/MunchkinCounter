package com.shatytskyi.munchcounter.ui.screens

import androidx.compose.animation.ExperimentalSharedTransitionApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Compare
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.data.Character
import com.shatytskyi.munchcounter.data.Gender
import com.shatytskyi.munchcounter.ui.components.APP_BAR_HEIGHT
import com.shatytskyi.munchcounter.ui.components.MunchkinCard
import com.shatytskyi.munchcounter.ui.components.MunchkinDialog
import com.shatytskyi.munchcounter.ui.components.MunchkinIcon
import com.shatytskyi.munchcounter.ui.components.MunchkinIconButton
import com.shatytskyi.munchcounter.ui.components.MunchkinIconTextButton
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.components.MunchkinTopAppBar
import com.shatytskyi.munchcounter.ui.components.munchkinClickable
import com.shatytskyi.munchcounter.ui.dialogs.DiceDialog
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme
import com.shatytskyi.munchcounter.viewmodel.CommonViewModel

@OptIn(androidx.compose.animation.ExperimentalSharedTransitionApi::class)
@Composable
fun FightScreen(
    viewModel: CommonViewModel,
    playerId: Long,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val characters by viewModel.characters.collectAsState()
    val player = characters.find { it.id == playerId }

    var playerPower by remember { mutableIntStateOf(0) }
    var monsterPower by remember { mutableIntStateOf(10) }
    var helper by remember { mutableStateOf<Character?>(null) }
    var showHelpersDialog by remember { mutableStateOf(false) }
    var showDiceDialog by remember { mutableStateOf(false) }

    LaunchedEffect(player) {
        if (player != null) {
            playerPower = player.level + player.items
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadCharacters()
    }

    if (player == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MunchkinText(
                    text = "●●●",
                    style = MunchkinTheme.typography.headlineLarge,
                    color = MunchkinTheme.colors.primary
                )
                MunchkinText(
                    text = stringResource(R.string.loading_fight),
                    style = MunchkinTheme.typography.bodyLarge,
                    color = MunchkinTheme.colors.onBackground
                )
            }
        }
        return
    }

    val density = LocalDensity.current
    val statusBarHeight = WindowInsets.systemBars.getTop(density)
    val topPadding = remember(statusBarHeight) {
        with(density) { statusBarHeight.toDp() + APP_BAR_HEIGHT.dp + 16.dp }
    }

    FightScreenContent(
        player = player,
        playerPower = playerPower,
        monsterPower = monsterPower,
        helper = helper,
        topPadding = topPadding,
        onPlayerPowerChange = { playerPower += it },
        onMonsterPowerChange = { monsterPower += it },
        onHelpersClick = { showHelpersDialog = true },
        onDiceClick = { showDiceDialog = true },
        onBackClick = onBack,
        modifier = modifier
    )

    if (showHelpersDialog) {
        HelpersDialog(
            characters = characters.filter { it.id != playerId },
            onDismiss = { showHelpersDialog = false },
            onHelperSelected = { selectedHelper ->
                helper = selectedHelper
                showHelpersDialog = false
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
private fun FightScreenContent(
    player: Character,
    playerPower: Int,
    monsterPower: Int,
    helper: Character?,
    topPadding: Dp,
    onPlayerPowerChange: (Int) -> Unit,
    onMonsterPowerChange: (Int) -> Unit,
    onHelpersClick: () -> Unit,
    onDiceClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current

    val totalPlayerPower = if (helper != null) {
        playerPower + helper.level + helper.items
    } else {
        playerPower
    }
    val powerDifference = totalPlayerPower - monsterPower

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = topPadding,
                bottom = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // 1. Статистика игрока
            item {
                PlayerStatsSection(
                    player = player,
                    currentPower = playerPower,
                    originalPower = player.level + player.items
                )
            }

            // 2. Помощник
            item {
                HelperSection(
                    helper = helper,
                    onHelpersClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                        onHelpersClick()
                    }
                )
            }

            // 3. Сравнение сил
            item {
                PowerComparisonSection(
                    playerPower = totalPlayerPower,
                    monsterPower = monsterPower,
                    difference = powerDifference
                )
            }

            // 4. Управление силой игрока
            item {
                PlayerPowerControlSection(
                    onPowerChange = { delta ->
                        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                        onPlayerPowerChange(delta)
                    }
                )
            }

            // 5. Управление силой монстра
            item {
                MonsterPowerControlSection(
                    onPowerChange = { delta ->
                        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                        onMonsterPowerChange(delta)
                    }
                )
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
                title = stringResource(R.string.fight_title),
                onBack = onBackClick,
                actions = {
                    MunchkinIconButton(onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                        onDiceClick()
                    }) {
                        MunchkinIcon(
                            imageVector = Icons.Default.Casino,
                            tint = MunchkinTheme.colors.onBackground
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun PlayerStatsSection(
    player: Character,
    currentPower: Int,
    originalPower: Int,
    modifier: Modifier = Modifier
) {
    MunchkinCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = MunchkinTheme.colors.background,
        color = MunchkinTheme.colors.primary
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MunchkinText(
                text = stringResource(R.string.player_stats),
                style = MunchkinTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MunchkinTheme.colors.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Name
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    MunchkinText(
                        text = stringResource(R.string.name),
                        style = MunchkinTheme.typography.labelSmall,
                        color = MunchkinTheme.colors.grey
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    MunchkinText(
                        text = player.name,
                        style = MunchkinTheme.typography.titleMedium,
                        color = MunchkinTheme.colors.onBackground
                    )
                }

                // Level
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
                    MunchkinText(
                        text = player.level.toString(),
                        textAlign = TextAlign.Center,
                        style = MunchkinTheme.typography.titleLarge,
                        color = MunchkinTheme.colors.primary
                    )
                }

                // Current Power
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    MunchkinText(
                        text = stringResource(R.string.current_power),
                        style = MunchkinTheme.typography.labelSmall,
                        color = MunchkinTheme.colors.grey
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    MunchkinText(
                        text = currentPower.toString(),
                        textAlign = TextAlign.Center,
                        style = MunchkinTheme.typography.headlineLarge,
                        color = when {
                            currentPower > originalPower -> MunchkinTheme.colors.secondary
                            currentPower < originalPower -> MunchkinTheme.colors.red
                            else -> MunchkinTheme.colors.secondary
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun HelperSection(
    helper: Character?,
    onHelpersClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    MunchkinCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = MunchkinTheme.colors.background,
        color = MunchkinTheme.colors.primary
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MunchkinIcon(
                imageVector = Icons.Default.PersonAdd,
                tint = MunchkinTheme.colors.primary,
                size = 24.dp
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                MunchkinText(
                    text = stringResource(R.string.helper),
                    style = MunchkinTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MunchkinTheme.colors.onBackground
                )

                if (helper != null) {
                    MunchkinText(
                        text = "${helper.name} (${helper.level + helper.items})",
                        textAlign = TextAlign.Center,
                        style = MunchkinTheme.typography.bodyMedium,
                        color = MunchkinTheme.colors.secondary
                    )
                } else {
                    MunchkinText(
                        text = stringResource(R.string.no_helper),
                        style = MunchkinTheme.typography.bodyMedium,
                        color = MunchkinTheme.colors.grey
                    )
                }
            }

            MunchkinIconTextButton(
                onClick = onHelpersClick,
                icon = if (helper != null) Icons.Default.Remove else Icons.Default.Add,
                text = if (helper != null) stringResource(R.string.remove) else stringResource(R.string.add),
                rippleColor = MunchkinTheme.colors.primary,
                bounded = false
            )
        }
    }
}

@Composable
private fun PowerComparisonSection(
    playerPower: Int,
    monsterPower: Int,
    difference: Int,
    modifier: Modifier = Modifier
) {
    MunchkinCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = MunchkinTheme.colors.background,
        color = MunchkinTheme.colors.primary
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MunchkinText(
                text = stringResource(R.string.power_comparison),
                style = MunchkinTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MunchkinTheme.colors.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Player Power
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    MunchkinText(
                        text = stringResource(R.string.player),
                        style = MunchkinTheme.typography.labelSmall,
                        color = MunchkinTheme.colors.grey
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    MunchkinText(
                        text = playerPower.toString(),
                        textAlign = TextAlign.Center,
                        style = MunchkinTheme.typography.headlineLarge,
                        color = MunchkinTheme.colors.primary
                    )
                }

                // VS
                MunchkinIcon(
                    imageVector = Icons.Outlined.Compare,
                    tint = MunchkinTheme.colors.grey,
                    size = 32.dp
                )

                // Monster Power
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    MunchkinText(
                        text = stringResource(R.string.monster),
                        style = MunchkinTheme.typography.labelSmall,
                        color = MunchkinTheme.colors.grey
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    MunchkinText(
                        text = monsterPower.toString(),
                        textAlign = TextAlign.Center,
                        style = MunchkinTheme.typography.headlineLarge,
                        color = MunchkinTheme.colors.red
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Difference
            MunchkinText(
                text = if (difference >= 0) {
                    stringResource(R.string.winning_by, difference)
                } else {
                    stringResource(R.string.losing_by, -difference)
                },
                style = MunchkinTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = if (difference >= 0) MunchkinTheme.colors.secondary else MunchkinTheme.colors.red
            )
        }
    }
}

@Composable
private fun PlayerPowerControlSection(
    onPowerChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    MunchkinCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = MunchkinTheme.colors.background,
        color = MunchkinTheme.colors.primary
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MunchkinText(
                text = stringResource(R.string.player_power_control),
                style = MunchkinTheme.typography.titleMedium,
                color = MunchkinTheme.colors.onBackground,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Minus buttons column
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (value in listOf(-1, -2, -3, -4, -5)) {
                        PowerControlButton(
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
                        PowerControlButton(
                            value = value,
                            onClick = { onPowerChange(value) },
                            isNegative = false
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MonsterPowerControlSection(
    onPowerChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    MunchkinCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = MunchkinTheme.colors.background,
        color = MunchkinTheme.colors.red
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MunchkinText(
                text = stringResource(R.string.monster_power_control),
                style = MunchkinTheme.typography.titleMedium,
                color = MunchkinTheme.colors.onBackground,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Minus buttons column
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (value in listOf(-1, -2, -3, -4, -5)) {
                        PowerControlButton(
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
                        PowerControlButton(
                            value = value,
                            onClick = { onPowerChange(value) },
                            isNegative = false
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PowerControlButton(
    value: Int,
    onClick: () -> Unit,
    isNegative: Boolean,
    modifier: Modifier = Modifier
) {
    MunchkinCard(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        color = if (isNegative) MunchkinTheme.colors.red else MunchkinTheme.colors.secondary,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            MunchkinIcon(
                imageVector = if (isNegative) Icons.Default.Remove else Icons.Default.Add,
                tint = MunchkinTheme.colors.onBackground,
                size = 20.dp
            )
            Spacer(modifier = Modifier.width(4.dp))
            MunchkinText(
                text = kotlin.math.abs(value).toString(),
                style = MunchkinTheme.typography.titleMedium,
                color = MunchkinTheme.colors.onBackground
            )
        }
    }
}

@Composable
private fun HelpersDialog(
    characters: List<Character>,
    onDismiss: () -> Unit,
    onHelperSelected: (Character) -> Unit,
    modifier: Modifier = Modifier
) {
    MunchkinDialog(
        onDismissRequest = onDismiss,
        title = stringResource(R.string.choose_helper),
        content = {
            Column {
                characters.forEach { character ->
                    MunchkinCard(
                        onClick = { onHelperSelected(character) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .munchkinClickable(
                                onClick = { onHelperSelected(character) }
                            ),
                        backgroundColor = MunchkinTheme.colors.background,
                        color = MunchkinTheme.colors.primary
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            MunchkinText(
                                text = character.name,
                                style = MunchkinTheme.typography.bodyLarge,
                                color = MunchkinTheme.colors.onBackground
                            )
                            MunchkinText(
                                text = "(${character.level + character.items})",
                                textAlign = TextAlign.Center,
                                style = MunchkinTheme.typography.titleMedium,
                                color = MunchkinTheme.colors.secondary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        },
        confirmButton = {
            MunchkinIconTextButton(
                onClick = onDismiss,
                icon = Icons.Default.Remove,
                text = stringResource(R.string.cancel),
                rippleColor = MunchkinTheme.colors.grey,
                bounded = false
            )
        },
        modifier = modifier
    )
}

@Preview(
    name = "Fight Screen",
    device = Devices.PIXEL_4,
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun FightScreenPreview() {
    val mockCharacter = Character(1, "Aragorn", 5, 8, Gender.MALE)

    MunchkinTheme {
        FightScreenContent(
            player = mockCharacter,
            playerPower = 13,
            monsterPower = 10,
            helper = null,
            topPadding = 100.dp,
            onPlayerPowerChange = {},
            onMonsterPowerChange = {},
            onHelpersClick = {},
            onDiceClick = {},
            onBackClick = {}
        )
    }
}
