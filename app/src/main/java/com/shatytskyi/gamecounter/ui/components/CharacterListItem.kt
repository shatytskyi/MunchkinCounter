package com.shatytskyi.gamecounter.ui.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Female
import androidx.compose.material.icons.outlined.Male
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shatytskyi.gamecounter.R
import com.shatytskyi.gamecounter.data.Character
import com.shatytskyi.gamecounter.data.Gender
import com.shatytskyi.gamecounter.ui.theme.MunchkinTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CharacterListItem(
    character: Character,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    hideName: Boolean = false,
    showLevelButtons: Boolean = true,
    showItemsButtons: Boolean = true,
    onLevelChange: (Int) -> Unit = {},
    onItemsChange: (Int) -> Unit = {},
    onGenderToggle: () -> Unit = {},
    animatedContentScope: AnimatedContentScope? = null,
    sharedTransitionScope: SharedTransitionScope? = null
) {
    val haptic = LocalHapticFeedback.current
    
    val cardModifier = if (sharedTransitionScope != null && animatedContentScope != null) {
        with(sharedTransitionScope) {
            modifier
                .fillMaxWidth()
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(key = "character-card-${character.id}"),
                    animatedVisibilityScope = animatedContentScope
                )
        }
    } else {
        modifier.fillMaxWidth()
    }
    
    MunchkinCard(
        modifier = cardModifier,
        color = MunchkinTheme.colors.grey,
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                if (!hideName) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        MunchkinText(
                            modifier = Modifier.padding(horizontal = 32.dp),
                            text = character.name,
                            style = MunchkinTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = MunchkinTheme.colors.onBackground,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .munchkinClickable(
                                    onClick = {
                                        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                        onGenderToggle()
                                    },
                                    bounded = false,
                                    rippleColor = when (character.gender) {
                                        Gender.MALE -> MunchkinTheme.colors.primary
                                        Gender.FEMALE -> MunchkinTheme.colors.secondary
                                    }
                                )
                        ) {
                            MunchkinIcon(
                                imageVector = when (character.gender) {
                                    Gender.MALE -> Icons.Outlined.Male
                                    Gender.FEMALE -> Icons.Outlined.Female
                                },
                                tint = when (character.gender) {
                                    Gender.MALE -> MunchkinTheme.colors.primary
                                    Gender.FEMALE -> MunchkinTheme.colors.secondary
                                },
                                size = 24.dp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatItem(
                        title = stringResource(R.string.level),
                        value = character.level,
                        onDecrease = { onLevelChange(-1) },
                        onIncrease = { onLevelChange(+1) },
                        color = MunchkinTheme.colors.primary,
                        modifier = Modifier.weight(1f),
                        showButtons = showLevelButtons,
                        sharedElementKey = "character-level-${character.id}",
                        titleSharedElementKey = "character-level-title-${character.id}",
                        animatedContentScope = animatedContentScope,
                        sharedTransitionScope = sharedTransitionScope
                    )

                    StatItem(
                        title = stringResource(R.string.power),
                        value = character.power,
                        onDecrease = {},
                        onIncrease = {},
                        color = MunchkinTheme.colors.secondary,
                        modifier = Modifier.weight(1f),
                        showButtons = false,
                        sharedElementKey = "character-power-${character.id}",
                        titleSharedElementKey = "character-power-title-${character.id}",
                        animatedContentScope = animatedContentScope,
                        sharedTransitionScope = sharedTransitionScope
                    )

                    StatItem(
                        title = stringResource(R.string.items),
                        value = character.items,
                        onDecrease = { onItemsChange(-1) },
                        onIncrease = { onItemsChange(+1) },
                        color = MunchkinTheme.colors.primary,
                        modifier = Modifier.weight(1f),
                        showButtons = showItemsButtons,
                        sharedElementKey = "character-items-${character.id}",
                        titleSharedElementKey = "character-items-title-${character.id}",
                        animatedContentScope = animatedContentScope,
                        sharedTransitionScope = sharedTransitionScope
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun StatItem(
    title: String,
    value: Int,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    color: Color,
    modifier: Modifier = Modifier,
    showButtons: Boolean = true,
    sharedElementKey: String? = null,
    titleSharedElementKey: String? = null,
    animatedContentScope: AnimatedContentScope? = null,
    sharedTransitionScope: SharedTransitionScope? = null
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val titleModifier = if (sharedTransitionScope != null && animatedContentScope != null && titleSharedElementKey != null) {
            with(sharedTransitionScope) {
                Modifier.sharedElement(
                    sharedContentState = rememberSharedContentState(key = titleSharedElementKey),
                    animatedVisibilityScope = animatedContentScope
                )
            }
        } else {
            Modifier
        }
        
        MunchkinText(
            modifier = titleModifier,
            text = title,
            style = MunchkinTheme.typography.labelMedium,
            color = MunchkinTheme.colors.onBackground,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(8.dp))

        val boxModifier = if (sharedTransitionScope != null && animatedContentScope != null && sharedElementKey != null) {
            with(sharedTransitionScope) {
                Modifier
                    .weight(1f)
                    .background(
                        color = MunchkinTheme.colors.background,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(key = sharedElementKey),
                        animatedVisibilityScope = animatedContentScope
                    )
            }
        } else {
            Modifier
                .weight(1f)
                .background(
                    color = MunchkinTheme.colors.background,
                    shape = RoundedCornerShape(8.dp)
                )
        }
        
        Box(
            modifier = boxModifier,
            contentAlignment = Alignment.Center
        ) {
            if (showButtons) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    MunchkinIconButton(
                        onClick = onDecrease,
                        size = 24.dp,
                        colors = MunchkinIconButtonDefaults.iconButtonColors(
                            contentColor = color
                        )
                    ) {
                        MunchkinIcon(
                            Icons.Default.Remove,
                            size = 24.dp,
                            tint = color
                        )
                    }

                    AnimatedNumber(
                        modifier = Modifier.weight(1f),
                        value = value,
                        style = MunchkinTheme.typography.displayMedium,
                        color = color
                    )

                    MunchkinIconButton(
                        onClick = onIncrease,
                        size = 24.dp,
                        colors = MunchkinIconButtonDefaults.iconButtonColors(
                            contentColor = color
                        )
                    ) {
                        MunchkinIcon(
                            Icons.Default.Add,
                            size = 24.dp,
                            tint = color
                        )
                    }
                }
            } else {
                AnimatedNumber(
                    modifier = Modifier.fillMaxWidth(),
                    value = value,
                    style = MunchkinTheme.typography.displayMedium,
                    color = color
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Preview
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CharacterListItemPreview() {
    MunchkinTheme {
        Box(
            modifier = Modifier
                .padding(16.dp)
        ) {
            CharacterListItem(
                character = Character(
                    id = 1,
                    name = "Aragorn Son of Arathorn the King of Gondor",
                    level = 5,
                    items = 15
                ),
                onClick = {},
                onLevelChange = {},
                onItemsChange = {},
                animatedContentScope = null,
                sharedTransitionScope = null
            )
        }
    }
}
