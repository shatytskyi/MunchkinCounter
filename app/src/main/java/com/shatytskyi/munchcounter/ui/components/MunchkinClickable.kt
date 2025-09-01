package com.shatytskyi.munchcounter.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun Modifier.munchkinClickable(
    enabled: Boolean = true,
    bounded: Boolean = true,
    radius: Dp = Dp.Unspecified,
    rippleColor: Color? = null,
    onClick: () -> Unit
): Modifier = this.clickable(
    interactionSource = remember { MutableInteractionSource() },
    indication = rippleColor?.let {
        munchkinRipple(
            bounded = bounded,
            radius = radius,
            color = rippleColor
        )
    },
    enabled = enabled,
    onClick = onClick
)

@Composable
fun Modifier.munchkinClickableDebounced(
    enabled: Boolean = true,
    bounded: Boolean = true,
    radius: Dp = Dp.Unspecified,
    color: Color = Color.Unspecified,
    debounceTime: Long = 300L,
    onClick: () -> Unit
): Modifier {
    var lastClickTime by remember { mutableLongStateOf(0L) }

    return this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = munchkinRipple(
            bounded = bounded,
            radius = radius,
            color = color
        ),
        enabled = enabled,
        onClick = {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > debounceTime) {
                lastClickTime = currentTime
                onClick()
            }
        }
    )
}
