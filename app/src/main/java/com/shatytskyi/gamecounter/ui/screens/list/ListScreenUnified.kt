package com.shatytskyi.gamecounter.ui.screens.list

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shatytskyi.gamecounter.R
import com.shatytskyi.gamecounter.data.Character
import com.shatytskyi.gamecounter.data.Gender
import com.shatytskyi.gamecounter.ui.components.APP_BAR_HEIGHT
import com.shatytskyi.gamecounter.ui.components.CharacterListItem
import com.shatytskyi.gamecounter.ui.components.MunchkinHorizontalDivider
import com.shatytskyi.gamecounter.ui.components.MunchkinIconTextButton
import com.shatytskyi.gamecounter.ui.components.MunchkinText
import com.shatytskyi.gamecounter.ui.components.icons.Add
import com.shatytskyi.gamecounter.ui.components.icons.MunchkinIcons
import com.shatytskyi.gamecounter.ui.components.icons.RemoveAll
import com.shatytskyi.gamecounter.ui.components.icons.Reset
import com.shatytskyi.gamecounter.ui.theme.MunchkinTheme

@OptIn(ExperimentalFoundationApi::class, ExperimentalSharedTransitionApi::class)
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
    onTimerClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope
) {
    val density = LocalDensity.current
    val statusBarHeight = WindowInsets.systemBars.getTop(density)
    val topPadding = remember(statusBarHeight) {
        with(density) { statusBarHeight.toDp() + APP_BAR_HEIGHT.dp + 16.dp }
    }

    val isEmpty = characters.isEmpty()

    ListScreenTopBarWrapper(
        onDiceClick = onDiceClick,
        onTimerClick = onTimerClick,
        onSettingsClick = onSettingsClick,
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
                bottom = 32.dp
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
                            style = MunchkinTheme.typography.headlineMedium,
                            color = MunchkinTheme.colors.onBackground,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        MunchkinText(
                            text = stringResource(R.string.no_players_description),
                            style = MunchkinTheme.typography.bodyMedium,
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
                        },
                        animatedContentScope = animatedContentScope,
                        sharedTransitionScope = sharedTransitionScope
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Bottom action buttons
            item(
                key = "bottom-actions",
                contentType = "bottom_actions"
            ) {
                Column(
                    modifier = Modifier
                        .animateItem()
                        .navigationBarsPadding()
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    MunchkinHorizontalDivider()

                    Spacer(modifier = Modifier.height(8.dp))

                    if (isEmpty) {
                        // Only Add button when no characters
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            MunchkinIconTextButton(
                                onClick = onAddCharacterClick,
                                icon = MunchkinIcons.Add,
                                text = stringResource(R.string.add),
                                modifier = Modifier.fillMaxWidth(0.5f),
                                textStyle = MunchkinTheme.typography.labelMedium,
                                contentPadding = 16.dp,
                                rippleColor = MunchkinTheme.colors.primary,
                                bounded = false
                            )
                        }
                    } else {
                        // All three buttons when characters exist
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Reset button
                            MunchkinIconTextButton(
                                onClick = onResetAllClick,
                                icon = MunchkinIcons.Reset,
                                text = stringResource(R.string.reset),
                                modifier = Modifier.weight(1f),
                                textStyle = MunchkinTheme.typography.labelMedium,
                                contentPadding = 16.dp,
                                rippleColor = MunchkinTheme.colors.secondary,
                                bounded = false
                            )

                            // Add button (slightly larger)
                            MunchkinIconTextButton(
                                onClick = onAddCharacterClick,
                                icon = MunchkinIcons.Add,
                                text = stringResource(R.string.add),
                                modifier = Modifier.weight(1.3f),
                                textStyle = MunchkinTheme.typography.labelMedium,
                                contentPadding = 16.dp,
                                rippleColor = MunchkinTheme.colors.primary,
                                bounded = false
                            )

                            // Clear button
                            MunchkinIconTextButton(
                                onClick = onRemoveAllClick,
                                icon = MunchkinIcons.RemoveAll,
                                text = stringResource(R.string.clear),
                                modifier = Modifier.weight(1f),
                                textStyle = MunchkinTheme.typography.labelMedium,
                                contentPadding = 16.dp,
                                rippleColor = MunchkinTheme.colors.red,
                                bounded = false
                            )
                        }
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
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ListScreenUnifiedEmptyPreview() {
    MunchkinTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            MunchkinText(
                text = "Empty State Preview",
                style = MunchkinTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(
    name = "With Characters",
    device = Devices.PIXEL_4,
    showSystemUi = true,
    showBackground = true
)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ListScreenUnifiedWithCharactersPreview() {
    val mockCharacters = listOf(
        Character(1, "Aragorn", 5, 8, Gender.MALE),
        Character(2, "Legolas", 3, 5, Gender.MALE),
        Character(3, "Gimli", 7, 12, Gender.MALE)
    )

    MunchkinTheme {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(mockCharacters.size) { index ->
                CharacterListItem(
                    character = mockCharacters[index],
                    modifier = Modifier.padding(vertical = 8.dp),
                    animatedContentScope = null,
                    sharedTransitionScope = null
                )
            }
        }
    }
}
