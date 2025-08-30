package com.shatytskyi.munchcounter.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.data.Character
import com.shatytskyi.munchcounter.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterCard(
    character: Character,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Character Name
            Text(
                text = character.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.onSurface,
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
                    Text(
                        text = "LVL",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = character.lvl.toString(),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                // Score
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "SCORE",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = character.score.toString(),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

@Composable
fun CharacterListItem(
    character: Character,
    onClick: () -> Unit,
    onLevelChange: (Int) -> Unit = {},
    onPowerChange: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onClick() }
    ) {
        // Character name
        Text(
            text = character.name,
            style = MaterialTheme.typography.titleLarge,
            color = Black,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        
        // Level and Power labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Уровень",
                style = MaterialTheme.typography.labelMedium,
                color = DarkGrey
            )
            
            Image(
                painter = painterResource(id = R.drawable.icon_more),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                colorFilter = ColorFilter.tint(Black)
            )
            
            Text(
                text = "Силы",
                style = MaterialTheme.typography.labelMedium,
                color = DarkGrey
            )
        }
        
        // Level and Power values with controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Level control
            MunchkinCard(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { onLevelChange(-1) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_left),
                            contentDescription = "Уменьшить уровень",
                            colorFilter = ColorFilter.tint(Primary)
                        )
                    }
                    
                    Box(
                        modifier = Modifier
                            .width(64.dp)
                            .fillMaxHeight()
                            .padding(vertical = 4.dp)
                            .background(White),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = character.lvl.toString(),
                            style = MaterialTheme.typography.displayMedium,
                            color = Black,
                            textAlign = TextAlign.Center
                        )
                    }
                    
                    IconButton(
                        onClick = { onLevelChange(+1) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_right),
                            contentDescription = "Увеличить уровень",
                            colorFilter = ColorFilter.tint(Primary)
                        )
                    }
                }
            }
            
            // Power control
            MunchkinCard(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { onPowerChange(-1) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_left),
                            contentDescription = "Уменьшить силу",
                            colorFilter = ColorFilter.tint(Primary)
                        )
                    }
                    
                    Box(
                        modifier = Modifier
                            .width(64.dp)
                            .fillMaxHeight()
                            .padding(vertical = 4.dp)
                            .background(White),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = character.score.toString(),
                            style = MaterialTheme.typography.displayMedium,
                            color = Black,
                            textAlign = TextAlign.Center
                        )
                    }
                    
                    IconButton(
                        onClick = { onPowerChange(+1) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_right),
                            contentDescription = "Увеличить силу",
                            colorFilter = ColorFilter.tint(Primary)
                        )
                    }
                }
            }
        }
    }
}