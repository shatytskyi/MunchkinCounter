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
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Brightness4
import androidx.compose.material.icons.outlined.BrightnessAuto
import androidx.compose.material.icons.outlined.BrightnessHigh
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.TextFields
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.ui.components.APP_BAR_HEIGHT
import com.shatytskyi.munchcounter.ui.components.MunchkinIconTextButton
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.components.MunchkinTopAppBar
import com.shatytskyi.munchcounter.ui.components.icons.ColorsOff
import com.shatytskyi.munchcounter.ui.components.icons.ColorsOn
import com.shatytskyi.munchcounter.ui.components.icons.Font
import com.shatytskyi.munchcounter.ui.components.icons.FontOff
import com.shatytskyi.munchcounter.ui.components.icons.Language
import com.shatytskyi.munchcounter.ui.components.icons.MunchkinIcons
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

enum class ThemeMode {
    LIGHT,
    AUTO,
    DARK
}

@OptIn(androidx.compose.animation.ExperimentalSharedTransitionApi::class)
@Composable
fun SettingsScreen(
    currentThemeMode: ThemeMode = ThemeMode.AUTO,
    dynamicColors: Boolean = false,
    systemFont: Boolean = false,
    onThemeModeChange: (ThemeMode) -> Unit = {},
    onDynamicColorsChange: (Boolean) -> Unit = {},
    onSystemFontChange: (Boolean) -> Unit = {},
    onBackClick: () -> Unit = {},
    onLanguageClick: () -> Unit = {}
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
            )
        ) {
            item {
                ThemeSwitchGroup(
                    selectedMode = currentThemeMode,
                    onModeSelected = onThemeModeChange
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                ColorSwitchGroup(
                    dynamicColors = dynamicColors,
                    onDynamicColorsChange = onDynamicColorsChange
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                FontSwitchGroup(
                    systemFont = systemFont,
                    onSystemFontChange = onSystemFontChange
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(
                    color = MunchkinTheme.colors.grey.copy(alpha = 0.3f)
                )
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                LanguageSelector(
                    onClick = onLanguageClick
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
                title = stringResource(R.string.settings),
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

    Column {
        Spacer(modifier = Modifier.height(16.dp))

        MunchkinText(
            text = stringResource(R.string.theme),
            style = MunchkinTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = MunchkinTheme.colors.onBackground,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ThemeMode.entries.forEach { mode ->
                MunchkinIconTextButton(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                        onModeSelected(mode)
                    },
                    icon = when (mode) {
                        ThemeMode.LIGHT -> Icons.Outlined.BrightnessHigh
                        ThemeMode.AUTO -> Icons.Outlined.BrightnessAuto
                        ThemeMode.DARK -> Icons.Outlined.Brightness4
                    },
                    text = when (mode) {
                        ThemeMode.LIGHT -> stringResource(R.string.theme_light)
                        ThemeMode.AUTO -> stringResource(R.string.theme_auto)
                        ThemeMode.DARK -> stringResource(R.string.theme_dark)
                    },
                    modifier = Modifier.weight(1f),
                    rippleColor = null,
                    iconTint = if (selectedMode == mode) MunchkinTheme.colors.primary else MunchkinTheme.colors.grey,
                    textColor = if (selectedMode == mode) MunchkinTheme.colors.primary else MunchkinTheme.colors.onBackground,
                    textStyle = MunchkinTheme.typography.labelMedium.copy(
                        fontWeight = if (selectedMode == mode) FontWeight.Medium else FontWeight.Normal
                    ),
                    bounded = false
                )
            }
        }

    }
}

@Composable
private fun ColorSwitchGroup(
    dynamicColors: Boolean,
    onDynamicColorsChange: (Boolean) -> Unit
) {
    val haptic = LocalHapticFeedback.current

    Column {
        Spacer(modifier = Modifier.height(16.dp))

        MunchkinText(
            text = stringResource(R.string.colors),
            style = MunchkinTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = MunchkinTheme.colors.onBackground,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MunchkinIconTextButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    onDynamicColorsChange(false)
                },
                icon = MunchkinIcons.ColorsOn,
                text = stringResource(R.string.colors_app),
                modifier = Modifier.weight(1f),
                iconTint = if (!dynamicColors) MunchkinTheme.colors.primary else MunchkinTheme.colors.grey,
                textColor = if (!dynamicColors) MunchkinTheme.colors.primary else MunchkinTheme.colors.onBackground,
                textStyle = MunchkinTheme.typography.labelMedium.copy(
                    fontWeight = if (!dynamicColors) FontWeight.Medium else FontWeight.Normal
                ),
                bounded = false
            )

            MunchkinIconTextButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    onDynamicColorsChange(true)
                },
                icon = MunchkinIcons.ColorsOff,
                text = stringResource(R.string.colors_dynamic),
                modifier = Modifier.weight(1f),
                iconTint = if (dynamicColors) MunchkinTheme.colors.primary else MunchkinTheme.colors.grey,
                textColor = if (dynamicColors) MunchkinTheme.colors.primary else MunchkinTheme.colors.onBackground,
                textStyle = MunchkinTheme.typography.labelMedium.copy(
                    fontWeight = if (dynamicColors) FontWeight.Medium else FontWeight.Normal
                ),
                bounded = false
            )
        }
    }
}

@Composable
private fun FontSwitchGroup(
    systemFont: Boolean,
    onSystemFontChange: (Boolean) -> Unit
) {
    val haptic = LocalHapticFeedback.current

    Column {
        Spacer(modifier = Modifier.height(16.dp))

        MunchkinText(
            text = stringResource(R.string.fonts),
            style = MunchkinTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = MunchkinTheme.colors.onBackground,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MunchkinIconTextButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    onSystemFontChange(false)
                },
                icon = MunchkinIcons.Font,
                text = stringResource(R.string.fonts_app),
                modifier = Modifier.weight(1f),
                iconTint = if (!systemFont) MunchkinTheme.colors.primary else MunchkinTheme.colors.grey,
                textColor = if (!systemFont) MunchkinTheme.colors.primary else MunchkinTheme.colors.onBackground,
                rippleColor = null,
                textStyle = MunchkinTheme.typography.labelMedium.copy(
                    fontWeight = if (!systemFont) FontWeight.Medium else FontWeight.Normal
                ),
                bounded = false
            )

            MunchkinIconTextButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    onSystemFontChange(true)
                },
                icon = MunchkinIcons.FontOff,
                text = stringResource(R.string.fonts_system),
                modifier = Modifier.weight(1f),
                iconTint = if (systemFont) MunchkinTheme.colors.primary else MunchkinTheme.colors.grey,
                textColor = if (systemFont) MunchkinTheme.colors.primary else MunchkinTheme.colors.onBackground,
                rippleColor = null,
                textStyle = MunchkinTheme.typography.labelMedium.copy(
                    fontWeight = if (systemFont) FontWeight.Medium else FontWeight.Normal
                ),
                bounded = false
            )
        }
    }
}

@Composable
private fun LanguageSelector(
    onClick: () -> Unit
) {
    val haptic = LocalHapticFeedback.current

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MunchkinText(
            text = stringResource(R.string.language),
            style = MunchkinTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = MunchkinTheme.colors.onBackground,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        MunchkinIconTextButton(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                onClick()
            },
            icon = MunchkinIcons.Language,
            text = stringResource(R.string.language_description),
            modifier = Modifier.fillMaxWidth(),
            rippleColor = null,
            iconTint = MunchkinTheme.colors.grey,
            textColor = MunchkinTheme.colors.onBackground,
            textStyle = MunchkinTheme.typography.bodyMedium,
            bounded = false,
            contentPadding = 16.dp
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
            dynamicColors = false,
            onThemeModeChange = {},
            onDynamicColorsChange = {},
            onBackClick = {},
            onLanguageClick = {}
        )
    }
}
