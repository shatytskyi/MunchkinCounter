package com.shatytskyi.munchcounter.ui.dialogs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.ui.components.MunchkinDialog
import com.shatytskyi.munchcounter.ui.components.MunchkinIcon
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.components.icons.dice.Dice1
import com.shatytskyi.munchcounter.ui.components.icons.dice.Dice2
import com.shatytskyi.munchcounter.ui.components.icons.dice.Dice3
import com.shatytskyi.munchcounter.ui.components.icons.dice.Dice4
import com.shatytskyi.munchcounter.ui.components.icons.dice.Dice5
import com.shatytskyi.munchcounter.ui.components.icons.dice.Dice6
import com.shatytskyi.munchcounter.ui.components.icons.MunchkinIcons
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme
import kotlinx.coroutines.delay

@Composable
fun DiceDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var result by remember { mutableIntStateOf((1..6).random()) }
    var isRolling by remember { mutableStateOf(false) }
    var animationValue by remember { mutableIntStateOf(result) }
    var currentDuration by remember { mutableIntStateOf(1000) }
    val haptic = LocalHapticFeedback.current

    val shake by animateFloatAsState(
        targetValue = if (isRolling) 1f else 0f,
        animationSpec = tween(currentDuration, easing = LinearEasing),
        label = "dice_shake"
    )

    LaunchedEffect(isRolling) {
        if (isRolling) {
            val iterations = currentDuration / 50
            val vibrationInterval = maxOf(iterations / 10, 1)

            repeat(iterations) { i ->
                animationValue = (1..6).random()

                if (i % vibrationInterval == 0) {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                }

                delay(50)
            }

            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            isRolling = false
        }
    }

    MunchkinDialog(
        onDismissRequest = onDismiss,
        title = "Roll the dice",
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 24.dp)
            ) {
                AnimatedVisibility(
                    visible = true,
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(160.dp)
                            .clickable(
                                enabled = !isRolling,
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = {
                                    result = (1..6).random()
                                    currentDuration =
                                        (500..1500).random()
                                    isRolling = true
                                }
                            )
                    ) {
                        MunchkinIcon(
                            imageVector = when (if (isRolling) animationValue else result) {
                                1 -> MunchkinIcons.Dice.Dice1
                                2 -> MunchkinIcons.Dice.Dice2
                                3 -> MunchkinIcons.Dice.Dice3
                                4 -> MunchkinIcons.Dice.Dice4
                                5 -> MunchkinIcons.Dice.Dice5
                                else -> MunchkinIcons.Dice.Dice6
                            },
                            size = 140.dp,
                            tint = MunchkinTheme.colors.primary,
                            modifier = Modifier.graphicsLayer {
                                translationX = if (isRolling)
                                    kotlin.math.sin(shake * 40) * 5f else 0f
                                translationY = if (isRolling)
                                    kotlin.math.cos(shake * 35) * 3f else 0f
                            }
                        )
                    }
                }
            }
        },
        confirmButton = {
            MunchkinText(
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onDismiss
                ),
                text = stringResource(R.string.close)
            )
        },
        modifier = modifier
    )
}
