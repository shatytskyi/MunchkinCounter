package com.shatytskyi.munchcounter.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.data.Character
import com.shatytskyi.munchcounter.ui.components.CommonDiceDialog
import com.shatytskyi.munchcounter.ui.components.CommonTopAppBar
import com.shatytskyi.munchcounter.ui.theme.Dimens
import com.shatytskyi.munchcounter.viewmodel.CharacterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FightScreen(
    viewModel: CharacterViewModel,
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
    var showInfoDialog by remember { mutableStateOf(false) }

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
                verticalArrangement = Arrangement.spacedBy(Dimens.paddingLarge)
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Loading Fight...",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
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
        CommonTopAppBar(
            title = "Fight",
            onBack = onBack,
            actions = {
                IconButton(onClick = { showDiceDialog = true }) {
                    Icon(
                        Icons.Default.Casino,
                        contentDescription = "Dice",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                IconButton(onClick = { showInfoDialog = true }) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = "Info",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
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
                .padding(Dimens.screenPaddingHorizontal),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingLarge)
        ) {
            item {
                Spacer(modifier = Modifier.height(Dimens.paddingMedium))
            }
            
            item {
                // Control buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            monster = Character.createMonster()
                            currentPlayer = player?.copy()
                            initialPower = player?.items ?: 0
                            helper = null
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text("Reset")
                    }

                    Button(
                        onClick = { showHelpersDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary
                        )
                    ) {
                        Icon(
                            Icons.Default.PersonAdd,
                            contentDescription = null,
                            modifier = Modifier.size(Dimens.iconSizeSmall)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Helper")
                    }

                    Button(
                        onClick = onBack,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.outline
                        )
                    ) {
                        Text("Exit")
                    }
                }
            }

            item {
                // Fight Results Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(Dimens.paddingLarge)
                    ) {
                        // Header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "PLAYER",
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "HELPER",
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "RESULT",
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = Dimens.paddingMedium))

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
                                Text(
                                    text = currentPlayer!!.name,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = if (helper != null) playerScore.toString() else currentPlayer!!.power.toString(),
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = when {
                                            helper != null -> MaterialTheme.colorScheme.onSurface
                                            currentPlayer!!.items > initialPower -> MaterialTheme.colorScheme.tertiary
                                            currentPlayer!!.items < initialPower -> MaterialTheme.colorScheme.error
                                            else -> MaterialTheme.colorScheme.onSurface
                                        }
                                    )
                                )
                            }

                            // Helper
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (helper != null) {
                                    Text(
                                        text = "+${helper!!.name}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "(${helper!!.power})",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    TextButton(
                                        onClick = { helper = null }
                                    ) {
                                        Text("Убрать")
                                    }
                                } else {
                                    Text(
                                        text = "-",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            // Result
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = if (scoreDifference > 0) "+$scoreDifference" else scoreDifference.toString(),
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isVictory) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.error
                                    )
                                )

                                Icon(
                                    if (isVictory) Icons.Default.EmojiEvents else Icons.Default.DirectionsRun,
                                    contentDescription = null,
                                    tint = if (isVictory) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(Dimens.iconSizeSmall)
                                )

                                Text(
                                    text = if (isVictory) "ПОБЕДА" else "БЕГСТВО",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = if (isVictory) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.error
                                )

                                // Result button
                                Button(
                                    onClick = {
                                        if (isVictory) {
                                            // Victory - level up
                                            viewModel.changeLevel(playerId, +1)
                                            onBack()
                                        } else {
                                            // Show dice for escape attempt
                                            showDiceDialog = true
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (isVictory) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.error
                                    ),
                                    modifier = Modifier.padding(top = Dimens.paddingMedium)
                                ) {
                                    Text(if (isVictory) "Победить!" else "Сбежать!")
                                }
                            }
                        }
                    }
                }

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
        EscapeDiceDialog(
            onDismiss = { showDiceDialog = false },
            onEscapeResult = { success ->
                if (success || !isVictory) {
                    onBack()
                }
                showDiceDialog = false
            }
        )
    }

    if (showInfoDialog) {
        AlertDialog(
            onDismissRequest = { showInfoDialog = false },
            title = { Text("Информация") },
            text = { Text(stringResource(R.string.info)) },
            confirmButton = {
                TextButton(onClick = { showInfoDialog = false }) {
                    Text("OK")
                }
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
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Dimens.paddingLarge)
        ) {
            Text(
                text = "${player.name} - Уровень ${player.lvl}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(Dimens.paddingMedium))

            // Level controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { onLevelChange(-1) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("LVL-")
                }
                Spacer(modifier = Modifier.width(Dimens.paddingLarge))
                Button(
                    onClick = { onLevelChange(+1) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    Text("LVL+")
                }
            }

            Spacer(modifier = Modifier.height(Dimens.paddingLarge))

            // Power controls
            Text(
text = "Power: ${player.items}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = when {
                    player.items > initialPower -> MaterialTheme.colorScheme.tertiary
                    player.items < initialPower -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.onSurface
                }
            )

            Spacer(modifier = Modifier.height(Dimens.paddingMedium))

            // Power buttons grid
            Column(
                verticalArrangement = Arrangement.spacedBy(Dimens.cardSpacing)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { onPowerChange(-5) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.weight(1f)
                    ) { Text("-5") }
                    Button(
                        onClick = { onPowerChange(-4) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.weight(1f)
                    ) { Text("-4") }
                    Button(
                        onClick = { onPowerChange(-3) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.weight(1f)
                    ) { Text("-3") }
                    Button(
                        onClick = { onPowerChange(-2) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.weight(1f)
                    ) { Text("-2") }
                    Button(
                        onClick = { onPowerChange(-1) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.weight(1f)
                    ) { Text("-1") }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { onPowerChange(+1) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                        modifier = Modifier.weight(1f)
                    ) { Text("+1") }
                    Button(
                        onClick = { onPowerChange(+2) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                        modifier = Modifier.weight(1f)
                    ) { Text("+2") }
                    Button(
                        onClick = { onPowerChange(+3) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                        modifier = Modifier.weight(1f)
                    ) { Text("+3") }
                    Button(
                        onClick = { onPowerChange(+4) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                        modifier = Modifier.weight(1f)
                    ) { Text("+4") }
                    Button(
                        onClick = { onPowerChange(+5) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                        modifier = Modifier.weight(1f)
                    ) { Text("+5") }
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
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Dimens.paddingLarge)
        ) {
            Text(
                text = "Монстр - Сила ${monster.power}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(Dimens.paddingLarge))

            // Monster power buttons
            Column(
                verticalArrangement = Arrangement.spacedBy(Dimens.cardSpacing)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { onPowerChange(-5) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.weight(1f)
                    ) { Text("-5") }
                    Button(
                        onClick = { onPowerChange(-4) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.weight(1f)
                    ) { Text("-4") }
                    Button(
                        onClick = { onPowerChange(-3) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.weight(1f)
                    ) { Text("-3") }
                    Button(
                        onClick = { onPowerChange(-2) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.weight(1f)
                    ) { Text("-2") }
                    Button(
                        onClick = { onPowerChange(-1) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.weight(1f)
                    ) { Text("-1") }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { onPowerChange(+1) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                        modifier = Modifier.weight(1f)
                    ) { Text("+1") }
                    Button(
                        onClick = { onPowerChange(+2) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                        modifier = Modifier.weight(1f)
                    ) { Text("+2") }
                    Button(
                        onClick = { onPowerChange(+3) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                        modifier = Modifier.weight(1f)
                    ) { Text("+3") }
                    Button(
                        onClick = { onPowerChange(+4) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                        modifier = Modifier.weight(1f)
                    ) { Text("+4") }
                    Button(
                        onClick = { onPowerChange(+5) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                        modifier = Modifier.weight(1f)
                    ) { Text("+5") }
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
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Выберите помощника") },
        text = {
            Column {
                characters.forEach { character ->
                    val helperName = if (character.name == playerName) "Близнец" else character.name
                    val totalScore = scoreDifference + character.power

                    TextButton(
                        onClick = { onHelperSelected(character) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(helperName)
                            Text(
                                text = if (totalScore > 0) "+$totalScore" else totalScore.toString(),
                                color = if (totalScore > 0) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
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

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Попытка побега") },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Нужно выбросить 5 или 6")

                Spacer(modifier = Modifier.height(Dimens.paddingLarge))

                if (diceResult != null) {
                    val result = diceResult!!
                    val success = result >= 5

                    Text(
                        text = "Результат: $result",
                        style = MaterialTheme.typography.headlineMedium,
                        color = if (success) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.error
                    )

                    Text(
                        text = if (success) "Успешный побег!" else "Побег не удался!",
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (success) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.error
                    )

                    Spacer(modifier = Modifier.height(Dimens.paddingLarge))

                    Button(
                        onClick = { onEscapeResult(success) }
                    ) {
                        Text("Продолжить")
                    }
                } else {
                    Button(
                        onClick = { diceResult = (1..6).random() }
                    ) {
                        Icon(Icons.Default.Casino, contentDescription = null)
                        Spacer(modifier = Modifier.width(Dimens.paddingMedium))
                        Text("Бросить кубик")
                    }
                }
            }
        },
        confirmButton = {
            if (diceResult == null) {
                TextButton(onClick = onDismiss) {
                    Text("Отмена")
                }
            }
        },
        modifier = modifier
    )
}
