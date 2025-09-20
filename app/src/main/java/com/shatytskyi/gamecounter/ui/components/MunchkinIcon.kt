package com.shatytskyi.gamecounter.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shatytskyi.gamecounter.ui.theme.MunchkinTheme

@Composable
fun MunchkinIcon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    tint: Color = MunchkinTheme.colors.onBackground,
    size: Dp = 24.dp
) {
    val painter = rememberVectorPainter(imageVector)
    
    Box(
        modifier = modifier
            .size(size)
            .paint(
                painter = painter,
                colorFilter = ColorFilter.tint(tint),
                contentScale = ContentScale.Fit
            )
    )
}
