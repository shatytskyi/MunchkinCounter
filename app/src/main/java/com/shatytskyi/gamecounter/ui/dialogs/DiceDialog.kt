package com.shatytskyi.gamecounter.ui.dialogs

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shatytskyi.gamecounter.R
import com.shatytskyi.gamecounter.ui.components.MunchkinDialog
import com.shatytskyi.gamecounter.ui.components.MunchkinIcon
import com.shatytskyi.gamecounter.ui.components.MunchkinText
import com.shatytskyi.gamecounter.ui.components.icons.MunchkinIcons
import com.shatytskyi.gamecounter.ui.components.icons.dice.Dice1
import com.shatytskyi.gamecounter.ui.components.icons.dice.Dice2
import com.shatytskyi.gamecounter.ui.components.icons.dice.Dice3
import com.shatytskyi.gamecounter.ui.components.icons.dice.Dice4
import com.shatytskyi.gamecounter.ui.components.icons.dice.Dice5
import com.shatytskyi.gamecounter.ui.components.icons.dice.Dice6
import com.shatytskyi.gamecounter.ui.theme.MunchkinTheme
import kotlinx.coroutines.delay
import kotlin.math.sqrt

@Composable
fun DiceDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var result by remember { mutableIntStateOf((1..6).random()) }
    var isRolling by remember { mutableStateOf(false) }
    var animationValue by remember { mutableIntStateOf(result) }
    var currentDuration by remember { mutableIntStateOf(1000) }
    val haptic = LocalHapticFeedback.current
    var rollCount by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    var lastShakeTime by remember { mutableLongStateOf(0L) }

    val shake by animateFloatAsState(
        targetValue = if (isRolling) 1f else 0f,
        animationSpec = tween(currentDuration, easing = LinearEasing),
        label = "dice_shake"
    )

    // Function to trigger dice roll
    fun triggerRoll() {
        if (!isRolling) {
            result = (1..6).random()
            currentDuration = (1000..2000).random()
            isRolling = true
            rollCount++
        }
    }

    // Shake detection setup
    DisposableEffect(Unit) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        val shakeThreshold = 20.0f // Balanced threshold for shake detection

        val sensorListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (!isRolling) { // Only detect shake when not already rolling
                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]

                    val acceleration = sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH

                    if (acceleration > shakeThreshold) {
                        val currentTime = System.currentTimeMillis()

                        // Check if this is a new shake (not continuous)
                        if (currentTime - lastShakeTime > 500) {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            lastShakeTime = currentTime
                            triggerRoll()
                        }
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // Not needed
            }
        }

        accelerometer?.let {
            sensorManager.registerListener(
                sensorListener,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }

        onDispose {
            sensorManager.unregisterListener(sensorListener)
        }
    }

    LaunchedEffect(isRolling) {
        if (isRolling) {
            val iterations = currentDuration / 50
            val vibrationStep = 3 // Vibrate every 3 iterations for better feel

            repeat(iterations) { i ->
                animationValue = (1..6).random()

                // More frequent, lighter vibrations during roll
                if (i % vibrationStep == 0) {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                }

                delay(50)
            }

            // Strong vibration at the end
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            isRolling = false
        }
    }

    MunchkinDialog(
        onDismissRequest = onDismiss,
        title = stringResource(R.string.roll_the_dice),
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
                                    triggerRoll()
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

                MunchkinText(
                    text = stringResource(R.string.dice_hint),
                    style = MunchkinTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MunchkinTheme.colors.grey,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .padding(horizontal = 16.dp)
                )
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
