package com.shatytskyi.gamecounter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shatytskyi.gamecounter.ui.theme.MunchkinTheme

@Composable
fun MunchkinIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = CircleShape,
    colors: MunchkinIconButtonColors = MunchkinIconButtonDefaults.iconButtonColors(),
    size: Dp = 48.dp,
    borderColor: Color? = null,
    borderWidth: Dp = 1.dp,
    content: @Composable () -> Unit
) {
    val containerColor = if (enabled) colors.containerColor else colors.disabledContainerColor
    val contentColor = if (enabled) colors.contentColor else colors.disabledContentColor
    
    val buttonModifier = if (borderColor != null) {
        modifier
            .size(size)
            .border(borderWidth, borderColor, shape)
            .clip(shape)
            .background(containerColor)
    } else {
        modifier
            .size(size)
            .clip(shape)
            .background(containerColor)
    }
    
    Box(
        modifier = buttonModifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = munchkinRipple(
                    bounded = false,
                    radius = size / 2,
                    color = contentColor
                ),
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

data class MunchkinIconButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color
)

object MunchkinIconButtonDefaults {
    @Composable
    fun iconButtonColors(
        containerColor: Color = Color.Transparent,
        contentColor: Color = MunchkinTheme.colors.onBackground,
        disabledContainerColor: Color = Color.Transparent,
        disabledContentColor: Color = MunchkinTheme.colors.onBackground.copy(alpha = 0.38f)
    ): MunchkinIconButtonColors = MunchkinIconButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor
    )
    
    @Composable
    fun filledIconButtonColors(
        containerColor: Color = MunchkinTheme.colors.primary,
        contentColor: Color = MunchkinTheme.colors.onBackground,
        disabledContainerColor: Color = MunchkinTheme.colors.onBackground.copy(alpha = 0.12f),
        disabledContentColor: Color = MunchkinTheme.colors.onBackground.copy(alpha = 0.38f)
    ): MunchkinIconButtonColors = MunchkinIconButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor
    )
}
