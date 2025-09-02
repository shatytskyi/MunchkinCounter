package com.shatytskyi.munchcounter.ui.screens.list

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.PlaylistRemove
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
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
import com.shatytskyi.munchcounter.data.Gender
import com.shatytskyi.munchcounter.ui.components.APP_BAR_HEIGHT
import com.shatytskyi.munchcounter.ui.components.CharacterListItem
import com.shatytskyi.munchcounter.ui.components.MunchkinIconTextButton
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListScreenUnified(
    characters: List<Character>,
    onAddCharacterClick: () -> Unit,
    onCharacterClick: (Long) -> Unit,
    onLevelChange: (Long, Int) -> Unit,
    onPowerChange: (Long, Int) -> Unit,
    onGenderToggle: (Long) -> Unit,
    onResetAllClick: () -> Unit,
    onRemoveAllClick: () -> Unit,
    onDiceClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    val density = LocalDensity.current
    val statusBarHeight = WindowInsets.systemBars.getTop(density)
    val topPadding = remember(statusBarHeight) {
        with(density) { statusBarHeight.toDp() + APP_BAR_HEIGHT.dp + 16.dp }
    }

    val isEmpty = characters.isEmpty()

    ListScreenTopBarWrapper(
        onDiceClick = onDiceClick
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = topPadding,
                bottom = 100.dp
            )
        ) {
            // Empty state content
            if (isEmpty) {
                item(
                    key = "empty-heroes-top",
                    contentType = "empty_state"
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItem()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(R.drawable.pic_knight),
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                colorFilter = ColorFilter.tint(MunchkinTheme.colors.onBackground)
                            )

                            Spacer(modifier = Modifier.width(24.dp))

                            Image(
                                painter = painterResource(R.drawable.pic_knight_fem),
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                colorFilter = ColorFilter.tint(MunchkinTheme.colors.onBackground)
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        MunchkinText(
                            text = stringResource(R.string.no_players),
                            style = MunchkinTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = MunchkinTheme.colors.onBackground,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        MunchkinText(
                            text = stringResource(R.string.no_players_description),
                            style = MunchkinTheme.typography.bodyLarge,
                            color = MunchkinTheme.colors.onBackground,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(R.drawable.pic_witch),
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                colorFilter = ColorFilter.tint(MunchkinTheme.colors.onBackground)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Image(
                                painter = painterResource(R.drawable.pic_wizard),
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                colorFilter = ColorFilter.tint(MunchkinTheme.colors.onBackground)
                            )
                        }

                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }

            // Character items
            itemsIndexed(
                items = characters,
                key = { _, character -> character.id },
                contentType = { _, _ -> "character" }
            ) { _, character ->
                Column(
                    modifier = Modifier.animateItem()
                ) {
                    CharacterListItem(
                        character = character,
                        onClick = { onCharacterClick(character.id) },
                        onLevelChange = { delta ->
                            onLevelChange(character.id, delta)
                        },
                        onItemsChange = { delta ->
                            onPowerChange(character.id, delta)
                        },
                        onGenderToggle = {
                            onGenderToggle(character.id)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Divider and Add Player button
            item(
                key = "divider-add",
                contentType = "divider_add"
            ) {
                Column(
                    modifier = Modifier.animateItem()
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    HorizontalDivider()

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        MunchkinIconTextButton(
                            onClick = onAddCharacterClick,
                            icon = Icons.Default.Add,
                            text = stringResource(R.string.add_player),
                            modifier = Modifier.fillMaxWidth(0.5f),
                            textStyle = MunchkinTheme.typography.labelLarge,
                            contentPadding = 16.dp,
                            rippleColor = MunchkinTheme.colors.primary,
                            bounded = false
                        )
                    }
                }
            }

            // Reset and Remove buttons - only shown when there are characters
            if (!isEmpty) {
                item(
                    key = "action-buttons",
                    contentType = "action_buttons"
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItem(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MunchkinIconTextButton(
                            onClick = onResetAllClick,
                            icon = Icons.Outlined.Refresh,
                            text = stringResource(R.string.reset_all),
                            modifier = Modifier.weight(1f),
                            textStyle = MunchkinTheme.typography.labelMedium,
                            contentPadding = 16.dp,
                            rippleColor = MunchkinTheme.colors.secondary,
                            bounded = false
                        )

                        MunchkinIconTextButton(
                            onClick = onRemoveAllClick,
                            icon = Icons.Outlined.PlaylistRemove,
                            text = stringResource(R.string.remove_all),
                            modifier = Modifier.weight(1f),
                            textStyle = MunchkinTheme.typography.labelMedium,
                            contentPadding = 16.dp,
                            rippleColor = MunchkinTheme.colors.red,
                            bounded = false
                        )
                    }
                }
            }

            // Settings button
            item(
                key = "settings",
                contentType = "settings"
            ) {
                Column(
                    modifier = Modifier.animateItem()
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        MunchkinIconTextButton(
                            onClick = onSettingsClick,
                            icon = Icons.Outlined.Settings,
                            text = stringResource(R.string.settings),
                            modifier = Modifier.fillMaxWidth(0.6f),
                            textStyle = MunchkinTheme.typography.labelMedium,
                            contentPadding = 20.dp,
                            rippleColor = MunchkinTheme.colors.grey,
                            bounded = false
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    name = "Empty State",
    device = Devices.PIXEL_4,
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun ListScreenUnifiedEmptyPreview() {
    MunchkinTheme {
        ListScreenUnified(
            characters = emptyList(),
            onAddCharacterClick = {},
            onCharacterClick = {},
            onLevelChange = { _, _ -> },
            onPowerChange = { _, _ -> },
            onGenderToggle = {},
            onResetAllClick = {},
            onRemoveAllClick = {}
        )
    }
}

@Preview(
    name = "With Characters",
    device = Devices.PIXEL_4,
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun ListScreenUnifiedWithCharactersPreview() {
    val mockCharacters = listOf(
        Character(1, "Aragorn", 5, 8, Gender.MALE),
        Character(2, "Legolas", 3, 5, Gender.MALE),
        Character(3, "Gimli", 7, 12, Gender.MALE)
    )

    MunchkinTheme {
        ListScreenUnified(
            characters = mockCharacters,
            onAddCharacterClick = {},
            onCharacterClick = {},
            onLevelChange = { _, _ -> },
            onPowerChange = { _, _ -> },
            onGenderToggle = {},
            onResetAllClick = {},
            onRemoveAllClick = {}
        )
    }
}
