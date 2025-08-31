package com.shatytskyi.munchcounter.ui.screens

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
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.data.Character
import com.shatytskyi.munchcounter.ui.components.CommonDiceDialog
import com.shatytskyi.munchcounter.ui.components.MunchkinButton
import com.shatytskyi.munchcounter.ui.components.MunchkinButtonDefaults
import com.shatytskyi.munchcounter.ui.components.MunchkinCard
import com.shatytskyi.munchcounter.ui.components.MunchkinDialog
import com.shatytskyi.munchcounter.ui.components.MunchkinIcon
import com.shatytskyi.munchcounter.ui.components.MunchkinIconButton
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.components.MunchkinTextButton
import com.shatytskyi.munchcounter.ui.components.MunchkinTopAppBar
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme
import com.shatytskyi.munchcounter.viewmodel.CommonViewModel
import kotlin.random.Random

@Composable
fun FightScreen(
    viewModel: CommonViewModel,
    playerId: Long,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val characters by viewModel.characters.collectAsState()
    val player = characters.find { it.id == playerId }

    var currentPlayer by remember { mutableStateOf<Character?>(null) }
    var monster by remember { mutableStateOf(Character.createMonster()) }
    var helper by remember { mutableStateOf<Character?>(null) }
    var initialPower by remember { mutableStateOf(0) }

    var showHelpersDialog by remember { mutableStateOf(false) }
    var showDiceDialog by remember { mutableStateOf(false) }
    var showEscapeDiceDialog by remember { mutableStateOf(false) }

    // Initialize player when found
    LaunchedEffect(player) {
        if (player != null && currentPlayer == null) {
            currentPlayer = player.copy()
            initialPower = player.items
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadCharacters()
    }

    if (currentPlayer == null) {
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
                    color = MunchkinTheme.colors.onSurface
                )
            }
        }
        return
    }

    val playerScore = if (helper != null) {
        currentPlayer!!.power + helper!!.power
    } else {
        currentPlayer!!.power
    }
    val scoreDifference = playerScore - monster.power
    val isVictory = scoreDifference > 0

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Top App Bar
        MunchkinTopAppBar(
            title = stringResource(R.string.fight_title),
            onBack = onBack,
            actions = {
                MunchkinIconButton(onClick = { showDiceDialog = true }) {
                    MunchkinIcon(
                        Icons.Default.Casino,
                        tint = MunchkinTheme.colors.onSurface
                    )
                }
            }
        )

        // Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.navigationBars.only(WindowInsetsSides.Bottom))
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                // Control buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MunchkinButton(
                        onClick = {
                            monster = Character.createMonster()
                            currentPlayer = player?.copy()
                            initialPower = player?.items ?: 0
                            helper = null
                        },
                        colors = MunchkinButtonDefaults.secondaryColors(),
                        borderColor = MunchkinTheme.colors.secondary.copy(alpha = 0.5f)
                    ) {
                        MunchkinText(stringResource(R.string.reset))
                    }

                    MunchkinButton(
                        onClick = { showHelpersDialog = true },
                        colors = MunchkinButtonDefaults.tertiaryColors(),
                        borderColor = MunchkinTheme.colors.tertiary.copy(alpha = 0.5f)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            MunchkinIcon(
                                Icons.Default.PersonAdd,
                                size = 24.dp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            MunchkinText(stringResource(R.string.helper))
                        }
                    }

                    MunchkinButton(
                        onClick = onBack,
                        colors = MunchkinButtonDefaults.buttonColors(
                            containerColor = MunchkinTheme.colors.outline,
                            contentColor = MunchkinTheme.colors.onSurface
                        ),
                        borderColor = MunchkinTheme.colors.outline.copy(alpha = 0.5f)
                    ) {
                        MunchkinText(stringResource(R.string.exit))
                    }
                }
            }

            item {
                // Fight Results Card
                MunchkinCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = MunchkinTheme.colors.surfaceContainer,
                    borderColor = MunchkinTheme.colors.outline.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            MunchkinText(
                                text = stringResource(R.string.player_header),
                                style = MunchkinTheme.typography.titleSmall,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                color = MunchkinTheme.colors.onSurfaceVariant
                            )
                            MunchkinText(
                                text = stringResource(R.string.helper_header),
                                style = MunchkinTheme.typography.titleSmall,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                color = MunchkinTheme.colors.onSurfaceVariant
                            )
                            MunchkinText(
                                text = stringResource(R.string.result_header),
                                style = MunchkinTheme.typography.titleSmall,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                color = MunchkinTheme.colors.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Values
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Player
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                MunchkinText(
                                    text = currentPlayer!!.name,
                                    style = MunchkinTheme.typography.bodyMedium
                                )
                                MunchkinText(
                                    text = if (helper != null) playerScore.toString() else currentPlayer!!.power.toString(),
                                    style = MunchkinTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = when {
                                        helper != null -> MunchkinTheme.colors.onSurface
                                        currentPlayer!!.items > initialPower -> MunchkinTheme.colors.tertiary
                                        currentPlayer!!.items < initialPower -> MunchkinTheme.colors.error
                                        else -> MunchkinTheme.colors.onSurface
                                    }
                                )
                            }

                            // Helper
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (helper != null) {
                                    MunchkinText(
                                        text = "+${helper!!.name}",
                                        style = MunchkinTheme.typography.bodyMedium
                                    )
                                    MunchkinText(
                                        text = "(${helper!!.power})",
                                        style = MunchkinTheme.typography.titleMedium
                                    )
                                    MunchkinTextButton(
                                        onClick = { helper = null },
                                        text = stringResource(R.string.remove_helper)
                                    )
                                } else {
                                    MunchkinText(
                                        text = "-",
                                        style = MunchkinTheme.typography.bodyMedium,
                                        color = MunchkinTheme.colors.onSurfaceVariant
                                    )
                                }
                            }

                            // Result
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                MunchkinText(
                                    text = if (scoreDifference > 0) "+$scoreDifference" else scoreDifference.toString(),
                                    style = MunchkinTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = if (isVictory) MunchkinTheme.colors.tertiary else MunchkinTheme.colors.error
                                )

                                MunchkinIcon(
                                    if (isVictory) Icons.Default.EmojiEvents else Icons.AutoMirrored.Filled.DirectionsRun,
                                    tint = if (isVictory) MunchkinTheme.colors.tertiary else MunchkinTheme.colors.error,
                                    size = 24.dp
                                )

                                MunchkinText(
                                    text = if (isVictory) stringResource(R.string.victory) else stringResource(
                                        R.string.escape
                                    ),
                                    style = MunchkinTheme.typography.labelMedium,
                                    color = if (isVictory) MunchkinTheme.colors.tertiary else MunchkinTheme.colors.error
                                )

                                // Result button
                                MunchkinButton(
                                    onClick = {
                                        if (isVictory) {
                                            // Victory - level up
                                            viewModel.changeLevel(playerId, +1)
                                            onBack()
                                        } else {
                                            // Show dice for escape attempt
                                            showEscapeDiceDialog = true
                                        }
                                    },
                                    colors = if (isVictory) {
                                        MunchkinButtonDefaults.tertiaryColors()
                                    } else {
                                        MunchkinButtonDefaults.errorColors()
                                    },
                                    borderColor = if (isVictory) {
                                        MunchkinTheme.colors.tertiary.copy(alpha = 0.5f)
                                    } else {
                                        MunchkinTheme.colors.error.copy(alpha = 0.5f)
                                    },
                                    modifier = Modifier.padding(top = 8.dp)
                                ) {
                                    MunchkinText(
                                        if (isVictory) stringResource(R.string.win_button) else stringResource(
                                            R.string.run_button
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                // Player stats and controls
                PlayerFightControls(
                    player = currentPlayer!!,
                    initialPower = initialPower,
                    onPowerChange = { delta ->
                        currentPlayer = currentPlayer!!.addPower(delta)
                    },
                    onLevelChange = { delta ->
                        currentPlayer = currentPlayer!!.addLevel(delta)
                        viewModel.changeLevel(playerId, delta)
                    }
                )
            }

            item {
                // Monster controls
                MonsterFightControls(
                    monster = monster,
                    onPowerChange = { delta ->
                        monster = monster.addPower(delta)
                    }
                )
            }
        }
    }

    // Dialogs
    if (showHelpersDialog) {
        HelpersDialog(
            characters = characters.filter { it.id != playerId },
            playerName = currentPlayer!!.name,
            scoreDifference = scoreDifference,
            onDismiss = { showHelpersDialog = false },
            onHelperSelected = { selectedHelper ->
                helper = selectedHelper
                showHelpersDialog = false
            }
        )
    }

    if (showDiceDialog) {
        CommonDiceDialog(onDismiss = { showDiceDialog = false })
    }

    if (showEscapeDiceDialog) {
        EscapeDiceDialog(
            onDismiss = { showEscapeDiceDialog = false },
            onEscapeResult = { success ->
                if (success || !isVictory) {
                    onBack()
                }
                showEscapeDiceDialog = false
            }
        )
    }
}

@Composable
private fun PlayerFightControls(
    player: Character,
    initialPower: Int,
    onPowerChange: (Int) -> Unit,
    onLevelChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    MunchkinCard(
        modifier = modifier.fillMaxWidth(),
        borderColor = MunchkinTheme.colors.primary.copy(alpha = 0.3f),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            MunchkinText(
                text = stringResource(R.string.player_level, player.name, player.lvl),
                style = MunchkinTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Level controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                MunchkinButton(
                    onClick = { onLevelChange(-1) },
                    colors = MunchkinButtonDefaults.errorColors(),
                    borderColor = MunchkinTheme.colors.error.copy(alpha = 0.5f)
                ) {
                    MunchkinText("LVL-")
                }
                Spacer(modifier = Modifier.width(16.dp))
                MunchkinButton(
                    onClick = { onLevelChange(+1) },
                    colors = MunchkinButtonDefaults.tertiaryColors(),
                    borderColor = MunchkinTheme.colors.tertiary.copy(alpha = 0.5f)
                ) {
                    MunchkinText("LVL+")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Power controls
            MunchkinText(
                text = stringResource(R.string.player_power, player.items),
                style = MunchkinTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = when {
                    player.items > initialPower -> MunchkinTheme.colors.tertiary
                    player.items < initialPower -> MunchkinTheme.colors.error
                    else -> MunchkinTheme.colors.onSurface
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Power buttons grid
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    MunchkinButton(
                        onClick = { onPowerChange(-5) },
                        colors = MunchkinButtonDefaults.errorColors(),
                        borderColor = MunchkinTheme.colors.error.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) { MunchkinText("-5") }
                    MunchkinButton(
                        onClick = { onPowerChange(-4) },
                        colors = MunchkinButtonDefaults.errorColors(),
                        borderColor = MunchkinTheme.colors.error.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) { MunchkinText("-4") }
                    MunchkinButton(
                        onClick = { onPowerChange(-3) },
                        colors = MunchkinButtonDefaults.errorColors(),
                        borderColor = MunchkinTheme.colors.error.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) { MunchkinText("-3") }
                    MunchkinButton(
                        onClick = { onPowerChange(-2) },
                        colors = MunchkinButtonDefaults.errorColors(),
                        borderColor = MunchkinTheme.colors.error.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) { MunchkinText("-2") }
                    MunchkinButton(
                        onClick = { onPowerChange(-1) },
                        colors = MunchkinButtonDefaults.errorColors(),
                        borderColor = MunchkinTheme.colors.error.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) { MunchkinText("-1") }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    MunchkinButton(
                        onClick = { onPowerChange(+1) },
                        colors = MunchkinButtonDefaults.tertiaryColors(),
                        borderColor = MunchkinTheme.colors.tertiary.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) { MunchkinText("+1") }
                    MunchkinButton(
                        onClick = { onPowerChange(+2) },
                        colors = MunchkinButtonDefaults.tertiaryColors(),
                        borderColor = MunchkinTheme.colors.tertiary.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) { MunchkinText("+2") }
                    MunchkinButton(
                        onClick = { onPowerChange(+3) },
                        colors = MunchkinButtonDefaults.tertiaryColors(),
                        borderColor = MunchkinTheme.colors.tertiary.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) { MunchkinText("+3") }
                    MunchkinButton(
                        onClick = { onPowerChange(+4) },
                        colors = MunchkinButtonDefaults.tertiaryColors(),
                        borderColor = MunchkinTheme.colors.tertiary.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) { MunchkinText("+4") }
                    MunchkinButton(
                        onClick = { onPowerChange(+5) },
                        colors = MunchkinButtonDefaults.tertiaryColors(),
                        borderColor = MunchkinTheme.colors.tertiary.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) { MunchkinText("+5") }
                }
            }
        }
    }
}

@Composable
private fun MonsterFightControls(
    monster: Character,
    onPowerChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    MunchkinCard(
        modifier = modifier.fillMaxWidth(),
        borderColor = MunchkinTheme.colors.error.copy(alpha = 0.3f),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            MunchkinText(
                text = stringResource(R.string.monster_power, monster.power),
                style = MunchkinTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Monster power buttons
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    MunchkinButton(
                        onClick = { onPowerChange(-5) },
                        colors = MunchkinButtonDefaults.errorColors(),
                        borderColor = MunchkinTheme.colors.error.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) { MunchkinText("-5") }
                    MunchkinButton(
                        onClick = { onPowerChange(-4) },
                        colors = MunchkinButtonDefaults.errorColors(),
                        borderColor = MunchkinTheme.colors.error.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) { MunchkinText("-4") }
                    MunchkinButton(
                        onClick = { onPowerChange(-3) },
                        colors = MunchkinButtonDefaults.errorColors(),
                        borderColor = MunchkinTheme.colors.error.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) { MunchkinText("-3") }
                    MunchkinButton(
                        onClick = { onPowerChange(-2) },
                        colors = MunchkinButtonDefaults.errorColors(),
                        borderColor = MunchkinTheme.colors.error.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) { MunchkinText("-2") }
                    MunchkinButton(
                        onClick = { onPowerChange(-1) },
                        colors = MunchkinButtonDefaults.errorColors(),
                        borderColor = MunchkinTheme.colors.error.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) { MunchkinText("-1") }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    MunchkinButton(
                        onClick = { onPowerChange(+1) },
                        colors = MunchkinButtonDefaults.tertiaryColors(),
                        borderColor = MunchkinTheme.colors.tertiary.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) { MunchkinText("+1") }
                    MunchkinButton(
                        onClick = { onPowerChange(+2) },
                        colors = MunchkinButtonDefaults.tertiaryColors(),
                        borderColor = MunchkinTheme.colors.tertiary.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) { MunchkinText("+2") }
                    MunchkinButton(
                        onClick = { onPowerChange(+3) },
                        colors = MunchkinButtonDefaults.tertiaryColors(),
                        borderColor = MunchkinTheme.colors.tertiary.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) { MunchkinText("+3") }
                    MunchkinButton(
                        onClick = { onPowerChange(+4) },
                        colors = MunchkinButtonDefaults.tertiaryColors(),
                        borderColor = MunchkinTheme.colors.tertiary.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) { MunchkinText("+4") }
                    MunchkinButton(
                        onClick = { onPowerChange(+5) },
                        colors = MunchkinButtonDefaults.tertiaryColors(),
                        borderColor = MunchkinTheme.colors.tertiary.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) { MunchkinText("+5") }
                }
            }
        }
    }
}

@Composable
private fun HelpersDialog(
    characters: List<Character>,
    playerName: String,
    scoreDifference: Int,
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
                    val helperName =
                        if (character.name == playerName) stringResource(R.string.twin) else character.name
                    val totalScore = scoreDifference + character.power

                    MunchkinButton(
                        onClick = { onHelperSelected(character) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = MunchkinButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MunchkinTheme.colors.onSurface
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            MunchkinText(helperName)
                            MunchkinText(
                                text = if (totalScore > 0) "+$totalScore" else totalScore.toString(),
                                color = if (totalScore > 0) MunchkinTheme.colors.tertiary else MunchkinTheme.colors.error
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            MunchkinTextButton(
                onClick = onDismiss,
                text = stringResource(R.string.cancel)
            )
        },
        modifier = modifier
    )
}

@Composable
private fun EscapeDiceDialog(
    onDismiss: () -> Unit,
    onEscapeResult: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var diceResult by remember { mutableStateOf<Int?>(null) }
    var isRolling by remember { mutableStateOf(false) }

    MunchkinDialog(
        onDismissRequest = onDismiss,
        title = stringResource(R.string.escape_attempt),
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MunchkinText(
                    text = stringResource(R.string.roll_dice_to_escape),
                    style = MunchkinTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                diceResult?.let { result ->
                    MunchkinText(
                        text = stringResource(R.string.dice_result, result),
                        style = MunchkinTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = if (result >= 5) MunchkinTheme.colors.tertiary else MunchkinTheme.colors.error
                    )

                    MunchkinText(
                        text = if (result >= 5) stringResource(R.string.escape_success) else stringResource(
                            R.string.escape_failed
                        ),
                        style = MunchkinTheme.typography.bodyMedium,
                        color = if (result >= 5) MunchkinTheme.colors.tertiary else MunchkinTheme.colors.error
                    )
                }

                MunchkinButton(
                    onClick = {
                        if (!isRolling) {
                            isRolling = true
                            val result = Random.nextInt(1, 7)
                            diceResult = result
                            isRolling = false
                        }
                    },
                    colors = MunchkinButtonDefaults.primaryColors(),
                    borderColor = MunchkinTheme.colors.primary.copy(alpha = 0.5f),
                    enabled = !isRolling
                ) {
                    MunchkinText(
                        if (diceResult == null) stringResource(R.string.roll_dice) else stringResource(
                            R.string.roll_again
                        )
                    )
                }
            }
        },
        confirmButton = {
            diceResult?.let { result ->
                MunchkinTextButton(
                    onClick = { onEscapeResult(result >= 5) },
                    text = if (result >= 5) stringResource(R.string.run_button) else stringResource(
                        R.string.stay
                    )
                )
            }
        },
        dismissButton = {
            MunchkinTextButton(
                onClick = onDismiss,
                text = stringResource(R.string.cancel)
            )
        },
        modifier = modifier
    )
}
