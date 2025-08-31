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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
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
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.shatytskyi.munchcounter.ui.components.MunchkinDialog
import com.shatytskyi.munchcounter.ui.components.MunchkinIcon
import com.shatytskyi.munchcounter.ui.components.MunchkinIconTextButton
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.components.icons.Dice1
import com.shatytskyi.munchcounter.ui.components.icons.Dice2
import com.shatytskyi.munchcounter.ui.components.icons.Dice3
import com.shatytskyi.munchcounter.ui.components.icons.Dice4
import com.shatytskyi.munchcounter.ui.components.icons.Dice5
import com.shatytskyi.munchcounter.ui.components.icons.Dice6
import com.shatytskyi.munchcounter.ui.components.icons.MunchkinIcons
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

@Composable
fun DiceDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var result by remember { mutableIntStateOf((1..6).random()) }
    var isRolling by remember { mutableStateOf(false) }
    var animationValue by remember { mutableIntStateOf(result) }
    
    val shake by animateFloatAsState(
        targetValue = if (isRolling) 1f else 0f,
        animationSpec = tween(1000, easing = LinearEasing),
        label = "dice_shake"
    )
    
    LaunchedEffect(isRolling) {
        if (isRolling) {
            repeat(20) { // Change value 20 times during animation
                animationValue = (1..6).random()
                delay(50) // 50ms * 20 = 1000ms total
            }
            isRolling = false
        }
    }

    MunchkinDialog(
        onDismissRequest = onDismiss,
        title = "Roll Dice",
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(40.dp),
                modifier = Modifier.padding(vertical = 32.dp)
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
                            .size(140.dp)
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
                            size = 120.dp,
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

                MunchkinIconTextButton(
                    text = "Reroll",
                    icon = Icons.Default.Casino,
                    onClick = { 
                        if (!isRolling) {
                            result = (1..6).random()
                            isRolling = true
                        }
                    }
                )
            }
        },
        confirmButton = {
            MunchkinText(
                modifier = Modifier.clickable(
                    onClick = onDismiss
                ),
                text = "Close"
            )
        },
        modifier = modifier
    )
}
