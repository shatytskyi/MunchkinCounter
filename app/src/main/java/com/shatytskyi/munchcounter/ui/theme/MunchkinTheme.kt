package com.shatytskyi.munchcounter.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val LocalMunchkinColors = staticCompositionLocalOf { MunchkinColors() }
val LocalMunchkinTypography = staticCompositionLocalOf { MunchkinTypography() }

@Composable
fun MunchkinTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as android.app.Activity).window
            WindowCompat.setDecorFitsSystemWindows(window, false)
            window.statusBarColor = android.graphics.Color.TRANSPARENT
            window.navigationBarColor = android.graphics.Color.TRANSPARENT
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = true
            insetsController.isAppearanceLightNavigationBars = true
        }
    }

    CompositionLocalProvider(
        LocalMunchkinColors provides MunchkinColors(),
        LocalMunchkinTypography provides MunchkinTypography(),
        content = content
    )
}

object MunchkinTheme {
    val colors: MunchkinColors
        @Composable
        get() = LocalMunchkinColors.current

    val typography: MunchkinTypography
        @Composable
        get() = LocalMunchkinTypography.current
}
