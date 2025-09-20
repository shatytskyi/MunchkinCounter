package com.shatytskyi.gamecounter.ui.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Brightness4
import androidx.compose.material.icons.outlined.BrightnessAuto
import androidx.compose.material.icons.outlined.BrightnessHigh
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shatytskyi.gamecounter.R
import com.shatytskyi.gamecounter.ui.components.APP_BAR_HEIGHT
import com.shatytskyi.gamecounter.ui.components.MunchkinHorizontalDivider
import com.shatytskyi.gamecounter.ui.components.MunchkinIconTextButton
import com.shatytskyi.gamecounter.ui.components.MunchkinText
import com.shatytskyi.gamecounter.ui.components.MunchkinTopAppBar
import com.shatytskyi.gamecounter.ui.components.icons.ColorsOff
import com.shatytskyi.gamecounter.ui.components.icons.ColorsOn
import com.shatytskyi.gamecounter.ui.components.icons.Language
import com.shatytskyi.gamecounter.ui.components.icons.MunchkinIcons
import com.shatytskyi.gamecounter.ui.theme.MunchkinTheme

enum class ThemeMode {
    LIGHT,
    AUTO,
    DARK
}

@Composable
fun SettingsScreen(
    currentThemeMode: ThemeMode = ThemeMode.AUTO,
    dynamicColors: Boolean = false,
    onThemeModeChange: (ThemeMode) -> Unit = {},
    onDynamicColorsChange: (Boolean) -> Unit = {},
    onBackClick: () -> Unit = {},
    onLanguageClick: () -> Unit = {},
    onRateAppClick: () -> Unit = {},
    onShareAppClick: () -> Unit = {}
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
                    onModeSelected = { mode ->
                        onThemeModeChange(mode)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                ColorSwitchGroup(
                    dynamicColors = dynamicColors,
                    onDynamicColorsChange = { enabled ->
                        onDynamicColorsChange(enabled)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                MunchkinHorizontalDivider(
                    color = MunchkinTheme.colors.grey.copy(alpha = 0.3f)
                )
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                LanguageSelector(
                    onClick = {
                        onLanguageClick()
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                MunchkinHorizontalDivider(
                    color = MunchkinTheme.colors.grey.copy(alpha = 0.3f)
                )
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                OtherSettingsSection(
                    onRateAppClick = onRateAppClick,
                    onShareAppClick = onShareAppClick
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
            style = MunchkinTheme.typography.headlineSmall,
            color = MunchkinTheme.colors.onBackground,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
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
                    rippleColor = null,
                    iconTint = if (selectedMode == mode) MunchkinTheme.colors.primary else MunchkinTheme.colors.grey,
                    textColor = if (selectedMode == mode) MunchkinTheme.colors.primary else MunchkinTheme.colors.onBackground,
                    textStyle = if (selectedMode == mode) MunchkinTheme.typography.labelLarge else MunchkinTheme.typography.labelMedium,
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
            style = MunchkinTheme.typography.headlineSmall,
            color = MunchkinTheme.colors.onBackground,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MunchkinIconTextButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    onDynamicColorsChange(false)
                },
                icon = MunchkinIcons.ColorsOn,
                text = stringResource(R.string.colors_app),
                iconTint = if (!dynamicColors) MunchkinTheme.colors.primary else MunchkinTheme.colors.grey,
                textColor = if (!dynamicColors) MunchkinTheme.colors.primary else MunchkinTheme.colors.onBackground,
                textStyle = if (!dynamicColors) MunchkinTheme.typography.labelLarge else MunchkinTheme.typography.labelMedium,
                bounded = false
            )

            MunchkinIconTextButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    onDynamicColorsChange(true)
                },
                icon = MunchkinIcons.ColorsOff,
                text = stringResource(R.string.colors_dynamic),
                iconTint = if (dynamicColors) MunchkinTheme.colors.primary else MunchkinTheme.colors.grey,
                textColor = if (dynamicColors) MunchkinTheme.colors.primary else MunchkinTheme.colors.onBackground,
                textStyle = if (dynamicColors) MunchkinTheme.typography.labelLarge else MunchkinTheme.typography.labelMedium,
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
            style = MunchkinTheme.typography.headlineSmall,
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

@Composable
private fun OtherSettingsSection(
    onRateAppClick: () -> Unit,
    onShareAppClick: () -> Unit
) {
    val haptic = LocalHapticFeedback.current

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MunchkinText(
            text = stringResource(R.string.other_settings),
            style = MunchkinTheme.typography.headlineSmall,
            color = MunchkinTheme.colors.onBackground,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        MunchkinIconTextButton(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                onRateAppClick()
            },
            icon = Icons.Outlined.Star,
            text = stringResource(R.string.rate_dialog_title),
            modifier = Modifier.fillMaxWidth(),
            rippleColor = null,
            iconTint = MunchkinTheme.colors.grey,
            textColor = MunchkinTheme.colors.onBackground,
            textStyle = MunchkinTheme.typography.bodyMedium,
            bounded = false,
            contentPadding = 16.dp
        )

        MunchkinIconTextButton(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                onShareAppClick()
            },
            icon = Icons.Outlined.Share,
            text = stringResource(R.string.share_app),
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
