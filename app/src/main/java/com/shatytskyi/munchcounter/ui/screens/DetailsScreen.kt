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
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.ui.components.CommonDiceDialog
import com.shatytskyi.munchcounter.ui.components.EditCharacterDialog
import com.shatytskyi.munchcounter.ui.components.MunchkinCard
import com.shatytskyi.munchcounter.ui.components.MunchkinIcon
import com.shatytskyi.munchcounter.ui.components.MunchkinIconButton
import com.shatytskyi.munchcounter.ui.components.MunchkinIconButtonDefaults
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.components.MunchkinTopAppBar
import com.shatytskyi.munchcounter.ui.components.PowerControlGrid
import com.shatytskyi.munchcounter.ui.components.WarningDialog
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme
import com.shatytskyi.munchcounter.viewmodel.CommonViewModel

@Composable
fun DetailsScreen(
    viewModel: CommonViewModel,
    characterId: Long,
    onBack: () -> Unit,
    onFight: () -> Unit,
    modifier: Modifier = Modifier
) {
    val characters by viewModel.characters.collectAsState()
    val character = characters.find { it.id == characterId }

    var showEditDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }
    var showDiceDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadCharacters()
    }

    if (character == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Simple loading indicator without CircularProgressIndicator
                MunchkinText(
                    text = "●●●",
                    style = MunchkinTheme.typography.headlineLarge,
                    color = MunchkinTheme.colors.primary
                )
                MunchkinText(
                    text = stringResource(R.string.loading_character),
                    style = MunchkinTheme.typography.bodyLarge,
                    color = MunchkinTheme.colors.onBackground
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
        MunchkinTopAppBar(
            title = character.name,
            onBack = onBack,
            actions = {
                MunchkinIconButton(onClick = { showResetDialog = true }) {
                    MunchkinIcon(
                        Icons.Default.Refresh,
                        tint = MunchkinTheme.colors.onBackground
                    )
                }
                MunchkinIconButton(onClick = { showEditDialog = true }) {
                    MunchkinIcon(
                        Icons.Default.Edit,
                        tint = MunchkinTheme.colors.onBackground
                    )
                }
                MunchkinIconButton(onClick = { showDiceDialog = true }) {
                    MunchkinIcon(
                        Icons.Default.Casino,
                        tint = MunchkinTheme.colors.onBackground
                    )
                }
                MunchkinIconButton(onClick = onFight) {
                    MunchkinIcon(
                        Icons.Default.LocalFireDepartment,
                        tint = MunchkinTheme.colors.red
                    )
                }
            }
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.navigationBars.only(WindowInsetsSides.Bottom))
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Hero Section - Total Power
            MunchkinCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                color = MunchkinTheme.colors.primary.copy(alpha = 0.3f),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MunchkinText(
                        text = "TOTAL POWER",
                        style = MunchkinTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 1.5.sp
                        ),
                        color = MunchkinTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    MunchkinText(
                        text = character.power.toString(),
                        style = MunchkinTheme.typography.displayLarge,
                        color = MunchkinTheme.colors.onBackground
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Stats Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Level Card
                MunchkinCard(
                    modifier = Modifier.weight(1f),
                    color = MunchkinTheme.colors.primary.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        MunchkinText(
                            text = "LEVEL",
                            style = MunchkinTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Medium,
                                letterSpacing = 1.2.sp
                            ),
                            color = MunchkinTheme.colors.onBackground
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            MunchkinIconButton(
                                onClick = { viewModel.changeLevel(characterId, -1) },
                                size = 36.dp,
                                colors = MunchkinIconButtonDefaults.filledIconButtonColors(
                                    containerColor = MunchkinTheme.colors.primary.copy(alpha = 0.1f),
                                    contentColor = MunchkinTheme.colors.primary
                                ),
                                borderColor = MunchkinTheme.colors.primary.copy(alpha = 0.5f)
                            ) {
                                MunchkinIcon(
                                    Icons.Default.Remove,
                                    size = 18.dp,
                                    tint = MunchkinTheme.colors.primary
                                )
                            }
                            MunchkinText(
                                text = character.lvl.toString(),
                                style = MunchkinTheme.typography.headlineLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = MunchkinTheme.colors.primary,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            MunchkinIconButton(
                                onClick = { viewModel.changeLevel(characterId, +1) },
                                size = 36.dp,
                                colors = MunchkinIconButtonDefaults.filledIconButtonColors(
                                    containerColor = MunchkinTheme.colors.primary.copy(alpha = 0.1f),
                                    contentColor = MunchkinTheme.colors.primary
                                ),
                                borderColor = MunchkinTheme.colors.primary.copy(alpha = 0.5f)
                            ) {
                                MunchkinIcon(
                                    Icons.Default.Add,
                                    size = 18.dp,
                                    tint = MunchkinTheme.colors.primary
                                )
                            }
                        }
                    }
                }

                // Items Card
                MunchkinCard(
                    modifier = Modifier.weight(1f),
                    color = MunchkinTheme.colors.green.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        MunchkinText(
                            text = "ITEMS",
                            style = MunchkinTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Medium,
                                letterSpacing = 1.2.sp
                            ),
                            color = MunchkinTheme.colors.onBackground
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            MunchkinIconButton(
                                onClick = { viewModel.changePower(characterId, -1) },
                                size = 36.dp,
                                colors = MunchkinIconButtonDefaults.filledIconButtonColors(
                                    containerColor = MunchkinTheme.colors.green.copy(alpha = 0.1f),
                                    contentColor = MunchkinTheme.colors.green
                                ),
                                borderColor = MunchkinTheme.colors.green.copy(alpha = 0.5f)
                            ) {
                                MunchkinIcon(
                                    Icons.Default.Remove,
                                    size = 18.dp,
                                    tint = MunchkinTheme.colors.green
                                )
                            }
                            MunchkinText(
                                text = character.items.toString(),
                                style = MunchkinTheme.typography.headlineLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = MunchkinTheme.colors.green,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            MunchkinIconButton(
                                onClick = { viewModel.changePower(characterId, +1) },
                                size = 36.dp,
                                colors = MunchkinIconButtonDefaults.filledIconButtonColors(
                                    containerColor = MunchkinTheme.colors.green.copy(alpha = 0.1f),
                                    contentColor = MunchkinTheme.colors.green
                                ),
                                borderColor = MunchkinTheme.colors.green.copy(alpha = 0.5f)
                            ) {
                                MunchkinIcon(
                                    Icons.Default.Add,
                                    size = 18.dp,
                                    tint = MunchkinTheme.colors.green
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Power Control Grid
            PowerControlGrid(
                onPowerChange = { delta -> viewModel.changePower(characterId, delta) },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }

    // Dialogs
    if (showEditDialog) {
        EditCharacterDialog(
            character = character,
            onDismiss = { showEditDialog = false },
            onConfirm = { name, level, power ->
                viewModel.updateCharacter(characterId, name, level, power)
                showEditDialog = false
            }
        )
    }

    if (showResetDialog) {
        WarningDialog(
            title = stringResource(R.string.reset_character_title, character.name),
            message = stringResource(R.string.reset_character_warning),
            onDismiss = { showResetDialog = false },
            onConfirm = {
                viewModel.resetCharacter(characterId)
                showResetDialog = false
            }
        )
    }

    if (showDiceDialog) {
        CommonDiceDialog(onDismiss = { showDiceDialog = false })
    }
}
