package com.shatytskyi.munchcounter.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.ui.theme.Dimens

@Composable
fun PowerControlGrid(
    onPowerChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 12.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header
            Text(
                text = "POWER CONTROLS",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.2.sp
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
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
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isNegative) {
                MaterialTheme.colorScheme.errorContainer
            } else {
                MaterialTheme.colorScheme.tertiaryContainer
            },
            contentColor = if (isNegative) {
                MaterialTheme.colorScheme.onErrorContainer
            } else {
                MaterialTheme.colorScheme.onTertiaryContainer
            }
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 3.dp,
            pressedElevation = 8.dp,
            disabledElevation = 0.dp
        )
    ) {
        Text(
            text = if (value > 0) "+$value" else value.toString(),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}