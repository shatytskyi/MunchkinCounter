package com.shatytskyi.munchcounter.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.ui.components.EditCharacterDialog
import com.shatytskyi.munchcounter.ui.components.MunchkinBackground
import com.shatytskyi.munchcounter.ui.components.MunchkinCard
import com.shatytskyi.munchcounter.ui.components.WarningDialog
import com.shatytskyi.munchcounter.ui.theme.Black
import com.shatytskyi.munchcounter.ui.theme.DarkGrey
import com.shatytskyi.munchcounter.ui.theme.Primary
import com.shatytskyi.munchcounter.ui.theme.Red
import com.shatytskyi.munchcounter.ui.theme.Secondary
import com.shatytskyi.munchcounter.ui.theme.White
import com.shatytskyi.munchcounter.ui.theme.Dimens
import com.shatytskyi.munchcounter.viewmodel.CharacterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoloScreen(
    viewModel: CharacterViewModel,
    characterId: Long,
    onBack: () -> Unit,
    onFight: () -> Unit,
    modifier: Modifier = Modifier
) {

    val characters by viewModel.characters.collectAsState()
    val character = characters.find { it.id == characterId }

    var showEditDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }
    var showDiceDialog by remember { mutableStateOf(false) }
    var showInfoDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadCharacters()
    }

    if (character == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(Dimens.paddingLarge))
                Text("Загрузка персонажа...")
            }
        }
        return
    }

    MunchkinBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = Dimens.screenPaddingHorizontal, vertical = Dimens.paddingMedium)
        ) {
            // Top section with name and action buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(horizontal = Dimens.paddingExtraLarge, vertical = Dimens.paddingMedium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back button
                Image(
                    painter = painterResource(id = R.drawable.icon_back),
                    contentDescription = "Назад",
                    modifier = Modifier
                        .size(Dimens.iconSizeLarge)
                        .clickable { onBack() },
                    colorFilter = ColorFilter.tint(Secondary)
                )

                // Character name
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Black,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = Dimens.paddingMedium),
                    textAlign = TextAlign.Center
                )

                // Fight button
                Image(
                    painter = painterResource(id = R.drawable.icon_fight),
                    contentDescription = "Сражение",
                    modifier = Modifier
                        .size(Dimens.iconSizeLarge)
                        .padding(Dimens.paddingMedium)
                        .clickable { onFight() },
                    colorFilter = ColorFilter.tint(Red)
                )
            }

            // Level section
            Column(
                modifier = Modifier.padding(Dimens.paddingMedium),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MunchkinCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .padding(horizontal = Dimens.paddingMedium)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Level minus button
                        Image(
                            painter = painterResource(id = R.drawable.icon_left),
                            contentDescription = "Уменьшить уровень",
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable { viewModel.changeLevel(characterId, -1) },
                            colorFilter = ColorFilter.tint(Primary)
                        )

                        // Level value
                        Text(
                            text = character.lvl.toString(),
                            style = MaterialTheme.typography.displayLarge,
                            color = Black,
                            modifier = Modifier
                                .width(100.dp)
                                .padding(Dimens.paddingSmall),
                            textAlign = TextAlign.Center
                        )

                        // Level plus button
                        Image(
                            painter = painterResource(id = R.drawable.icon_right),
                            contentDescription = "Увеличить уровень",
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable { viewModel.changeLevel(characterId, +1) },
                            colorFilter = ColorFilter.tint(Primary)
                        )
                    }
                }

                Text(
                    text = "Уровень",
                    style = MaterialTheme.typography.labelMedium,
                    color = DarkGrey,
                    modifier = Modifier.padding(top = Dimens.paddingMedium)
                )
            }

            // Power section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(Dimens.paddingMedium),
                contentAlignment = Alignment.Center
            ) {
                // Reset button (left)
                Image(
                    painter = painterResource(id = R.drawable.icon_reset),
                    contentDescription = "Сбросить",
                    modifier = Modifier
                        .size(Dimens.iconSizeLarge)
                        .padding(Dimens.paddingMedium)
                        .align(Alignment.CenterStart)
                        .clickable { showResetDialog = true },
                    colorFilter = ColorFilter.tint(Primary)
                )

                // Power value (center)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = character.power.toString(),
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontSize = 72.sp
                        ),
                        color = Black,
                        modifier = Modifier.padding(Dimens.paddingMedium),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Силы",
                        style = MaterialTheme.typography.labelMedium,
                        color = DarkGrey
                    )
                }

                // Edit button (right)
                Image(
                    painter = painterResource(id = R.drawable.icon_edit),
                    contentDescription = "Редактировать",
                    modifier = Modifier
                        .size(Dimens.iconSizeLarge)
                        .padding(Dimens.paddingLarge)
                        .align(Alignment.CenterEnd)
                        .clickable { showEditDialog = true },
                    colorFilter = ColorFilter.tint(Primary)
                )
            }

            // Power control buttons
            MunchkinCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(Dimens.paddingLarge)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(Dimens.cardSpacing),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Power control columns
                    listOf(
                        Triple("1", +1, -1),
                        Triple("2", +2, -2),
                        Triple("3", +3, -3),
                        Triple("4", +4, -4),
                        Triple("5", +5, -5)
                    ).forEach { (label, plusValue, minusValue) ->
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .background(White),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Plus button
                            Image(
                                painter = painterResource(id = R.drawable.icon_up),
                                contentDescription = "Добавить $plusValue",
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                                    .clickable { viewModel.changePower(characterId, plusValue) },
                                colorFilter = ColorFilter.tint(Primary)
                            )

                            // Number label
                            Text(
                                text = label,
                                style = MaterialTheme.typography.displayMedium,
                                color = Black,
                                modifier = Modifier
                                    .height(45.dp)
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                textAlign = TextAlign.Center
                            )

                            // Minus button
                            Image(
                                painter = painterResource(id = R.drawable.icon_down),
                                contentDescription = "Убавить $minusValue",
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                                    .clickable { viewModel.changePower(characterId, minusValue) },
                                colorFilter = ColorFilter.tint(Primary)
                            )
                        }
                    }
                }
            }
        }
    }

    // Dialogs
    if (showEditDialog) {
        EditCharacterDialog(
            character = character,
            onDismiss = { showEditDialog = false },
            onConfirm = { name, level, power ->
                viewModel.updateCharacter(characterId, name, level, power)
                showEditDialog = false
            }
        )
    }

    if (showResetDialog) {
        WarningDialog(
            title = "Сбросить ${character.name}?",
            message = "Персонаж будет сброшен до 1 уровня с 0 силы",
            onDismiss = { showResetDialog = false },
            onConfirm = {
                viewModel.resetCharacter(characterId)
                showResetDialog = false
            }
        )
    }

    if (showDiceDialog) {
        DiceDialog(onDismiss = { showDiceDialog = false })
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
private fun DiceDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var result by remember { mutableStateOf<Int?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Бросить кубик") },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (result != null) {
                    Text(
                        text = "Результат: $result",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(Dimens.paddingLarge))
                }

                Button(
                    onClick = { result = (1..6).random() }
                ) {
                    Icon(
                        Icons.Default.Casino,
                        contentDescription = null,
                        modifier = Modifier.size(Dimens.iconSizeSmall)
                    )
                    Spacer(modifier = Modifier.width(Dimens.paddingMedium))
                    Text("Бросить")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Закрыть")
            }
        },
        modifier = modifier
    )
}
