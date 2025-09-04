package com.shatytskyi.munchcounter.ui.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

@Composable
fun DetailsControlsLayout(
    modifier: Modifier = Modifier,
    onLevelChange: (Int) -> Unit,
    onPowerChange: (Int) -> Unit
) {
    val haptic = LocalHapticFeedback.current

    AdaptiveHeightLayout(
        modifier = modifier,
        items = listOf(
            // Level title
            LayoutItem(
                type = LayoutItemType.FIXED,
                content = {
                    MunchkinText(
                        text = stringResource(R.string.level),
                        style = MunchkinTheme.typography.titleMedium,
                        color = MunchkinTheme.colors.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(vertical = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            ),

            // Level buttons row
            LayoutItem(
                type = LayoutItemType.DISTRIBUTABLE,
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        LevelControlCard(
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                onLevelChange(-1)
                            },
                            isNegative = true,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                        LevelControlCard(
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                onLevelChange(+1)
                            },
                            isNegative = false,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                    }
                }
            ),

            // Items title
            LayoutItem(
                type = LayoutItemType.FIXED,
                content = {
                    MunchkinText(
                        text = stringResource(R.string.items),
                        style = MunchkinTheme.typography.titleMedium,
                        color = MunchkinTheme.colors.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(vertical = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            ),

            // Items row 1: -1/+1
            LayoutItem(
                type = LayoutItemType.DISTRIBUTABLE,
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        PowerControlCard(
                            value = -1,
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                onPowerChange(-1)
                            },
                            isNegative = true,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                        PowerControlCard(
                            value = +1,
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                onPowerChange(+1)
                            },
                            isNegative = false,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                    }
                }
            ),

            // Items row 2: -2/+2
            LayoutItem(
                type = LayoutItemType.DISTRIBUTABLE,
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        PowerControlCard(
                            value = -2,
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                onPowerChange(-2)
                            },
                            isNegative = true,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                        PowerControlCard(
                            value = +2,
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                onPowerChange(+2)
                            },
                            isNegative = false,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                    }
                }
            ),

            // Items row 3: -3/+3
            LayoutItem(
                type = LayoutItemType.DISTRIBUTABLE,
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        PowerControlCard(
                            value = -3,
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                onPowerChange(-3)
                            },
                            isNegative = true,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                        PowerControlCard(
                            value = +3,
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                onPowerChange(+3)
                            },
                            isNegative = false,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                    }
                }
            ),

            // Items row 4: -4/+4
            LayoutItem(
                type = LayoutItemType.DISTRIBUTABLE,
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        PowerControlCard(
                            value = -4,
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                onPowerChange(-4)
                            },
                            isNegative = true,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                        PowerControlCard(
                            value = +4,
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                onPowerChange(+4)
                            },
                            isNegative = false,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                    }
                }
            ),

            // Items row 5: -5/+5
            LayoutItem(
                type = LayoutItemType.DISTRIBUTABLE,
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        PowerControlCard(
                            value = -5,
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                onPowerChange(-5)
                            },
                            isNegative = true,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                        PowerControlCard(
                            value = +5,
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                onPowerChange(+5)
                            },
                            isNegative = false,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                    }
                }
            )
        ),
        spacedBy = 8.dp
    )
}

enum class LayoutItemType {
    FIXED,
    DISTRIBUTABLE
}

data class LayoutItem(
    val type: LayoutItemType,
    val content: @Composable () -> Unit,
    val weight: Float = 1f
)

@Composable
private fun AdaptiveHeightLayout(
    modifier: Modifier = Modifier,
    spacedBy: Dp = 0.dp,
    items: List<LayoutItem>
) {
    Layout(
        content = {
            items.forEach { item ->
                item.content()
            }
        },
        modifier = modifier
    ) { measurables, constraints ->
        // First pass: measure all fixed items
        val fixedPlaceables = mutableListOf<Placeable>()
        val distributableItems = mutableListOf<Pair<Measurable, LayoutItem>>()
        var fixedHeight = 0
        var itemIndex = 0

        items.forEach { item ->
            val measurable = measurables[itemIndex]
            when (item.type) {
                LayoutItemType.FIXED -> {
                    val placeable = measurable.measure(
                        constraints.copy(
                            minHeight = 0,
                            maxHeight = Constraints.Infinity
                        )
                    )
                    fixedPlaceables.add(placeable)
                    fixedHeight += placeable.height
                }

                LayoutItemType.DISTRIBUTABLE -> {
                    distributableItems.add(measurable to item)
                }
            }
            itemIndex++
        }

        // Calculate spacing
        val totalSpacing = if (items.size > 1) spacedBy.roundToPx() * (items.size - 1) else 0

        // Calculate remaining height for distributable items
        val availableHeight = constraints.maxHeight - fixedHeight - totalSpacing
        val totalWeight = distributableItems.sumOf { it.second.weight.toDouble() }.toFloat()

        // Second pass: measure distributable items with calculated height based on weights
        val distributablePlaceables = distributableItems.map { (measurable, item) ->
            val itemHeight = if (totalWeight > 0) {
                (availableHeight * item.weight / totalWeight).toInt()
            } else 0

            measurable.measure(
                constraints.copy(
                    minHeight = itemHeight,
                    maxHeight = itemHeight
                )
            )
        }

        // Combine all placeables in original order
        val allPlaceables = mutableListOf<Placeable>()
        var fixedIndex = 0
        var distributableIndex = 0

        items.forEach { item ->
            when (item.type) {
                LayoutItemType.FIXED -> {
                    allPlaceables.add(fixedPlaceables[fixedIndex])
                    fixedIndex++
                }

                LayoutItemType.DISTRIBUTABLE -> {
                    allPlaceables.add(distributablePlaceables[distributableIndex])
                    distributableIndex++
                }
            }
        }

        // Layout
        layout(constraints.maxWidth, constraints.maxHeight) {
            var yPosition = 0
            allPlaceables.forEachIndexed { index, placeable ->
                placeable.placeRelative(0, yPosition)
                yPosition += placeable.height
                if (index < allPlaceables.size - 1) {
                    yPosition += spacedBy.roundToPx()
                }
            }
        }
    }
}

