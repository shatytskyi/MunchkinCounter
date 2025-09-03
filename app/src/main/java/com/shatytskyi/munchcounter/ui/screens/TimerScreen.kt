package com.shatytskyi.munchcounter.ui.screens

import android.media.AudioFormat
import android.media.AudioTrack
import android.view.WindowManager
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.VolumeOff
import androidx.compose.material.icons.automirrored.outlined.VolumeUp
import com.shatytskyi.munchcounter.ui.components.MunchkinHorizontalDivider
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.ui.components.MunchkinIcon
import com.shatytskyi.munchcounter.ui.components.MunchkinIconButton
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.components.MunchkinTopAppBar
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.PI
import kotlin.math.sin

private const val SOUND_DURATION = 200
private const val START_FREQUENCY = 523
private const val TICK_FREQUENCY = 659
private const val END_FREQUENCY = 783
private const val DEFAULT_VOLUME = 0.3f

@OptIn(androidx.compose.animation.ExperimentalSharedTransitionApi::class)
@Composable
fun TimerScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val activity = context as? androidx.activity.ComponentActivity
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    DisposableEffect(Unit) {
        onDispose {
            val activity = context as? androidx.activity.ComponentActivity
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    TimerContent(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        onBack = onBack
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun TimerContent(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val timerState = useTimerLogic()
    val haptic = LocalHapticFeedback.current

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MunchkinTopAppBar(
            title = stringResource(R.string.timer_title),
            onBack = onBack
        )

        Spacer(modifier = Modifier.height(16.dp))


        MunchkinText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = stringResource(R.string.timer_select_seconds),
            style = MunchkinTheme.typography.bodyMedium,
            color = MunchkinTheme.colors.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        SecondsSelector(
            selectedSeconds = timerState.selectedSeconds,
            onSecondsSelected = { seconds ->
                haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                timerState.onSecondsSelected(seconds)
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Volume selector
        MunchkinText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = stringResource(R.string.volume),
            style = MunchkinTheme.typography.labelMedium,
            color = MunchkinTheme.colors.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(36.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(MunchkinTheme.colors.grey.copy(alpha = 0.2f))
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Slider(
                    modifier = Modifier.height(24.dp),
                    value = timerState.volume,
                    onValueChange = timerState.onVolumeChanged,
                    valueRange = 0.01f..1f,
                    enabled = timerState.isSoundEnabled,
                    colors = SliderDefaults.colors(
                        thumbColor = if (timerState.isSoundEnabled) MunchkinTheme.colors.primary else MunchkinTheme.colors.grey,
                        activeTrackColor = if (timerState.isSoundEnabled) MunchkinTheme.colors.primary else MunchkinTheme.colors.grey,
                        inactiveTrackColor = MunchkinTheme.colors.grey.copy(alpha = 0.5f),
                        disabledThumbColor = MunchkinTheme.colors.grey.copy(alpha = 0.5f),
                        disabledActiveTrackColor = MunchkinTheme.colors.grey.copy(alpha = 0.3f),
                        disabledInactiveTrackColor = MunchkinTheme.colors.grey.copy(alpha = 0.2f)
                    ),
                )
            }
            
            MunchkinIconButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    timerState.onSoundToggle()
                },
                size = 36.dp
            ) {
                MunchkinIcon(
                    imageVector = if (timerState.isSoundEnabled) Icons.AutoMirrored.Outlined.VolumeUp else Icons.AutoMirrored.Outlined.VolumeOff,
                    tint = if (timerState.isSoundEnabled) MunchkinTheme.colors.primary else MunchkinTheme.colors.grey,
                    size = 20.dp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        MunchkinHorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        TimerButton(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            currentSeconds = timerState.currentSeconds,
            isRunning = timerState.isRunning,
            selectedSeconds = timerState.selectedSeconds,
            shouldFlash = timerState.shouldFlash,
            onToggle = {
                haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                timerState.onTimerToggle()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun SecondsSelector(
    selectedSeconds: Int,
    onSecondsSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = androidx.compose.foundation.lazy.rememberLazyListState()
    val density = LocalDensity.current
    var containerWidth by remember { mutableIntStateOf(0) }

    LaunchedEffect(selectedSeconds, containerWidth) {
        if (containerWidth > 0) {
            val itemWidthPx = with(density) { 48.dp.toPx() }.toInt()
            val centerOffset = (containerWidth / 2) - (itemWidthPx / 2)
            val targetIndex = selectedSeconds
            listState.animateScrollToItem(targetIndex, scrollOffset = -centerOffset)
        }
    }

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.onSizeChanged { size ->
            containerWidth = size.width
        }
    ) {
        item {
            Spacer(modifier = Modifier.width(16.dp))
        }
        items(10) { index ->
            val seconds = index + 1
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        if (selectedSeconds == seconds) MunchkinTheme.colors.primary
                        else MunchkinTheme.colors.grey.copy(alpha = 0.3f)
                    )
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onSecondsSelected(seconds)
                    },
                contentAlignment = Alignment.Center
            ) {
                MunchkinText(
                    text = seconds.toString(),
                    color = if (selectedSeconds == seconds)
                        Color.White
                    else MunchkinTheme.colors.onBackground,
                    style = MunchkinTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        item {
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Composable
private fun TimerButton(
    modifier: Modifier = Modifier,
    currentSeconds: Int,
    isRunning: Boolean,
    selectedSeconds: Int,
    shouldFlash: Boolean,
    onToggle: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (shouldFlash) 1.1f else 1f,
        animationSpec = tween(durationMillis = 500),
        label = "flash_scale"
    )

    val progress =
        if (selectedSeconds > 0 && isRunning) currentSeconds.toFloat() / selectedSeconds else 0f
    val backgroundColor by animateColorAsState(
        targetValue = if (shouldFlash) MunchkinTheme.colors.secondary
        else if (isRunning) lerp(
            MunchkinTheme.colors.primary,
            MunchkinTheme.colors.secondary,
            1f - progress
        )
        else MunchkinTheme.colors.primary,
        animationSpec = tween(durationMillis = 300),
        label = "background_color"
    )

    Box(
        modifier = modifier
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale
            )
            .clip(RoundedCornerShape(24.dp))
            .border(width = 2.dp, color = backgroundColor, shape = RoundedCornerShape(24.dp))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onToggle() },
        contentAlignment = Alignment.Center
    ) {
        if (isRunning) {
            MunchkinText(
                text = currentSeconds.toString(),
                color = backgroundColor,
                style = MunchkinTheme.typography.displayLarge.copy(
                    fontSize = 120.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )
        } else {
            MunchkinText(
                modifier = Modifier.padding(horizontal = 24.dp),
                text = stringResource(R.string.timer_start),
                color = backgroundColor,
                style = MunchkinTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

private class TimerLogic {
    suspend fun playBeep(frequency: Int, volume: Float) {
        withContext(Dispatchers.IO) {
            val sampleRate = 44100
            val numSamples = (sampleRate * SOUND_DURATION / 1000.0).toInt()
            val samples = ShortArray(numSamples)
            val increment = 2.0 * PI * frequency / sampleRate
            var angle = 0.0

            val fadeInSamples = numSamples / 10
            val fadeOutSamples = numSamples / 10

            for (i in samples.indices) {
                var amplitude = 1.0

                if (i < fadeInSamples) {
                    amplitude = i.toDouble() / fadeInSamples
                } else if (i > numSamples - fadeOutSamples) {
                    amplitude = (numSamples - i).toDouble() / fadeOutSamples
                }

                val fundamental = sin(angle) * 0.6
                val harmonic2 = sin(angle * 2) * 0.2
                val harmonic3 = sin(angle * 3) * 0.1
                val harmonic4 = sin(angle * 4) * 0.05

                val waveform = fundamental + harmonic2 + harmonic3 + harmonic4
                samples[i] = (waveform * Short.MAX_VALUE * volume * amplitude).toInt().toShort()

                angle += increment
                angle %= (2.0 * PI)
            }

            val minBufferSize = AudioTrack.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )

            val audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    android.media.AudioAttributes.Builder()
                        .setUsage(android.media.AudioAttributes.USAGE_ALARM)
                        .setContentType(android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .build()
                )
                .setBufferSizeInBytes(kotlin.math.max(minBufferSize, samples.size * 2))
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            audioTrack.write(samples, 0, samples.size)
            audioTrack.play()

            while (audioTrack.playState == AudioTrack.PLAYSTATE_PLAYING) {
                delay(10)
            }

            audioTrack.stop()
            audioTrack.release()
        }
    }
}

@Composable
private fun useTimerLogic(): TimerState {
    var selectedSeconds by remember { mutableIntStateOf(5) }
    var currentSeconds by remember { mutableIntStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }
    var volume by remember { mutableFloatStateOf(DEFAULT_VOLUME) }
    var isSoundEnabled by remember { mutableStateOf(true) }
    var shouldFlash by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val timerLogic = remember { TimerLogic() }
    val haptic = LocalHapticFeedback.current

    LaunchedEffect(isRunning, currentSeconds) {
        if (isRunning && currentSeconds > 0) {
            delay(1000L)
            currentSeconds -= 1
            if (currentSeconds > 0) {
                haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                if (isSoundEnabled) {
                    timerLogic.playBeep(TICK_FREQUENCY, volume)
                }
            } else {
                isRunning = false
                shouldFlash = true
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                if (isSoundEnabled) {
                    timerLogic.playBeep(END_FREQUENCY, volume)
                }
            }
        }
    }

    LaunchedEffect(shouldFlash) {
        if (shouldFlash) {
            delay(500L)
            shouldFlash = false
        }
    }

    return TimerState(
        selectedSeconds = selectedSeconds,
        currentSeconds = currentSeconds,
        isRunning = isRunning,
        volume = volume,
        isSoundEnabled = isSoundEnabled,
        shouldFlash = shouldFlash,
        onSecondsSelected = { seconds ->
            if (!isRunning) {
                selectedSeconds = seconds
            }
        },
        onVolumeChanged = { newVolume ->
            volume = newVolume
        },
        onSoundToggle = {
            isSoundEnabled = !isSoundEnabled
        },
        onTimerToggle = {
            if (isRunning) {
                isRunning = false
                currentSeconds = 0
            } else {
                currentSeconds = selectedSeconds
                isRunning = true
                haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                if (isSoundEnabled) {
                    scope.launch {
                        timerLogic.playBeep(START_FREQUENCY, volume)
                    }
                }
            }
        }
    )
}

private data class TimerState(
    val selectedSeconds: Int,
    val currentSeconds: Int,
    val isRunning: Boolean,
    val volume: Float,
    val isSoundEnabled: Boolean,
    val shouldFlash: Boolean,
    val onSecondsSelected: (Int) -> Unit,
    val onVolumeChanged: (Float) -> Unit,
    val onSoundToggle: () -> Unit,
    val onTimerToggle: () -> Unit
)

@Preview(
    name = "Timer Screen - Ready",
    device = Devices.PIXEL_4,
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun TimerScreenPreview() {
    MunchkinTheme {
        TimerScreen(
            onBack = {}
        )
    }
}
