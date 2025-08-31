package com.shatytskyi.munchcounter.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

@Composable
fun MunchkinIconTextButton(
    onClick: () -> Unit,
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    bounded: Boolean = false,
    iconSize: Dp = 20.dp,
    spacerWidth: Dp = 4.dp,
    rippleColor: Color = MunchkinTheme.colors.primary,
    iconTint: Color = MunchkinTheme.colors.onBackground,
    textColor: Color = MunchkinTheme.colors.onBackground,
    textStyle: TextStyle = MunchkinTheme.typography.labelMedium,
    contentPadding: Dp = 16.dp
) {
    Row(
        modifier = modifier
            .munchkinClickable(
                onClick = onClick,
                enabled = enabled,
                bounded = bounded,
                color = rippleColor
            )
            .padding(vertical = contentPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        MunchkinIcon(
            imageVector = icon,
            size = iconSize,
            tint = iconTint
        )
        Spacer(modifier = Modifier.width(spacerWidth))
        MunchkinText(
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}