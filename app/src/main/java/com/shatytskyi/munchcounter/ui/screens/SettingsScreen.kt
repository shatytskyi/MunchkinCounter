package com.shatytskyi.munchcounter.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Brightness4
import androidx.compose.material.icons.outlined.BrightnessAuto
import androidx.compose.material.icons.outlined.BrightnessHigh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.ui.components.APP_BAR_HEIGHT
import com.shatytskyi.munchcounter.ui.components.MunchkinCard
import com.shatytskyi.munchcounter.ui.components.MunchkinIcon
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.components.MunchkinTopAppBar
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

enum class ThemeMode(val displayName: String) {
    LIGHT("Light"),
    AUTO("Auto"),
    DARK("Dark")
}

@Composable
fun SettingsScreen(
    currentThemeMode: ThemeMode = ThemeMode.AUTO,
    onThemeModeChange: (ThemeMode) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val density = LocalDensity.current
    val statusBarHeight = WindowInsets.systemBars.getTop(density)
    val topPadding = remember(statusBarHeight) {
        with(density) { statusBarHeight.toDp() + APP_BAR_HEIGHT.dp + 16.dp }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom)),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = topPadding,
                bottom = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                ThemeSwitchGroup(
                    selectedMode = currentThemeMode,
                    onModeSelected = onThemeModeChange
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MunchkinTheme.colors.background.copy(alpha = 0.95f))
        ) {
            MunchkinTopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top)),
                title = "Settings",
                onBack = onBackClick
            )
        }
    }
}

@Composable
private fun ThemeSwitchGroup(
    selectedMode: ThemeMode,
    onModeSelected: (ThemeMode) -> Unit
) {
    val haptic = LocalHapticFeedback.current

    MunchkinCard(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = MunchkinTheme.colors.background,
        color = MunchkinTheme.colors.primary
    ) {
        Column {
            Spacer(modifier = Modifier.height(24.dp))

            MunchkinText(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Theme",
                style = MunchkinTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MunchkinTheme.colors.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                ThemeMode.entries.forEach { mode ->
                    ThemeOption(
                        mode = mode,
                        isSelected = mode == selectedMode,
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                            onModeSelected(mode)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ThemeOption(
    mode: ThemeMode,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val icon = when (mode) {
        ThemeMode.LIGHT -> Icons.Outlined.BrightnessHigh
        ThemeMode.AUTO -> Icons.Outlined.BrightnessAuto
        ThemeMode.DARK -> Icons.Outlined.Brightness4
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .selectable(
                selected = isSelected,
                onClick = onClick,
                role = Role.RadioButton
            )
            .padding(horizontal = 12.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MunchkinIcon(
            imageVector = icon,
            tint = if (isSelected) MunchkinTheme.colors.primary else MunchkinTheme.colors.grey,
            size = 24.dp
        )

        MunchkinText(
            text = mode.displayName,
            style = MunchkinTheme.typography.labelMedium.copy(
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
            ),
            color = if (isSelected) MunchkinTheme.colors.primary else MunchkinTheme.colors.onBackground
        )
    }
}

@Preview(
    name = "Settings Screen",
    device = Devices.PIXEL_7_PRO,
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun SettingsScreenPreview() {
    MunchkinTheme {
        SettingsScreen(
            currentThemeMode = ThemeMode.AUTO,
            onThemeModeChange = {},
            onBackClick = {}
        )
    }
}
