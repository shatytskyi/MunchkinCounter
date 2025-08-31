package com.shatytskyi.munchcounter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

@Composable
fun MunchkinCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    backgroundColor: Color = MunchkinTheme.colors.surface,
    borderColor: Color = MunchkinTheme.colors.outline,
    borderWidth: Dp = 1.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val cardModifier = if (onClick != null) {
        modifier
            .border(borderWidth, borderColor, shape)
            .clip(shape)
            .background(backgroundColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = MunchkinTheme.colors.onSurface),
                onClick = onClick
            )
    } else {
        modifier
            .border(borderWidth, borderColor, shape)
            .clip(shape)
            .background(backgroundColor)
    }

    Box(modifier = cardModifier) {
        content()
    }
}
