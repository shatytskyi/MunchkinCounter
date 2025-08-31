package com.shatytskyi.munchcounter.ui.screens

import androidx.compose.foundation.background
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
import com.shatytskyi.munchcounter.ui.components.MunchkinTopAppBar
import com.shatytskyi.munchcounter.ui.components.EditCharacterDialog
import com.shatytskyi.munchcounter.ui.components.MunchkinCard
import com.shatytskyi.munchcounter.ui.components.MunchkinDialog
import com.shatytskyi.munchcounter.ui.components.MunchkinIcon
import com.shatytskyi.munchcounter.ui.components.MunchkinIconButton
import com.shatytskyi.munchcounter.ui.components.MunchkinIconButtonDefaults
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.components.MunchkinTextButton
import com.shatytskyi.munchcounter.ui.components.PowerControlGrid
import com.shatytskyi.munchcounter.ui.components.WarningDialog
import com.shatytskyi.munchcounter.ui.theme.Dimens
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme
import com.shatytskyi.munchcounter.viewmodel.CharacterViewModel

@Composable
fun SoloScreen(
    viewModel: CharacterViewModel,
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
    var showInfoDialog by remember { mutableStateOf(false) }

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
                verticalArrangement = Arrangement.spacedBy(Dimens.paddingLarge)
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
        MunchkinTopAppBar(
            title = character.name,
            onBack = onBack,
            actions = {
                MunchkinIconButton(onClick = { showResetDialog = true }) {
                    MunchkinIcon(
                        Icons.Default.Refresh,
                        tint = MunchkinTheme.colors.onSurface
                    )
                }
                MunchkinIconButton(onClick = { showEditDialog = true }) {
                    MunchkinIcon(
                        Icons.Default.Edit,
                        tint = MunchkinTheme.colors.onSurface
                    )
                }
                MunchkinIconButton(onClick = { showDiceDialog = true }) {
                    MunchkinIcon(
                        Icons.Default.Casino,
                        tint = MunchkinTheme.colors.onSurface
                    )
                }
                MunchkinIconButton(onClick = onFight) {
                    MunchkinIcon(
                        Icons.Default.LocalFireDepartment,
                        tint = MunchkinTheme.colors.error
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
                .padding(vertical = Dimens.paddingMedium),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium)
        ) {
            Spacer(modifier = Modifier.height(Dimens.paddingMedium))
            
            // Hero Section - Total Power
            MunchkinCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.screenPaddingHorizontal),
                backgroundColor = MunchkinTheme.colors.primaryContainer,
                borderColor = MunchkinTheme.colors.primary.copy(alpha = 0.3f),
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
                        color = MunchkinTheme.colors.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    MunchkinText(
                        text = character.power.toString(),
                        style = MunchkinTheme.typography.displayLarge.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 80.sp
                        ),
                        color = MunchkinTheme.colors.onPrimaryContainer
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(Dimens.paddingSmall))
            
            // Stats Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.screenPaddingHorizontal),
                horizontalArrangement = Arrangement.spacedBy(Dimens.paddingMedium)
            ) {
                // Level Card
                MunchkinCard(
                    modifier = Modifier.weight(1f),
                    backgroundColor = MunchkinTheme.colors.surfaceContainer,
                    borderColor = MunchkinTheme.colors.primary.copy(alpha = 0.2f),
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
                            color = MunchkinTheme.colors.onSurfaceVariant
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
                    backgroundColor = MunchkinTheme.colors.surfaceContainer,
                    borderColor = MunchkinTheme.colors.tertiary.copy(alpha = 0.2f),
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
                            color = MunchkinTheme.colors.onSurfaceVariant
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
                                    containerColor = MunchkinTheme.colors.tertiary.copy(alpha = 0.1f),
                                    contentColor = MunchkinTheme.colors.tertiary
                                ),
                                borderColor = MunchkinTheme.colors.tertiary.copy(alpha = 0.5f)
                            ) {
                                MunchkinIcon(
                                    Icons.Default.Remove,
                                    size = 18.dp,
                                    tint = MunchkinTheme.colors.tertiary
                                )
                            }
                            MunchkinText(
                                text = character.items.toString(),
                                style = MunchkinTheme.typography.headlineLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = MunchkinTheme.colors.tertiary,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            MunchkinIconButton(
                                onClick = { viewModel.changePower(characterId, +1) },
                                size = 36.dp,
                                colors = MunchkinIconButtonDefaults.filledIconButtonColors(
                                    containerColor = MunchkinTheme.colors.tertiary.copy(alpha = 0.1f),
                                    contentColor = MunchkinTheme.colors.tertiary
                                ),
                                borderColor = MunchkinTheme.colors.tertiary.copy(alpha = 0.5f)
                            ) {
                                MunchkinIcon(
                                    Icons.Default.Add,
                                    size = 18.dp,
                                    tint = MunchkinTheme.colors.tertiary
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(Dimens.paddingSmall))
            
            // Power Control Grid
            PowerControlGrid(
                onPowerChange = { delta -> viewModel.changePower(characterId, delta) },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = Dimens.screenPaddingHorizontal)
            )
            
            Spacer(modifier = Modifier.height(Dimens.paddingMedium))
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

    if (showInfoDialog) {
        MunchkinDialog(
            onDismissRequest = { showInfoDialog = false },
            title = stringResource(R.string.info),
            content = {
                MunchkinText(
                    text = stringResource(R.string.info),
                    style = MunchkinTheme.typography.bodyMedium,
                    color = MunchkinTheme.colors.onSurface
                )
            },
            confirmButton = {
                MunchkinTextButton(
                    onClick = { showInfoDialog = false },
                    text = "OK"
                )
            }
        )
    }
}