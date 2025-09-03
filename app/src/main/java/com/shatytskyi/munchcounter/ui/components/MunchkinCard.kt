package com.shatytskyi.munchcounter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
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
    shape: Shape = RoundedCornerShape(16.dp),
    backgroundColor: Color = MunchkinTheme.colors.background,
    color: Color = MunchkinTheme.colors.primary,
    borderWidth: Dp = 1.5.dp,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    val cardModifier = modifier
        .border(borderWidth, color, shape)
        .clip(shape)
        .background(backgroundColor)
        .then(
            if (onClick != null) {
                Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = munchkinRipple(
                        bounded = true,
                        color = color
                    ),
                    enabled = enabled,
                    onClick = onClick
                )
            } else {
                Modifier
            }
        )

    Box(modifier = cardModifier) {
        content()
    }
}
