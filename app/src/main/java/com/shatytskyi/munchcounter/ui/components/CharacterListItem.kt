package com.shatytskyi.munchcounter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.data.Character
import com.shatytskyi.munchcounter.ui.theme.Dimens
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

@Composable
fun CharacterListItem(
    character: Character,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onLevelChange: (Int) -> Unit = {},
    onItemsChange: (Int) -> Unit = {}
) {
    MunchkinCard(
        modifier = modifier
            .fillMaxWidth(),
        backgroundColor = MunchkinTheme.colors.surfaceContainer,
        elevation = 2.dp,
        shape = RoundedCornerShape(Dimens.paddingLarge),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.paddingLarge),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium)
        ) {
            // Character Name Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MunchkinTheme.colors.primaryContainer,
                        shape = RoundedCornerShape(Dimens.paddingMedium)
                    )
            ) {
                MunchkinText(
                    text = character.name,
                    style = MunchkinTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MunchkinTheme.colors.onPrimaryContainer,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(
                            horizontal = Dimens.paddingLarge,
                            vertical = Dimens.paddingMedium
                        )
                        .align(Alignment.Center)
                )
            }

            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.paddingMedium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatCard(
                    title = "Level",
                    value = character.lvl,
                    onDecrease = { onLevelChange(-1) },
                    onIncrease = { onLevelChange(+1) },
                    color = MunchkinTheme.colors.primary,
                    modifier = Modifier.weight(1f)
                )

                Box(
                    modifier = Modifier
                        .weight(0.6f)
                        .background(
                            color = MunchkinTheme.colors.secondaryContainer,
                            shape = RoundedCornerShape(Dimens.paddingLarge)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimens.paddingMedium),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        MunchkinText(
                            text = "Power",
                            style = MunchkinTheme.typography.labelMedium,
                            color = MunchkinTheme.colors.onSecondaryContainer
                        )

                        MunchkinText(
                            text = character.power.toString(),
                            style = MunchkinTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = MunchkinTheme.colors.onSecondaryContainer
                        )
                    }
                }

                StatCard(
                    title = "Items",
                    value = character.items,
                    onDecrease = { onItemsChange(-1) },
                    onIncrease = { onItemsChange(+1) },
                    color = MunchkinTheme.colors.tertiary,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: Int,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    color: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = MunchkinTheme.colors.surfaceContainerLow,
                shape = RoundedCornerShape(Dimens.paddingLarge)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.paddingMedium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MunchkinText(
                text = title,
                style = MunchkinTheme.typography.labelMedium,
                color = MunchkinTheme.colors.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(Dimens.paddingSmall))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                MunchkinIconButton(
                    onClick = onDecrease,
                    size = 32.dp,
                    colors = MunchkinIconButtonDefaults.iconButtonColors(
                        contentColor = color
                    )
                ) {
                    MunchkinIcon(
                        Icons.Default.Remove,
                        contentDescription = null,
                        tint = color
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MunchkinTheme.colors.surface,
                            shape = RoundedCornerShape(Dimens.paddingMedium)
                        ),
                ) {
                    MunchkinText(
                        text = value.toString(),
                        style = MunchkinTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = color,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(vertical = Dimens.paddingSmall)
                            .align(Alignment.Center)
                    )
                }

                MunchkinIconButton(
                    onClick = onIncrease,
                    size = 32.dp,
                    colors = MunchkinIconButtonDefaults.iconButtonColors(
                        contentColor = color
                    )
                ) {
                    MunchkinIcon(
                        Icons.Default.Add,
                        contentDescription = null,
                        tint = color
                    )
                }
            }
        }
    }
}