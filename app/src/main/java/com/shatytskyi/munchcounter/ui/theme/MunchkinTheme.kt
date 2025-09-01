package com.shatytskyi.munchcounter.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.shatytskyi.munchcounter.ui.screens.ThemeMode

val LocalMunchkinColors = staticCompositionLocalOf { MunchkinColors() }
val LocalMunchkinTypography = staticCompositionLocalOf { MunchkinTypography() }

@Composable
fun MunchkinTheme(
    themeMode: ThemeMode = ThemeMode.AUTO,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val systemInDarkTheme = isSystemInDarkTheme()
    val darkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.AUTO -> systemInDarkTheme
    }
    val colors = when {
        dynamicColor -> {
            val context = LocalContext.current
            val dynamicColorScheme = if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }

            MunchkinColors(
                primary = dynamicColorScheme.primary,
                secondary = dynamicColorScheme.secondary,
                green = dynamicColorScheme.tertiary,
                red = dynamicColorScheme.error,
                background = dynamicColorScheme.background,
                onBackground = dynamicColorScheme.onBackground,
                grey = dynamicColorScheme.outline,
            )
        }

        darkTheme -> DarkMunchkinColors
        else -> LightMunchkinColors
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as android.app.Activity).window
            WindowCompat.setDecorFitsSystemWindows(window, false)
            window.statusBarColor = android.graphics.Color.TRANSPARENT
            window.navigationBarColor = android.graphics.Color.TRANSPARENT
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = !darkTheme
            insetsController.isAppearanceLightNavigationBars = !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalMunchkinColors provides colors,
        LocalMunchkinTypography provides MunchkinTypography()
    ) {
        Box(
            modifier = Modifier.background(colors.background)
        ) {
            content()
        }
    }
}

object MunchkinTheme {
    val colors: MunchkinColors
        @Composable
        get() = LocalMunchkinColors.current

    val typography: MunchkinTypography
        @Composable
        get() = LocalMunchkinTypography.current
}
