package com.shatytskyi.munchcounter.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.data.Character
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

@Composable
fun CharacterCard(
    character: Character,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    MunchkinCard(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        backgroundColor = MunchkinTheme.colors.surface,
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Character Name
            MunchkinText(
                text = character.name,
                style = MunchkinTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MunchkinTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Level and Power
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Level
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MunchkinText(
                        text = "LVL",
                        style = MunchkinTheme.typography.labelSmall,
                        color = MunchkinTheme.colors.onSurfaceVariant
                    )
                    MunchkinText(
                        text = character.lvl.toString(),
                        style = MunchkinTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MunchkinTheme.colors.primary
                    )
                }
                
                // Score
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MunchkinText(
                        text = "SCORE",
                        style = MunchkinTheme.typography.labelSmall,
                        color = MunchkinTheme.colors.onSurfaceVariant
                    )
                    MunchkinText(
                        text = character.power.toString(),
                        style = MunchkinTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MunchkinTheme.colors.secondary
                    )
                }
            }
        }
    }
}