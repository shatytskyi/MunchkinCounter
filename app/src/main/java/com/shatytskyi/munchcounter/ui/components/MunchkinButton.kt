package com.shatytskyi.munchcounter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

@Composable
fun MunchkinButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(8.dp),
    colors: MunchkinButtonColors = MunchkinButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = PaddingValues(16.dp),
    borderColor: Color? = null,
    borderWidth: Dp = 1.dp,
    content: @Composable () -> Unit
) {
    val containerColor = if (enabled) colors.containerColor else colors.disabledContainerColor
    val contentColor = if (enabled) colors.contentColor else colors.disabledContentColor
    
    val buttonModifier = if (borderColor != null) {
        modifier
            .border(borderWidth, borderColor, shape)
            .clip(shape)
            .background(containerColor)
    } else {
        modifier
            .clip(shape)
            .background(containerColor)
    }
    
    Box(
        modifier = buttonModifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = contentColor),
                enabled = enabled,
                onClick = onClick
            )
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

data class MunchkinButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color
)

object MunchkinButtonDefaults {
    @Composable
    fun buttonColors(
        containerColor: Color = MunchkinTheme.colors.primary,
        contentColor: Color = MunchkinTheme.colors.onPrimary,
        disabledContainerColor: Color = Color(0xFFE0E0E0),
        disabledContentColor: Color = Color(0xFF9E9E9E)
    ): MunchkinButtonColors = MunchkinButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor
    )
    
    @Composable
    fun primaryColors() = buttonColors(
        containerColor = MunchkinTheme.colors.primary,
        contentColor = MunchkinTheme.colors.onPrimary
    )
    
    @Composable
    fun secondaryColors() = buttonColors(
        containerColor = MunchkinTheme.colors.secondaryContainer,
        contentColor = MunchkinTheme.colors.onSecondaryContainer
    )
    
    @Composable
    fun tertiaryColors() = buttonColors(
        containerColor = MunchkinTheme.colors.tertiaryContainer,
        contentColor = MunchkinTheme.colors.onTertiaryContainer
    )
    
    @Composable
    fun errorColors() = buttonColors(
        containerColor = MunchkinTheme.colors.errorContainer,
        contentColor = MunchkinTheme.colors.onErrorContainer
    )
}