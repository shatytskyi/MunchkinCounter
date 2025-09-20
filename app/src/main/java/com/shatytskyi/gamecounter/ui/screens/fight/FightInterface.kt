package com.shatytskyi.gamecounter.ui.screens.fight

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.shatytskyi.gamecounter.R
import com.shatytskyi.gamecounter.data.Character
import com.shatytskyi.gamecounter.ui.components.MunchkinCard
import com.shatytskyi.gamecounter.ui.components.MunchkinHorizontalDivider
import com.shatytskyi.gamecounter.ui.components.MunchkinIcon
import com.shatytskyi.gamecounter.ui.components.MunchkinIconTextButton
import com.shatytskyi.gamecounter.ui.components.MunchkinText
import com.shatytskyi.gamecounter.ui.components.icons.Add
import com.shatytskyi.gamecounter.ui.components.icons.MunchkinIcons
import com.shatytskyi.gamecounter.ui.components.icons.Remove
import com.shatytskyi.gamecounter.ui.components.icons.Reset
import com.shatytskyi.gamecounter.ui.dialogs.HelpSelectionDialog
import com.shatytskyi.gamecounter.ui.dialogs.HelperOption
import com.shatytskyi.gamecounter.ui.theme.MunchkinTheme
import com.shatytskyi.gamecounter.data.FightPreferencesImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FightInterface(
    character: Character,
    characters: List<Character>,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val screenWidth = configuration.screenWidthDp.dp
    val coroutineScope = rememberCoroutineScope()

    // Sizes for sections
    val playerControlsWidth = screenWidth * 0.65f
    val comparisonColumnWidth = screenWidth * 0.2f
    val monsterControlsWidth = screenWidth * 0.65f

    // State
    val scrollState = rememberScrollState()

    // Player and monster power
    var playerTempPower by rememberSaveable { mutableIntStateOf(0) }
    var monsterPower by rememberSaveable { mutableIntStateOf(0) }

    // Helper state
    var helperOption by remember { mutableStateOf<HelperOption?>(null) }
    var showHelperDialog by remember { mutableStateOf(false) }

    // Info message state
    val fightPreferences = remember { FightPreferencesImpl(context) }
    var showInfoMessage by remember { mutableStateOf(!fightPreferences.hasShownFightInfoMessage()) }

    // Calculate total player power including helper
    val helperPower = when {
        helperOption == null -> 0
        helperOption!!.isClone -> character.level + character.items + playerTempPower
        else -> helperOption!!.character.level + helperOption!!.character.items
    }

    val totalPlayerPower = character.level + character.items + playerTempPower + helperPower
    val totalMonsterPower = monsterPower

    // Initial animation: scroll to monster after info message if needed
    LaunchedEffect(showInfoMessage) {
        if (!showInfoMessage) {
            delay(400)
            // Scroll to show monster (right side)
            val scrollAmountPx = with(density) { playerControlsWidth.toPx().toInt() }
            scrollState.animateScrollTo(
                value = scrollAmountPx,
                animationSpec = tween(durationMillis = 400)
            )
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Divider at the top
            MunchkinHorizontalDivider()

            Spacer(modifier = Modifier.height(8.dp))

        // Scrollable fight content takes all available space
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .horizontalScroll(scrollState, enabled = true)
                    .padding(horizontal = 16.dp)
            ) {
                // Player Power Controls
                Box(
                    modifier = Modifier
                        .width(playerControlsWidth)
                        .fillMaxHeight()
                ) {
                    PlayerPowerControls(
                        modifier = Modifier.fillMaxSize(),
                        currentPower = totalPlayerPower,
                        basePower = character.level + character.items,
                        tempPower = playerTempPower,
                        playerName = character.name,
                        helperName = when {
                            helperOption == null -> null
                            helperOption!!.isClone -> stringResource(R.string.clone)
                            else -> helperOption!!.character.name
                        },
                        onPowerChange = { delta ->
                            haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                            playerTempPower += delta
                        }
                    )
                }

                // Power Comparison Column
                Box(
                    modifier = Modifier
                        .width(comparisonColumnWidth)
                        .fillMaxHeight()
                ) {
                    PowerComparisonColumn(
                        modifier = Modifier.fillMaxSize(),
                        playerPower = totalPlayerPower,
                        monsterPower = totalMonsterPower,
                        isPlayerWinning = totalPlayerPower > totalMonsterPower
                    )
                }

                // Monster Power Controls
                Box(
                    modifier = Modifier
                        .width(monsterControlsWidth)
                        .fillMaxHeight()
                ) {
                    MonsterPowerControls(
                        modifier = Modifier.fillMaxSize(),
                        currentPower = totalMonsterPower,
                        onPowerChange = { delta ->
                            haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                            monsterPower += delta
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Divider
        MunchkinHorizontalDivider()

        Spacer(modifier = Modifier.height(16.dp))

        // Action buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Help/Remove Help button
            MunchkinIconTextButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    if (helperOption == null) {
                        showHelperDialog = true
                    } else {
                        helperOption = null
                    }
                },
                icon = if (helperOption == null) MunchkinIcons.Add else MunchkinIcons.Remove,
                text = stringResource(if (helperOption == null) R.string.help else R.string.remove_help),
                modifier = Modifier.weight(1f),
                textStyle = MunchkinTheme.typography.labelMedium,
                contentPadding = 16.dp,
                rippleColor = MunchkinTheme.colors.primary,
                bounded = false
            )

            // Reset button
            MunchkinIconTextButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    playerTempPower = 0
                    monsterPower = 0
                    helperOption = null
                },
                icon = MunchkinIcons.Reset,
                text = stringResource(R.string.reset),
                modifier = Modifier.weight(1f),
                textStyle = MunchkinTheme.typography.labelMedium,
                contentPadding = 16.dp,
                rippleColor = MunchkinTheme.colors.secondary,
                bounded = false
            )
        }
    }

    // Helper selection dialog
    if (showHelperDialog) {
        HelpSelectionDialog(
            characters = characters,
            currentPlayer = character,
            currentPlayerTempPower = playerTempPower,
            currentMonsterPower = monsterPower,
            onDismiss = { showHelperDialog = false },
            onConfirm = { option ->
                helperOption = option
                showHelperDialog = false

                // Scroll to player side after helper selection
                coroutineScope.launch {
                    scrollState.animateScrollTo(
                        value = 0,
                        animationSpec = tween(durationMillis = 400)
                    )
                }
            }
        )
    }

        // Info message overlay with full-screen clickable background
        AnimatedVisibility(
            visible = showInfoMessage,
            modifier = Modifier.fillMaxSize(),
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MunchkinTheme.colors.background.copy(alpha = 0.7f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        showInfoMessage = false
                        fightPreferences.setFightInfoMessageShown(true)
                    },
                contentAlignment = Alignment.TopCenter
            ) {
                MunchkinCard(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .padding(top = 24.dp),
                    color = MunchkinTheme.colors.secondary.copy(alpha = 0.95f),
                    onClick = {
                        showInfoMessage = false
                        fightPreferences.setFightInfoMessageShown(true)
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        MunchkinText(
                            text = stringResource(R.string.fight_info_title),
                            style = MunchkinTheme.typography.titleMedium,
                            color = MunchkinTheme.colors.onBackground,
                            textAlign = TextAlign.Center
                        )
                        MunchkinText(
                            text = stringResource(R.string.fight_info_message),
                            style = MunchkinTheme.typography.bodyMedium,
                            color = MunchkinTheme.colors.onBackground,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        MunchkinText(
                            text = stringResource(R.string.got_it),
                            style = MunchkinTheme.typography.labelLarge,
                            color = MunchkinTheme.colors.primary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PlayerPowerControls(
    currentPower: Int,
    basePower: Int,
    tempPower: Int,
    playerName: String,
    helperName: String? = null,
    onPowerChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Header
        MunchkinText(
            text = if (helperName != null) {
                "$playerName + $helperName"
            } else {
                playerName
            },
            style = MunchkinTheme.typography.titleSmall,
            color = MunchkinTheme.colors.primary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            maxLines = 1
        )

        // Power control buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Minus column
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (value in listOf(-1, -2, -3, -4, -5)) {
                    PowerButton(
                        value = value,
                        onClick = { onPowerChange(value) },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                }
            }

            // Plus column
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (value in listOf(+1, +2, +3, +4, +5)) {
                    PowerButton(
                        value = value,
                        onClick = { onPowerChange(value) },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun MonsterPowerControls(
    currentPower: Int,
    onPowerChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Header
        MunchkinText(
            text = stringResource(R.string.monster),
            style = MunchkinTheme.typography.titleSmall,
            color = MunchkinTheme.colors.red,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            textAlign = TextAlign.Center
        )

        // Power control buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Minus column
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (value in listOf(-1, -2, -3, -4, -5)) {
                    PowerButton(
                        value = value,
                        onClick = { onPowerChange(value) },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                }
            }

            // Plus column
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (value in listOf(+1, +2, +3, +4, +5)) {
                    PowerButton(
                        value = value,
                        onClick = { onPowerChange(value) },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun PowerComparisonColumn(
    playerPower: Int,
    monsterPower: Int,
    isPlayerWinning: Boolean,
    modifier: Modifier = Modifier
) {
    val powerDifference = playerPower - monsterPower

    ConstraintLayout(
        modifier = modifier
            .background(MunchkinTheme.colors.background.copy(alpha = 0.5f))
            .padding(8.dp)
    ) {
        val (playerRef, playerDiffRef, vsRef, monsterRef, monsterDiffRef) = createRefs()

        // Power difference above player (if player is winning)
        if (powerDifference > 0) {
            AnimatedContent(
                targetState = powerDifference,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
                label = "player_difference_animation",
                modifier = Modifier.constrainAs(playerDiffRef) {
                    bottom.linkTo(playerRef.top, margin = 8.dp)
                    centerHorizontallyTo(parent)
                }
            ) { difference ->
                MunchkinText(
                    text = "(+$difference)",
                    style = MunchkinTheme.typography.bodyMedium,
                    color = MunchkinTheme.colors.primary
                )
            }
        }

        // Player power
        AnimatedContent(
            targetState = playerPower,
            transitionSpec = {
                if (targetState > initialState) {
                    (slideInVertically { height -> height } + fadeIn()) togetherWith
                            (slideOutVertically { height -> -height } + fadeOut())
                } else {
                    (slideInVertically { height -> -height } + fadeIn()) togetherWith
                            (slideOutVertically { height -> height } + fadeOut())
                }
            },
            label = "player_power_animation",
            modifier = Modifier.constrainAs(playerRef) {
                bottom.linkTo(vsRef.top, margin = 24.dp)
                centerHorizontallyTo(parent)
            }
        ) { power ->
            MunchkinText(
                text = power.toString(),
                style = MunchkinTheme.typography.headlineLarge,
                color = MunchkinTheme.colors.primary
            )
        }

        // VS - centered in the parent
        MunchkinText(
            text = "VS",
            style = MunchkinTheme.typography.titleLarge,
            color = MunchkinTheme.colors.grey,
            modifier = Modifier.constrainAs(vsRef) {
                centerTo(parent)
            }
        )

        // Monster power
        AnimatedContent(
            targetState = monsterPower,
            transitionSpec = {
                if (targetState > initialState) {
                    (slideInVertically { height -> height } + fadeIn()) togetherWith
                            (slideOutVertically { height -> -height } + fadeOut())
                } else {
                    (slideInVertically { height -> -height } + fadeIn()) togetherWith
                            (slideOutVertically { height -> height } + fadeOut())
                }
            },
            label = "monster_power_animation",
            modifier = Modifier.constrainAs(monsterRef) {
                top.linkTo(vsRef.bottom, margin = 24.dp)
                centerHorizontallyTo(parent)
            }
        ) { power ->
            MunchkinText(
                text = power.toString(),
                style = MunchkinTheme.typography.headlineLarge,
                color = MunchkinTheme.colors.red
            )
        }

        // Power difference below monster (if monster is winning)
        if (powerDifference < 0) {
            AnimatedContent(
                targetState = powerDifference,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
                label = "monster_difference_animation",
                modifier = Modifier.constrainAs(monsterDiffRef) {
                    top.linkTo(monsterRef.bottom, margin = 8.dp)
                    centerHorizontallyTo(parent)
                }
            ) { difference ->
                MunchkinText(
                    text = "(+${kotlin.math.abs(difference)})",
                    style = MunchkinTheme.typography.bodyMedium,
                    color = MunchkinTheme.colors.red
                )
            }
        }
    }
}

@Composable
private fun PowerButton(
    value: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isNegative = value < 0
    MunchkinCard(
        modifier = modifier,
        color = MunchkinTheme.colors.grey,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MunchkinIcon(
                imageVector = if (isNegative) Icons.Default.Remove else Icons.Default.Add,
                tint = MunchkinTheme.colors.onBackground,
                size = 20.dp
            )
            Spacer(modifier = Modifier.width(4.dp))
            MunchkinText(
                text = kotlin.math.abs(value).toString(),
                style = MunchkinTheme.typography.titleLarge,
                color = MunchkinTheme.colors.onBackground
            )
        }
    }
}
