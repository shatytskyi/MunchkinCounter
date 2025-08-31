package com.shatytskyi.munchcounter.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.data.Character
import com.shatytskyi.munchcounter.ui.components.APP_BAR_HEIGHT
import com.shatytskyi.munchcounter.ui.components.CharacterListItem
import com.shatytskyi.munchcounter.ui.components.MunchkinCard
import com.shatytskyi.munchcounter.ui.components.MunchkinIcon
import com.shatytskyi.munchcounter.ui.components.MunchkinIconButton
import com.shatytskyi.munchcounter.ui.components.MunchkinIconTextButton
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.components.MunchkinTopAppBar
import com.shatytskyi.munchcounter.ui.dialogs.AddCharacterDialog
import com.shatytskyi.munchcounter.ui.dialogs.DiceDialog
import com.shatytskyi.munchcounter.ui.dialogs.WarningDialog
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme
import com.shatytskyi.munchcounter.viewmodel.CommonViewModel

@Composable
fun ListScreen(
    viewModel: CommonViewModel,
    onCharacterClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val characters by viewModel.characters.collectAsState()

    ListScreenContent(
        characters = characters,
        onCharacterClick = onCharacterClick,
        onAddCharacter = { name ->
            viewModel.addCharacter(name)
        },
        onLevelChange = { characterId, delta ->
            viewModel.changeLevel(characterId, delta)
        },
        onPowerChange = { characterId, delta ->
            viewModel.changePower(characterId, delta)
        },
        onResetAll = {
            viewModel.resetAllCharacters()
        },
        onRemoveAll = {
            viewModel.removeAllCharacters()
        },
        modifier = modifier
    )
}

@Composable
private fun ListScreenContent(
    characters: List<Character>,
    onCharacterClick: (Long) -> Unit,
    onAddCharacter: (String) -> Unit,
    onLevelChange: (Long, Int) -> Unit,
    onPowerChange: (Long, Int) -> Unit,
    onResetAll: () -> Unit,
    onRemoveAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var showResetAllDialog by remember { mutableStateOf(false) }
    var showRemoveAllDialog by remember { mutableStateOf(false) }
    var showDiceDialog by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
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
                    onLevelChange = onLevelChange,
                    onPowerChange = onPowerChange,
                    onResetAllClick = {
                        showResetAllDialog = true
                    },
                    onRemoveAllClick = {
                        showRemoveAllDialog = true
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MunchkinTheme.colors.background.copy(alpha = 0.95f))
        ) {
            MunchkinTopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top)),
                title = stringResource(R.string.app_name),
                actions = {
                    MunchkinIconButton(onClick = { showDiceDialog = true }) {
                        MunchkinIcon(
                            imageVector = Icons.Outlined.Casino,
                            tint = MunchkinTheme.colors.onBackground
                        )
                    }
                }
            )
        }
    }

    // Dialogs
    if (showAddDialog) {
        AddCharacterDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name ->
                onAddCharacter(name)
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
                onResetAll()
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
                onRemoveAll()
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
    val density = LocalDensity.current
    val statusBarHeight = WindowInsets.systemBars.getTop(density)
    val topPadding = remember(statusBarHeight) {
        with(density) { statusBarHeight.toDp() + APP_BAR_HEIGHT.dp + 40.dp }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars.only(WindowInsetsSides.Bottom))
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
            .padding(top = topPadding, start = 24.dp, end = 24.dp, bottom = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.pic_knight),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
                Image(
                    painter = painterResource(R.drawable.pic_knight_fem),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
            }

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

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.pic_witch),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
                Image(
                    painter = painterResource(R.drawable.pic_wizard),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            MunchkinIconTextButton(
                onClick = onAddCharacterClick,
                icon = Icons.Default.Add,
                text = "Add Player",
                modifier = Modifier.fillMaxWidth(0.6f),
                textStyle = MunchkinTheme.typography.labelLarge,
                contentPadding = 24.dp,
                rippleColor = MunchkinTheme.colors.primary,
                bounded = false
            )
        }
    }
}

@Composable
private fun CharacterListContent(
    characters: List<Character>,
    onAddCharacterClick: () -> Unit,
    onCharacterClick: (Long) -> Unit,
    onLevelChange: (Long, Int) -> Unit,
    onPowerChange: (Long, Int) -> Unit,
    onResetAllClick: () -> Unit,
    onRemoveAllClick: () -> Unit
) {
    val density = LocalDensity.current
    val statusBarHeight = WindowInsets.systemBars.getTop(density)
    val topPadding = remember(statusBarHeight) {
        with(density) { statusBarHeight.toDp() + APP_BAR_HEIGHT.dp + 16.dp }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = topPadding,
            bottom = 100.dp
        )
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

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                MunchkinIconTextButton(
                    onClick = onAddCharacterClick,
                    icon = Icons.Default.Add,
                    text = "Add Player",
                    modifier = Modifier.fillMaxWidth(0.5f),
                    textStyle = MunchkinTheme.typography.labelLarge,
                    contentPadding = 24.dp,
                    rippleColor = MunchkinTheme.colors.primary,
                    bounded = false
                )
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MunchkinIconTextButton(
                    onClick = onResetAllClick,
                    icon = Icons.Outlined.Refresh,
                    text = "Reset All",
                    modifier = Modifier.weight(1f),
                    textStyle = MunchkinTheme.typography.labelMedium,
                    contentPadding = 24.dp,
                    rippleColor = MunchkinTheme.colors.secondary,
                    bounded = false
                )

                MunchkinIconTextButton(
                    onClick = onRemoveAllClick,
                    icon = Icons.Outlined.PlaylistRemove,
                    text = "Remove All",
                    modifier = Modifier.weight(1f),
                    textStyle = MunchkinTheme.typography.labelMedium,
                    contentPadding = 24.dp,
                    rippleColor = MunchkinTheme.colors.red,
                    bounded = false
                )
            }
        }

    }
}

@Preview(
    name = "Large Phone",
    device = Devices.PIXEL_7_PRO,
    showSystemUi = true,
    showBackground = true
)
@Preview(
    name = "Medium Phone",
    device = Devices.PIXEL_4,
    showSystemUi = true,
    showBackground = true
)
@Preview(
    name = "Small Phone",
    device = "spec:width=360dp,height=640dp,dpi=320",
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun ListScreenPreview() {
    val mockCharacters = listOf(
        Character(1, "Aragorn", 5, 8),
        Character(2, "Legolas", 3, 5),
        Character(3, "Gimli", 7, 12),
        Character(4, "Gandalf", 10, 15)
    )

    MunchkinTheme {
        ListScreenContent(
            characters = mockCharacters,
            onCharacterClick = {},
            onAddCharacter = {},
            onLevelChange = { _, _ -> },
            onPowerChange = { _, _ -> },
            onResetAll = {},
            onRemoveAll = {}
        )
    }
}

@Preview(
    name = "Large Phone",
    device = Devices.PIXEL_7_PRO,
    showSystemUi = true,
    showBackground = true
)
@Preview(
    name = "Medium Phone",
    device = Devices.PIXEL_4,
    showSystemUi = true,
    showBackground = true
)
@Preview(
    name = "Small Phone",
    device = "spec:width=360dp,height=640dp,dpi=320",
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun ListScreenEmptyPreview() {
    MunchkinTheme {
        ListScreenContent(
            characters = emptyList(),
            onCharacterClick = {},
            onAddCharacter = {},
            onLevelChange = { _, _ -> },
            onPowerChange = { _, _ -> },
            onResetAll = {},
            onRemoveAll = {}
        )
    }
}
