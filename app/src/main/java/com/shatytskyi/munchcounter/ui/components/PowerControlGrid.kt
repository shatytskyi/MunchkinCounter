package com.shatytskyi.munchcounter.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shatytskyi.munchcounter.ui.theme.Dimens
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

@Composable
fun PowerControlGrid(
    onPowerChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    MunchkinCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = MunchkinTheme.colors.surface,
        borderColor = MunchkinTheme.colors.outline.copy(alpha = 0.4f),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header
            MunchkinText(
                text = "POWER CONTROLS",
                style = MunchkinTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.2.sp
                ),
                color = MunchkinTheme.colors.onSurfaceVariant,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            // Two columns layout: Minus on left, Plus on right
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Left column - Minus buttons
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PowerButton(
                        value = -1,
                        onClick = { onPowerChange(-1) },
                        isNegative = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    PowerButton(
                        value = -2,
                        onClick = { onPowerChange(-2) },
                        isNegative = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    PowerButton(
                        value = -3,
                        onClick = { onPowerChange(-3) },
                        isNegative = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    PowerButton(
                        value = -4,
                        onClick = { onPowerChange(-4) },
                        isNegative = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    PowerButton(
                        value = -5,
                        onClick = { onPowerChange(-5) },
                        isNegative = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                
                // Right column - Plus buttons
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PowerButton(
                        value = 1,
                        onClick = { onPowerChange(1) },
                        isNegative = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    PowerButton(
                        value = 2,
                        onClick = { onPowerChange(2) },
                        isNegative = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    PowerButton(
                        value = 3,
                        onClick = { onPowerChange(3) },
                        isNegative = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    PowerButton(
                        value = 4,
                        onClick = { onPowerChange(4) },
                        isNegative = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    PowerButton(
                        value = 5,
                        onClick = { onPowerChange(5) },
                        isNegative = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun PowerButton(
    value: Int,
    onClick: () -> Unit,
    isNegative: Boolean,
    modifier: Modifier = Modifier
) {
    MunchkinButton(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(14.dp),
        colors = if (isNegative) {
            MunchkinButtonDefaults.errorColors()
        } else {
            MunchkinButtonDefaults.tertiaryColors()
        },
        borderColor = if (isNegative) {
            MunchkinTheme.colors.error.copy(alpha = 0.5f)
        } else {
            MunchkinTheme.colors.tertiary.copy(alpha = 0.5f)
        }
    ) {
        MunchkinText(
            text = if (value > 0) "+$value" else value.toString(),
            style = MunchkinTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = if (isNegative) {
                MunchkinTheme.colors.onErrorContainer
            } else {
                MunchkinTheme.colors.onTertiaryContainer
            }
        )
    }
}