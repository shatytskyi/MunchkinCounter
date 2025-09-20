package com.shatytskyi.gamecounter.ui.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shatytskyi.gamecounter.ui.components.MunchkinCard
import com.shatytskyi.gamecounter.ui.components.MunchkinIcon
import com.shatytskyi.gamecounter.ui.components.MunchkinText
import com.shatytskyi.gamecounter.ui.theme.MunchkinTheme

@Composable
fun LevelControlCard(
    onClick: () -> Unit,
    isNegative: Boolean,
    modifier: Modifier = Modifier
) {
    MunchkinCard(
        modifier = modifier,
        color = MunchkinTheme.colors.grey,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            MunchkinText(
                text = "LVL",
                style = MunchkinTheme.typography.titleMedium,
                color = MunchkinTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.width(8.dp))
            MunchkinIcon(
                imageVector = if (isNegative) Icons.Default.Remove else Icons.Default.Add,
                tint = MunchkinTheme.colors.onBackground,
                size = 24.dp
            )
        }
    }
}
