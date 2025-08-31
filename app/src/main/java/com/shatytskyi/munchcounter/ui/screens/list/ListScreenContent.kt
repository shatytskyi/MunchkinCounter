package com.shatytskyi.munchcounter.ui.screens.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.PlaylistRemove
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.data.Character
import com.shatytskyi.munchcounter.ui.components.APP_BAR_HEIGHT
import com.shatytskyi.munchcounter.ui.components.CharacterListItem
import com.shatytskyi.munchcounter.ui.components.MunchkinIconTextButton
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

@Composable
fun ListScreenContent(
    characters: List<Character>,
    onAddCharacterClick: () -> Unit,
    onCharacterClick: (Long) -> Unit,
    onLevelChange: (Long, Int) -> Unit,
    onPowerChange: (Long, Int) -> Unit,
    onResetAllClick: () -> Unit,
    onRemoveAllClick: () -> Unit,
    onDiceClick: () -> Unit = {}
) {
    val density = LocalDensity.current
    val statusBarHeight = WindowInsets.systemBars.getTop(density)
    val topPadding = remember(statusBarHeight) {
        with(density) { statusBarHeight.toDp() + APP_BAR_HEIGHT.dp + 16.dp }
    }

    ListScreenTopBarWrapper(
        onDiceClick = onDiceClick
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
}

@Preview(
    name = "Character List",
    device = Devices.PIXEL_4,
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun CharacterListContentPreview() {
    val mockCharacters = listOf(
        Character(1, "Aragorn", 5, 8),
        Character(2, "Legolas", 3, 5),
        Character(3, "Gimli", 7, 12),
        Character(4, "Gandalf", 10, 15)
    )

    MunchkinTheme {
        ListScreenContent(
            characters = mockCharacters,
            onAddCharacterClick = {},
            onCharacterClick = {},
            onLevelChange = { _, _ -> },
            onPowerChange = { _, _ -> },
            onResetAllClick = {},
            onRemoveAllClick = {}
        )
    }
}
