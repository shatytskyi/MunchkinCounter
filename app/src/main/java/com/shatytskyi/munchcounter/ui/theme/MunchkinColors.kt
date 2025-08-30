package com.shatytskyi.munchcounter.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class MunchkinColors(
    // Primary colors
    val primary: Color = Color(0xFF009EB3),
    val onPrimary: Color = Color(0xFFF3F3F3),
    val primaryContainer: Color = Color(0xFFB3E5FC),
    val onPrimaryContainer: Color = Color(0xFF151515),
    
    // Secondary colors
    val secondary: Color = Color(0xFFAF9D15),
    val onSecondary: Color = Color(0xFFF3F3F3),
    val secondaryContainer: Color = Color(0xFFFFF9C4),
    val onSecondaryContainer: Color = Color(0xFF151515),
    
    // Tertiary colors (green)
    val tertiary: Color = Color(0xFF218E3C),
    val onTertiary: Color = Color(0xFFF3F3F3),
    val tertiaryContainer: Color = Color(0xFFE8F5E8),
    val onTertiaryContainer: Color = Color(0xFF151515),
    
    // Error colors
    val error: Color = Color(0xFFA51B1B),
    val onError: Color = Color(0xFFF3F3F3),
    val errorContainer: Color = Color(0xFFFFDAD6),
    val onErrorContainer: Color = Color(0xFF410002),
    
    // Background colors
    val background: Color = Color(0xFFF3F3F3),
    val onBackground: Color = Color(0xFF151515),
    
    // Surface colors
    val surface: Color = Color(0xFFF3F3F3),
    val onSurface: Color = Color(0xFF151515),
    val surfaceContainer: Color = Color(0xFFF5F5F5),
    val surfaceContainerLow: Color = Color(0xFFE8E8E8),
    val onSurfaceVariant: Color = Color(0xFF616161),
    
    // Outline
    val outline: Color = Color(0xFF616161),
)