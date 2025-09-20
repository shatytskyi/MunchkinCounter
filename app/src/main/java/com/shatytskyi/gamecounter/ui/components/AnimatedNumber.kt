package com.shatytskyi.gamecounter.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.shatytskyi.gamecounter.ui.theme.MunchkinTheme

@Composable
fun AnimatedNumber(
    value: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = MunchkinTheme.typography.displayMedium,
    color: Color = MunchkinTheme.colors.primary
) {
    AnimatedContent(
        targetState = value,
        modifier = modifier,
        transitionSpec = {
            val isIncreasing = targetState > initialState
            
            if (isIncreasing) {
                slideInVertically(
                    animationSpec = tween(300),
                    initialOffsetY = { -it }
                ) togetherWith slideOutVertically(
                    animationSpec = tween(300),
                    targetOffsetY = { it }
                )
            } else {
                slideInVertically(
                    animationSpec = tween(300),
                    initialOffsetY = { it }
                ) togetherWith slideOutVertically(
                    animationSpec = tween(300),
                    targetOffsetY = { -it }
                )
            }
        },
        label = "NumberAnimation"
    ) { animatedValue ->
        MunchkinText(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = animatedValue.toString(),
            style = style,
            color = color
        )
    }
}
