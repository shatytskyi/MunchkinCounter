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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
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
        color = MunchkinTheme.colors.primary,
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MunchkinText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                text = character.name,
                style = MunchkinTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MunchkinTheme.colors.onBackground,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                    modifier = Modifier.weight(0.6f)
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
                            color = MunchkinTheme.colors.onBackground
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        MunchkinText(
                            text = character.power.toString(),
                            style = MunchkinTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = MunchkinTheme.colors.onBackground
                        )
                    }
                }

                StatCard(
                    title = "Items",
                    value = character.items,
                    onDecrease = { onItemsChange(-1) },
                    onIncrease = { onItemsChange(+1) },
                    color = MunchkinTheme.colors.green,
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
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
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
                color = MunchkinTheme.colors.onBackground
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                MunchkinIconButton(
                    onClick = onDecrease,
                    size = 48.dp,
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
                            color = MunchkinTheme.colors.background,
                            shape = RoundedCornerShape(8.dp)
                        ),
                ) {
                    MunchkinText(
                        text = value.toString(),
                        style = MunchkinTheme.typography.titleLarge,
                        color = color,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }

                MunchkinIconButton(
                    onClick = onIncrease,
                    size = 48.dp,
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

@Preview(showBackground = true)
@Composable
fun CharacterListItemPreview() {
    MunchkinTheme {
        Box(
            modifier = Modifier
                .background(MunchkinTheme.colors.background)
                .padding(16.dp)
        ) {
            CharacterListItem(
                character = Character(
                    id = 1,
                    name = "Aragorn",
                    lvl = 99,
                    items = 99
                ),
                onClick = {},
                onLevelChange = {},
                onItemsChange = {}
            )
        }
    }
}
