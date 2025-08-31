package com.shatytskyi.munchcounter.ui.components

import androidx.compose.foundation.Indication
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun munchkinRipple(
    bounded: Boolean = true,
    radius: Dp = Dp.Unspecified,
    color: Color = Color.Unspecified
): Indication {
    return ripple(
        bounded = bounded,
        radius = radius,
        color = color
    )
}
