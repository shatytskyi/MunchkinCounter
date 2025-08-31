package com.shatytskyi.munchcounter.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

@Composable
fun PowerControlGrid(
    onPowerChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MunchkinText(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.items_controls),
            style = MunchkinTheme.typography.titleSmall,
            color = MunchkinTheme.colors.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
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

@Composable
private fun PowerButton(
    value: Int,
    onClick: () -> Unit,
    isNegative: Boolean,
    modifier: Modifier = Modifier
) {
    MunchkinCard(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        color = if (isNegative) {
            MunchkinTheme.colors.red
        } else {
            MunchkinTheme.colors.green
        },
    ) {
        MunchkinText(
            text = if (value > 0) "+$value" else value.toString(),
            style = MunchkinTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = if (isNegative) {
                MunchkinTheme.colors.red
            } else {
                MunchkinTheme.colors.green
            }
        )
    }
}
