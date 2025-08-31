package com.shatytskyi.munchcounter.ui.components

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
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    hideName: Boolean = false,
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
                .height(140.dp)
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
                        text = character.name,
                        style = MunchkinTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MunchkinTheme.colors.onBackground,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
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
                    title = "Level",
                    value = character.lvl,
                    onDecrease = { onLevelChange(-1) },
                    onIncrease = { onLevelChange(+1) },
                    color = MunchkinTheme.colors.primary,
                    modifier = Modifier.weight(1f)
                )

                StatItem(
                    title = "Power",
                    value = character.power,
                    onDecrease = {},
                    onIncrease = {},
                    color = MunchkinTheme.colors.secondary,
                    modifier = Modifier.weight(1f),
                    showButtons = false
                )

                StatItem(
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
private fun StatItem(
    title: String,
    value: Int,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    color: Color,
    modifier: Modifier = Modifier,
    showButtons: Boolean = true
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        MunchkinText(
            text = title,
            style = MunchkinTheme.typography.labelMedium,
            color = MunchkinTheme.colors.onBackground,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = MunchkinTheme.colors.background,
                    shape = RoundedCornerShape(8.dp)
                ),
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

                    MunchkinText(
                        text = value.toString(),
                        style = MunchkinTheme.typography.displayLarge,
                        color = color,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f),
                        maxLines = 1
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
                MunchkinText(
                    text = value.toString(),
                    style = MunchkinTheme.typography.displayLarge,
                    color = color,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
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
                    name = "Aragorn",
                    lvl = 5,
                    items = 15
                ),
                onClick = {},
                onLevelChange = {},
                onItemsChange = {}
            )
        }
    }
}
