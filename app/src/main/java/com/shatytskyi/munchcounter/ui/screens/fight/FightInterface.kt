package com.shatytskyi.munchcounter.ui.screens.fight

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.data.Character
import com.shatytskyi.munchcounter.ui.components.MunchkinCard
import com.shatytskyi.munchcounter.ui.components.MunchkinHorizontalDivider
import com.shatytskyi.munchcounter.ui.components.MunchkinIcon
import com.shatytskyi.munchcounter.ui.components.MunchkinIconTextButton
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.components.icons.Add
import com.shatytskyi.munchcounter.ui.components.icons.MunchkinIcons
import com.shatytskyi.munchcounter.ui.components.icons.Reset
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme
import kotlinx.coroutines.delay

@Composable
fun FightInterface(
    character: Character,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    // Sizes for sections
    val playerControlsWidth = screenWidth * 0.65f
    val comparisonColumnWidth = screenWidth * 0.2f
    val monsterControlsWidth = screenWidth * 0.65f

    // State
    val scrollState = rememberScrollState()

    // Player and monster power
    var playerTempPower by rememberSaveable { mutableIntStateOf(0) }
    var monsterPower by rememberSaveable { mutableIntStateOf(0) }

    val totalPlayerPower = character.level + character.items + playerTempPower
    val totalMonsterPower = monsterPower

    // Initial animation: scroll to monster on screen start
    LaunchedEffect(Unit) {
        delay(400)
        // Scroll to show monster (right side)
        val scrollAmountPx = with(density) { playerControlsWidth.toPx().toInt() }
        scrollState.animateScrollTo(
            value = scrollAmountPx,
            animationSpec = tween(durationMillis = 400)
        )
    }

    Column(
        modifier = modifier.fillMaxSize()
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
//            val context = LocalContext.current
//
            // Help/Add Friend button
//            MunchkinIconTextButton(
//                onClick = {
//                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
//                    Toast.makeText(context, "Coming soon!", Toast.LENGTH_SHORT).show()
//                },
//                icon = MunchkinIcons.Add,
//                text = "Help",
//                modifier = Modifier.weight(1f),
//                textStyle = MunchkinTheme.typography.labelMedium,
//                contentPadding = 16.dp,
//                rippleColor = MunchkinTheme.colors.primary,
//                bounded = false
//            )

            // Reset button
            MunchkinIconTextButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    // Reset temporary power changes
                    playerTempPower = 0
                    monsterPower = 0
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
}

@Composable
private fun PlayerPowerControls(
    currentPower: Int,
    basePower: Int,
    tempPower: Int,
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
            text = stringResource(R.string.player),
            style = MunchkinTheme.typography.titleMedium,
            color = MunchkinTheme.colors.primary,
            modifier = Modifier.fillMaxWidth(),
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
            style = MunchkinTheme.typography.titleMedium,
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
        val (playerRef, vsRef, monsterRef, differenceRef) = createRefs()
        
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
            style = MunchkinTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
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
        
        // Power difference indicator
        if (powerDifference != 0) {
            AnimatedContent(
                targetState = powerDifference,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
                label = "power_difference_animation",
                modifier = Modifier.constrainAs(differenceRef) {
                    top.linkTo(monsterRef.bottom, margin = 16.dp)
                    centerHorizontallyTo(parent)
                }
            ) { difference ->
                MunchkinText(
                    text = if (difference > 0) "(+$difference)" else "($difference)",
                    style = MunchkinTheme.typography.bodyLarge,
                    color = if (difference > 0) MunchkinTheme.colors.primary else MunchkinTheme.colors.red
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
                style = MunchkinTheme.typography.titleMedium,
                color = MunchkinTheme.colors.onBackground
            )
        }
    }
}
