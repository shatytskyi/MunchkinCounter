package com.shatytskyi.gamecounter.ui.screens

import android.media.AudioFormat
import android.media.AudioTrack
import android.view.WindowManager
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.shatytskyi.gamecounter.R
import com.shatytskyi.gamecounter.data.TimerPreferences
import com.shatytskyi.gamecounter.ui.components.MunchkinHorizontalDivider
import com.shatytskyi.gamecounter.ui.components.MunchkinText
import com.shatytskyi.gamecounter.ui.components.MunchkinTopAppBar
import com.shatytskyi.gamecounter.ui.theme.MunchkinTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.compose.koinInject
import kotlin.math.PI
import kotlin.math.sin

private const val SOUND_DURATION = 200
private const val START_FREQUENCY = 523
private const val TICK_FREQUENCY = 659
private const val END_FREQUENCY = 783

@Composable
fun TimerScreen(
    onBack: () -> Unit
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
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.onSizeChanged { size ->
            containerWidth = size.width
        }
    ) {
        item {
            Spacer(modifier = Modifier.width(16.dp))
        }
        items(10) { index ->
            val seconds = index + 1
            val isSelected = selectedSeconds == seconds

            val borderWidth by animateFloatAsState(
                targetValue = if (isSelected) 2f else 1f,
                animationSpec = tween(durationMillis = 200),
                label = "border_width"
            )

            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.1f else 1f,
                animationSpec = tween(durationMillis = 200),
                label = "item_scale"
            )

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale
                    )
                    .clip(CircleShape)
                    .border(
                        width = borderWidth.dp,
                        color = if (isSelected) MunchkinTheme.colors.primary
                                else MunchkinTheme.colors.grey.copy(alpha = 0.4f),
                        shape = CircleShape
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
                    color = if (isSelected)
                        MunchkinTheme.colors.primary
                    else MunchkinTheme.colors.onBackground.copy(alpha = 0.7f),
                    style = MunchkinTheme.typography.labelLarge.copy(
                        fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                        fontSize = if (isSelected) 18.sp else 16.sp
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
    val flashAlpha by animateFloatAsState(
        targetValue = if (shouldFlash) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "flash_alpha"
    )

    val progress =
        if (selectedSeconds > 0 && isRunning) currentSeconds.toFloat() / selectedSeconds else 0f

    val backgroundColor by animateColorAsState(
        targetValue = when {
            shouldFlash -> MunchkinTheme.colors.secondary
            isRunning -> lerp(
                MunchkinTheme.colors.primary,
                MunchkinTheme.colors.secondary,
                1f - progress
            )
            else -> MunchkinTheme.colors.primary
        },
        animationSpec = tween(durationMillis = 300),
        label = "background_color"
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .border(
                width = if (shouldFlash) 3.dp else 2.dp,
                color = if (shouldFlash)
                    MunchkinTheme.colors.secondary.copy(alpha = flashAlpha)
                else backgroundColor,
                shape = RoundedCornerShape(24.dp)
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onToggle() },
        contentAlignment = Alignment.Center
    ) {
        if (shouldFlash) {
            MunchkinText(
                text = stringResource(R.string.timer_finished),
                color = MunchkinTheme.colors.secondary.copy(alpha = flashAlpha),
                style = MunchkinTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )
        } else if (isRunning) {
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
    suspend fun playBeep(frequency: Int) {
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
                samples[i] = (waveform * Short.MAX_VALUE * amplitude).toInt().toShort()

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
                        .setUsage(android.media.AudioAttributes.USAGE_MEDIA)
                        .setContentType(android.media.AudioAttributes.CONTENT_TYPE_MUSIC)
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
    val timerPreferences = koinInject<TimerPreferences>()
    val savedSeconds by timerPreferences.selectedSeconds.collectAsState(initial = 5)
    var selectedSeconds by remember(savedSeconds) { mutableIntStateOf(savedSeconds) }
    var currentSeconds by remember { mutableIntStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }
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
                timerLogic.playBeep(TICK_FREQUENCY)
            } else {
                isRunning = false
                shouldFlash = true
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                timerLogic.playBeep(END_FREQUENCY)
            }
        }
    }

    LaunchedEffect(shouldFlash) {
        if (shouldFlash) {
            delay(1500L)
            shouldFlash = false
        }
    }

    return TimerState(
        selectedSeconds = selectedSeconds,
        currentSeconds = currentSeconds,
        isRunning = isRunning,
        shouldFlash = shouldFlash,
        onSecondsSelected = { seconds ->
            if (!isRunning) {
                selectedSeconds = seconds
                scope.launch {
                    timerPreferences.setSelectedSeconds(seconds)
                }
            }
        },
        onTimerToggle = {
            if (isRunning) {
                isRunning = false
                currentSeconds = 0
            } else {
                currentSeconds = selectedSeconds
                isRunning = true
                haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                scope.launch {
                    timerLogic.playBeep(START_FREQUENCY)
                }
            }
        }
    )
}

private data class TimerState(
    val selectedSeconds: Int,
    val currentSeconds: Int,
    val isRunning: Boolean,
    val shouldFlash: Boolean,
    val onSecondsSelected: (Int) -> Unit,
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
