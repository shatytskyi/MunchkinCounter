package com.shatytskyi.munchcounter.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.data.Character
import com.shatytskyi.munchcounter.ui.components.MunchkinBackground
import com.shatytskyi.munchcounter.ui.components.MunchkinButton
import com.shatytskyi.munchcounter.ui.components.MunchkinPowerButton
import com.shatytskyi.munchcounter.ui.theme.Black
import com.shatytskyi.munchcounter.ui.theme.Dimens
import com.shatytskyi.munchcounter.ui.theme.Primary
import com.shatytskyi.munchcounter.ui.theme.Secondary
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
            initialPower = player.power
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadCharacters()
    }

    if (currentPlayer == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val playerScore = if (helper != null) {
        currentPlayer!!.score + helper!!.score
    } else {
        currentPlayer!!.score
    }
    val scoreDifference = playerScore - monster.score
    val isVictory = scoreDifference > 0

    MunchkinBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            // Top bar with back button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.screenPaddingHorizontal),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_back),
                    contentDescription = "Назад",
                    modifier = Modifier
                        .size(Dimens.iconSizeLarge)
                        .clickable { onBack() },
                    colorFilter = ColorFilter.tint(Secondary)
                )

                Text(
                    text = "Сражение",
                    style = MaterialTheme.typography.titleLarge,
                    color = Black
                )

                Row {
                    Image(
                        painter = painterResource(id = R.drawable.icon_dice),
                        contentDescription = "Кубик",
                        modifier = Modifier
                            .size(Dimens.iconSizeLarge)
                            .padding(Dimens.paddingMedium)
                            .clickable { showDiceDialog = true },
                        colorFilter = ColorFilter.tint(Primary)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.icon_info),
                        contentDescription = "Информация",
                        modifier = Modifier
                            .size(Dimens.iconSizeLarge)
                            .padding(Dimens.paddingMedium)
                            .clickable { showInfoDialog = true },
                        colorFilter = ColorFilter.tint(Primary)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Dimens.paddingMedium),
                verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium)
            ) {
                // Control buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            // Reset fight
                            monster = Character.createMonster()
                            currentPlayer = player?.copy()
                            initialPower = player?.power ?: 0
                            helper = null
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text("Сбросить")
                    }

                    Button(
                        onClick = { showHelpersDialog = true }
                    ) {
                        Text("Помощь")
                    }

                    Button(
                        onClick = onBack,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.outline
                        )
                    ) {
                        Text("Выйти")
                    }
                }

                // Fight table
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(Dimens.paddingLarge)
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
                                text = "ИГРОК",
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "ПОМОЩНИК",
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "РЕЗУЛЬТАТ",
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                        }

                        Divider(modifier = Modifier.padding(vertical = Dimens.paddingMedium))

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
                                    text = if (helper != null) playerScore.toString() else currentPlayer!!.score.toString(),
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = when {
                                            helper != null -> MaterialTheme.colorScheme.onSurface
                                            currentPlayer!!.power > initialPower -> MaterialTheme.colorScheme.tertiary
                                            currentPlayer!!.power < initialPower -> MaterialTheme.colorScheme.error
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
                                        text = "(${helper!!.score})",
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
                MunchkinButton(
                    text = "LVL-",
                    onClick = { onLevelChange(-1) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                )
                Spacer(modifier = Modifier.width(Dimens.paddingLarge))
                MunchkinButton(
                    text = "LVL+",
                    onClick = { onLevelChange(+1) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                )
            }

            Spacer(modifier = Modifier.height(Dimens.paddingLarge))

            // Power controls
            Text(
                text = "Силы: ${player.power}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = when {
                    player.power > initialPower -> MaterialTheme.colorScheme.tertiary
                    player.power < initialPower -> MaterialTheme.colorScheme.error
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
                    MunchkinPowerButton("-5", { onPowerChange(-5) }, isPositive = false)
                    MunchkinPowerButton("-4", { onPowerChange(-4) }, isPositive = false)
                    MunchkinPowerButton("-3", { onPowerChange(-3) }, isPositive = false)
                    MunchkinPowerButton("-2", { onPowerChange(-2) }, isPositive = false)
                    MunchkinPowerButton("-1", { onPowerChange(-1) }, isPositive = false)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MunchkinPowerButton("+1", { onPowerChange(+1) }, isPositive = true)
                    MunchkinPowerButton("+2", { onPowerChange(+2) }, isPositive = true)
                    MunchkinPowerButton("+3", { onPowerChange(+3) }, isPositive = true)
                    MunchkinPowerButton("+4", { onPowerChange(+4) }, isPositive = true)
                    MunchkinPowerButton("+5", { onPowerChange(+5) }, isPositive = true)
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
                text = "Монстр - Сила ${monster.score}",
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
                    MunchkinPowerButton("-5", { onPowerChange(-5) }, isPositive = false)
                    MunchkinPowerButton("-4", { onPowerChange(-4) }, isPositive = false)
                    MunchkinPowerButton("-3", { onPowerChange(-3) }, isPositive = false)
                    MunchkinPowerButton("-2", { onPowerChange(-2) }, isPositive = false)
                    MunchkinPowerButton("-1", { onPowerChange(-1) }, isPositive = false)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MunchkinPowerButton("+1", { onPowerChange(+1) }, isPositive = true)
                    MunchkinPowerButton("+2", { onPowerChange(+2) }, isPositive = true)
                    MunchkinPowerButton("+3", { onPowerChange(+3) }, isPositive = true)
                    MunchkinPowerButton("+4", { onPowerChange(+4) }, isPositive = true)
                    MunchkinPowerButton("+5", { onPowerChange(+5) }, isPositive = true)
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
                    val totalScore = scoreDifference + character.score

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
