package com.shatytskyi.munchcounter.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

@Composable
fun MunchkinOutlinedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    cornerRadius: Dp = 8.dp,
    containerColor: Color = MunchkinTheme.colors.primary,
    content: @Composable () -> Unit
) {
    val targetButtonColor = if (enabled) containerColor else MunchkinTheme.colors.grey

    Box(
        modifier = modifier
            .border(2.dp, targetButtonColor, RoundedCornerShape(cornerRadius))
            .clip(RoundedCornerShape(cornerRadius))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = targetButtonColor),
                enabled = enabled,
                onClick = onClick
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun MunchkinTextButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color = MunchkinTheme.colors.primary
) {
    MunchkinOutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        containerColor = color
    ) {
        MunchkinText(
            text = text,
            style = MunchkinTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Medium
            ),
            color = if (enabled) color else color.copy(alpha = 0.38f)
        )
    }
}
