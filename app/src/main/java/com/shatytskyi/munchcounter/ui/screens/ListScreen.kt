package com.shatytskyi.munchcounter.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.outlined.Casino
import androidx.compose.material.icons.outlined.PlaylistRemove
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.runtime.Composable
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
import com.shatytskyi.munchcounter.ui.components.AddCharacterDialog
import com.shatytskyi.munchcounter.ui.components.CharacterListItem
import com.shatytskyi.munchcounter.ui.components.MunchkinDialog
import com.shatytskyi.munchcounter.ui.components.MunchkinIcon
import com.shatytskyi.munchcounter.ui.components.MunchkinIconButton
import com.shatytskyi.munchcounter.ui.components.MunchkinOutlinedButton
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.components.MunchkinTextButton
import com.shatytskyi.munchcounter.ui.components.MunchkinTopAppBar
import com.shatytskyi.munchcounter.ui.components.WarningDialog
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme
import com.shatytskyi.munchcounter.viewmodel.CommonViewModel

@Composable
fun ListScreen(
    viewModel: CommonViewModel,
    onCharacterClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val characters by viewModel.characters.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    var showResetAllDialog by remember { mutableStateOf(false) }
    var showRemoveAllDialog by remember { mutableStateOf(false) }
    var showDiceDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        MunchkinTopAppBar(
            title = stringResource(R.string.app_name),
            actions = {
                MunchkinIconButton(
                    onClick = {
                        if (characters.isNotEmpty()) {
                            showResetAllDialog = true
                        }
                    }
                ) {
                    MunchkinIcon(
                        imageVector = Icons.Outlined.Refresh,
                        tint = MunchkinTheme.colors.onBackground
                    )
                }

                MunchkinIconButton(
                    onClick = {
                        if (characters.isNotEmpty()) {
                            showRemoveAllDialog = true
                        }
                    }
                ) {
                    MunchkinIcon(
                        imageVector = Icons.Outlined.PlaylistRemove,
                        tint = MunchkinTheme.colors.onBackground
                    )
                }

                MunchkinIconButton(onClick = { showDiceDialog = true }) {
                    MunchkinIcon(
                        imageVector = Icons.Outlined.Casino,
                        tint = MunchkinTheme.colors.onBackground
                    )
                }
            }
        )

        when {
            characters.isEmpty() -> {
                EmptyStateContent(
                    onAddCharacterClick = {
                        showAddDialog = true
                    }
                )
            }

            else -> {
                CharacterListContent(
                    characters = characters,
                    onCharacterClick = onCharacterClick,
                    onAddCharacterClick = {
                        showAddDialog = true
                    },
                    onLevelChange = { characterId, delta ->
                        viewModel.changeLevel(characterId, delta)
                    },
                    onPowerChange = { characterId, delta ->
                        viewModel.changePower(characterId, delta)
                    }
                )
            }
        }
    }

    // Dialogs
    if (showAddDialog) {
        AddCharacterDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name ->
                viewModel.addCharacter(name)
                showAddDialog = false
            }
        )
    }

    if (showResetAllDialog) {
        WarningDialog(
            title = "Reset All Players?",
            message = "All players will be reset to level 1 with 0 power",
            onDismiss = { showResetAllDialog = false },
            onConfirm = {
                viewModel.resetAllCharacters()
                showResetAllDialog = false
            }
        )
    }

    if (showRemoveAllDialog) {
        WarningDialog(
            title = "Delete All Players?",
            message = "All players will be permanently deleted",
            onDismiss = { showRemoveAllDialog = false },
            onConfirm = {
                viewModel.removeAllCharacters()
                showRemoveAllDialog = false
            }
        )
    }

    if (showDiceDialog) {
        DiceDialog(
            onDismiss = { showDiceDialog = false }
        )
    }
}

@Composable
private fun EmptyStateContent(
    onAddCharacterClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars.only(WindowInsetsSides.Bottom))
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MunchkinText(
                text = "No Players Yet",
                style = MunchkinTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MunchkinTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )

            MunchkinText(
                text = "Add your first player to get started with Munchkin!",
                style = MunchkinTheme.typography.bodyLarge,
                color = MunchkinTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            MunchkinOutlinedButton(
                onClick = onAddCharacterClick,
                containerColor = MunchkinTheme.colors.primary,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MunchkinIcon(
                        Icons.Default.Add,
                        size = 24.dp,
                        tint = MunchkinTheme.colors.onBackground
                    )
                    MunchkinText(
                        text = "Add Player",
                        style = MunchkinTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = MunchkinTheme.colors.onBackground
                    )
                }
            }
        }
    }
}

@Composable
private fun CharacterListContent(
    characters: List<Character>,
    onAddCharacterClick: () -> Unit,
    onCharacterClick: (Long) -> Unit,
    onLevelChange: (Long, Int) -> Unit,
    onPowerChange: (Long, Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 8.dp,
            bottom = 100.dp // Space for FAB
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(
            items = characters,
            key = { _, character -> character.id }
        ) { _, character ->
            CharacterListItem(
                character = character,
                onClick = { onCharacterClick(character.id) },
                onLevelChange = { delta ->
                    onLevelChange(character.id, delta)
                },
                onItemsChange = { delta ->
                    onPowerChange(character.id, delta)
                }
            )
        }

        item {
            MunchkinOutlinedButton(
                onClick = onAddCharacterClick,
                containerColor = MunchkinTheme.colors.primary,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MunchkinIcon(
                        Icons.Default.Add,
                        size = 24.dp,
                        tint = MunchkinTheme.colors.onBackground
                    )
                    MunchkinText(
                        text = "Add Player",
                        style = MunchkinTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = MunchkinTheme.colors.onBackground
                    )
                }
            }
        }
    }
}

@Composable
private fun DiceDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var result by remember { mutableStateOf<Int?>(null) }

    MunchkinDialog(
        onDismissRequest = onDismiss,
        title = "Roll Dice",
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AnimatedVisibility(
                    visible = result != null,
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    result?.let { diceResult ->
                        MunchkinText(
                            text = "Result: $diceResult",
                            style = MunchkinTheme.typography.displayMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = MunchkinTheme.colors.primary
                        )
                    }
                }

                MunchkinOutlinedButton(
                    onClick = { result = (1..6).random() },
                    containerColor = MunchkinTheme.colors.primary,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        MunchkinIcon(
                            Icons.Default.Casino,
                            size = 24.dp,
                            tint = MunchkinTheme.colors.onBackground
                        )
                        MunchkinText(
                            text = "Roll",
                            style = MunchkinTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = MunchkinTheme.colors.onBackground
                        )
                    }
                }
            }
        },
        confirmButton = {
            MunchkinTextButton(
                onClick = onDismiss,
                text = "Close"
            )
        },
        modifier = modifier
    )
}
