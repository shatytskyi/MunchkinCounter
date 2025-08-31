package com.shatytskyi.munchcounter.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.data.Character
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
        borderColor = MunchkinTheme.colors.outline.copy(alpha = 0.3f),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Character Name Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MunchkinTheme.colors.primaryContainer,
                        shape = RoundedCornerShape(8.dp)
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
                            horizontal = 16.dp,
                            vertical = 8.dp
                        )
                        .align(Alignment.Center)
                )
            }

            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
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
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
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
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MunchkinText(
                text = title,
                style = MunchkinTheme.typography.labelMedium,
                color = MunchkinTheme.colors.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(4.dp))

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
                        tint = color
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MunchkinTheme.colors.surface,
                            shape = RoundedCornerShape(8.dp)
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
                            .padding(vertical = 4.dp)
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
                        tint = color
                    )
                }
            }
        }
    }
}
