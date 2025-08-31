package com.shatytskyi.munchcounter.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class MunchkinColors(
    // Primary colors
    val primary: Color = Color(0xFF009EB3),

    // Secondary colors
    val secondary: Color = Color(0xFFAF9D15),

    // Tertiary colors (green)
    val green: Color = Color(0xFF218E3C),

    // Error colors
    val red: Color = Color(0xFFA51B1B),

    // Background colors
    val background: Color = Color(0XFFfff9ed),
    val onBackground: Color = Color(0xFF151515),

    // Outline
    val grey: Color = Color(0xFF616161),
)

// Dark theme colors
val DarkMunchkinColors = MunchkinColors(
    primary = Color(0xFF7AB8C4),
    secondary = Color(0xFFBFA76A),
    green = Color(0xFF4caf50),
    red = Color(0xFFef5350),
    background = Color(0xFF1a1a1a),
    onBackground = Color(0xFFf5f5f5),
    grey = Color(0xFF9e9e9e),
)

// Light theme colors (default)
val LightMunchkinColors = MunchkinColors()
