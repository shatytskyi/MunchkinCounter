package com.shatytskyi.munchcounter.ui.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Casino
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.PersonRemove
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.data.Character
import com.shatytskyi.munchcounter.ui.components.APP_BAR_HEIGHT
import com.shatytskyi.munchcounter.ui.components.CharacterListItem
import com.shatytskyi.munchcounter.ui.components.MunchkinCard
import com.shatytskyi.munchcounter.ui.components.MunchkinIcon
import com.shatytskyi.munchcounter.ui.components.MunchkinIconButton
import com.shatytskyi.munchcounter.ui.components.MunchkinIconTextButton
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.components.MunchkinTopAppBar
import com.shatytskyi.munchcounter.ui.components.icons.MunchkinIcons
import com.shatytskyi.munchcounter.ui.components.icons.Swords
import com.shatytskyi.munchcounter.ui.dialogs.DiceDialog
import com.shatytskyi.munchcounter.ui.dialogs.EditCharacterDialog
import com.shatytskyi.munchcounter.ui.dialogs.WarningDialog
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme
import com.shatytskyi.munchcounter.viewmodel.CommonViewModel

@Composable
fun DetailsScreen(
    viewModel: CommonViewModel,
    characterId: Long,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onFight: () -> Unit = {}
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
        onFightClick = onFight,
        onResetClick = { showResetDialog = true },
        onEditClick = { showEditDialog = true },
        onDeleteClick = { showDeleteDialog = true },
        onBackClick = onBack,
        onDiceClick = { showDiceDialog = true },
        modifier = modifier
    )

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
            title = "Reset ${character.name}?",
            message = "Player will be reset to level 1 with 0 items",
            onDismiss = { showResetDialog = false },
            onConfirm = {
                viewModel.resetCharacter(characterId)
                showResetDialog = false
            }
        )
    }

    if (showDeleteDialog) {
        WarningDialog(
            title = "Delete ${character.name}?",
            message = "Player will be permanently deleted",
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

@Composable
private fun DetailsScreenContent(
    character: Character,
    topPadding: Dp,
    onLevelChange: (Int) -> Unit,
    onPowerChange: (Int) -> Unit,
    onFightClick: () -> Unit,
    onResetClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit,
    onDiceClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = topPadding,
                bottom = 100.dp
            )
        ) {
            item {
                CharacterListItem(
                    character = character,
                    hideName = true,
                    onLevelChange = onLevelChange,
                    onItemsChange = onPowerChange
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    MunchkinIconTextButton(
                        onClick = onFightClick,
                        icon = MunchkinIcons.Swords,
                        text = "Fight!",
                        modifier = Modifier.fillMaxWidth(0.6f),
                        textStyle = MunchkinTheme.typography.labelLarge,
                        contentPadding = 24.dp,
                        rippleColor = MunchkinTheme.colors.red,
                        bounded = false
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                PowerControlWidget(
                    onPowerChange = onPowerChange
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MunchkinIconTextButton(
                        onClick = onResetClick,
                        icon = Icons.Outlined.Refresh,
                        text = "Reset Player",
                        modifier = Modifier.weight(1f),
                        textStyle = MunchkinTheme.typography.labelMedium,
                        contentPadding = 24.dp,
                        rippleColor = MunchkinTheme.colors.secondary,
                        bounded = false
                    )

                    MunchkinIconTextButton(
                        onClick = onEditClick,
                        icon = Icons.Outlined.Edit,
                        text = "Edit Player",
                        modifier = Modifier.weight(1f),
                        textStyle = MunchkinTheme.typography.labelMedium,
                        contentPadding = 24.dp,
                        rippleColor = MunchkinTheme.colors.primary,
                        bounded = false
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    MunchkinIconTextButton(
                        onClick = onDeleteClick,
                        icon = Icons.Outlined.PersonRemove,
                        text = "Delete Player",
                        modifier = Modifier.fillMaxWidth(0.7f),
                        textStyle = MunchkinTheme.typography.labelMedium,
                        contentPadding = 24.dp,
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
                actions = {
                    MunchkinIconButton(onClick = onDiceClick) {
                        MunchkinIcon(
                            imageVector = Icons.Outlined.Casino,
                            tint = MunchkinTheme.colors.onBackground
                        )
                    }
                }
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
                text = "Items Control",
                style = MunchkinTheme.typography.titleMedium,
                color = MunchkinTheme.colors.onBackground
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
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
        color = if (isNegative) MunchkinTheme.colors.secondary else MunchkinTheme.colors.primary,
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
@Composable
private fun DetailsScreenPreview() {
    val mockCharacter = Character(1, "Aragorn", 5, 8)

    MunchkinTheme {
        DetailsScreenContent(
            character = mockCharacter,
            topPadding = 100.dp,
            onLevelChange = {},
            onPowerChange = {},
            onFightClick = {},
            onResetClick = {},
            onEditClick = {},
            onDeleteClick = {},
            onBackClick = {},
            onDiceClick = {}
        )
    }
}
