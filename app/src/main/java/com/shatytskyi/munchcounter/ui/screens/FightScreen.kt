package com.shatytskyi.munchcounter.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shatytskyi.munchcounter.ui.components.CommonTopAppBar
import com.shatytskyi.munchcounter.ui.components.MunchkinCard
import com.shatytskyi.munchcounter.ui.components.MunchkinIcon
import com.shatytskyi.munchcounter.ui.components.MunchkinIconButton
import com.shatytskyi.munchcounter.ui.components.MunchkinIconButtonDefaults
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.theme.Dimens
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme
import com.shatytskyi.munchcounter.viewmodel.CharacterViewModel

@Composable
fun FightScreen(
    viewModel: CharacterViewModel,
    playerId: Long,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val characters by viewModel.characters.collectAsState()
    val player = characters.find { it.id == playerId }

    LaunchedEffect(Unit) {
        viewModel.loadCharacters()
    }

    if (player == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Dimens.paddingLarge)
            ) {
                MunchkinText(
                    text = "●●●",
                    style = MunchkinTheme.typography.headlineLarge,
                    color = MunchkinTheme.colors.primary
                )
                MunchkinText(
                    text = "Loading Player...",
                    style = MunchkinTheme.typography.bodyLarge,
                    color = MunchkinTheme.colors.onSurface
                )
            }
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Top App Bar
        CommonTopAppBar(
            title = "${player.name} - Fight",
            onBack = onBack
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.navigationBars.only(WindowInsetsSides.Bottom))
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
                .padding(Dimens.screenPaddingHorizontal),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingLarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(Dimens.paddingMedium))

            // Player Power Display
            MunchkinCard(
                modifier = Modifier.fillMaxWidth(),
                elevation = 4.dp,
                backgroundColor = MunchkinTheme.colors.primaryContainer,
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MunchkinText(
                        text = "PLAYER POWER",
                        style = MunchkinTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 1.2.sp
                        ),
                        color = MunchkinTheme.colors.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    MunchkinText(
                        text = player.power.toString(),
                        style = MunchkinTheme.typography.displayLarge.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 60.sp
                        ),
                        color = MunchkinTheme.colors.onPrimaryContainer
                    )
                    MunchkinText(
                        text = "Level ${player.lvl} + Items ${player.items}",
                        style = MunchkinTheme.typography.bodyLarge,
                        color = MunchkinTheme.colors.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                }
            }

            // Power Adjustment Section
            MunchkinCard(
                modifier = Modifier.fillMaxWidth(),
                elevation = 2.dp,
                backgroundColor = MunchkinTheme.colors.surfaceContainer,
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MunchkinText(
                        text = "FIGHT ADJUSTMENTS",
                        style = MunchkinTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 1.2.sp
                        ),
                        color = MunchkinTheme.colors.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Quick power adjustments
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // -5 Power
                        MunchkinIconButton(
                            onClick = { viewModel.changePower(playerId, -5) },
                            size = 60.dp,
                            colors = MunchkinIconButtonDefaults.filledIconButtonColors(
                                containerColor = MunchkinTheme.colors.errorContainer,
                                contentColor = MunchkinTheme.colors.onErrorContainer
                            )
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                MunchkinIcon(
                                    Icons.Default.Remove,
                                    contentDescription = null,
                                    size = 20.dp,
                                    tint = MunchkinTheme.colors.onErrorContainer
                                )
                                MunchkinText(
                                    text = "5",
                                    style = MunchkinTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = MunchkinTheme.colors.onErrorContainer
                                )
                            }
                        }
                        
                        // -1 Power
                        MunchkinIconButton(
                            onClick = { viewModel.changePower(playerId, -1) },
                            size = 60.dp,
                            colors = MunchkinIconButtonDefaults.filledIconButtonColors(
                                containerColor = MunchkinTheme.colors.errorContainer.copy(alpha = 0.7f),
                                contentColor = MunchkinTheme.colors.onErrorContainer
                            )
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                MunchkinIcon(
                                    Icons.Default.Remove,
                                    contentDescription = null,
                                    size = 20.dp,
                                    tint = MunchkinTheme.colors.onErrorContainer
                                )
                                MunchkinText(
                                    text = "1",
                                    style = MunchkinTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = MunchkinTheme.colors.onErrorContainer
                                )
                            }
                        }
                        
                        // +1 Power
                        MunchkinIconButton(
                            onClick = { viewModel.changePower(playerId, +1) },
                            size = 60.dp,
                            colors = MunchkinIconButtonDefaults.filledIconButtonColors(
                                containerColor = MunchkinTheme.colors.tertiaryContainer.copy(alpha = 0.7f),
                                contentColor = MunchkinTheme.colors.onTertiaryContainer
                            )
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                MunchkinIcon(
                                    Icons.Default.Add,
                                    contentDescription = null,
                                    size = 20.dp,
                                    tint = MunchkinTheme.colors.onTertiaryContainer
                                )
                                MunchkinText(
                                    text = "1",
                                    style = MunchkinTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = MunchkinTheme.colors.onTertiaryContainer
                                )
                            }
                        }
                        
                        // +5 Power
                        MunchkinIconButton(
                            onClick = { viewModel.changePower(playerId, +5) },
                            size = 60.dp,
                            colors = MunchkinIconButtonDefaults.filledIconButtonColors(
                                containerColor = MunchkinTheme.colors.tertiaryContainer,
                                contentColor = MunchkinTheme.colors.onTertiaryContainer
                            )
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                MunchkinIcon(
                                    Icons.Default.Add,
                                    contentDescription = null,
                                    size = 20.dp,
                                    tint = MunchkinTheme.colors.onTertiaryContainer
                                )
                                MunchkinText(
                                    text = "5",
                                    style = MunchkinTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = MunchkinTheme.colors.onTertiaryContainer
                                )
                            }
                        }
                    }
                }
            }

            // Instructions
            MunchkinCard(
                modifier = Modifier.fillMaxWidth(),
                elevation = 1.dp,
                backgroundColor = MunchkinTheme.colors.surface,
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MunchkinText(
                        text = "💡 FIGHT TIPS",
                        style = MunchkinTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MunchkinTheme.colors.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    MunchkinText(
                        text = "Use the buttons above to adjust power during combat.\nItems and curses can temporarily change your power!",
                        style = MunchkinTheme.typography.bodyMedium,
                        color = MunchkinTheme.colors.onSurface,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}