package com.shatytskyi.munchcounter.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedNumber(
    value: Int,
    textStyle: TextStyle,
    color: Color,
    modifier: Modifier = Modifier,
) {
    val valueString = value.toString()

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        valueString.forEach { char ->
            if (char == '-') {
                MunchkinText(
                    text = "-",
                    style = textStyle,
                    color = color,
                    minTextSize = 14.sp,
                    maxTextSize = textStyle.fontSize
                )
            } else {
                AnimatedDigit(
                    digit = char.digitToInt(),
                    textStyle = textStyle,
                    color = color
                )
            }
        }
    }
}

@Composable
private fun AnimatedDigit(
    digit: Int,
    textStyle: TextStyle,
    color: Color,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        targetState = digit,
        transitionSpec = {
            val isCountingDown = when {
                initialState == 0 && targetState == 9 -> true  // 0 -> 9 is countdown
                initialState == 9 && targetState == 0 -> false // 9 -> 0 is count up/reset
                else -> targetState < initialState // Normal comparison for other cases
            }

            if (isCountingDown) {
                (slideInVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    initialOffsetY = { it }
                ) + fadeIn(
                    animationSpec = tween(300)
                )) togetherWith (slideOutVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    targetOffsetY = { -it }
                ) + fadeOut(
                    animationSpec = tween(300)
                ))
            } else {
                (slideInVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    initialOffsetY = { -it }
                ) + fadeIn(
                    animationSpec = tween(300)
                )) togetherWith (slideOutVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    targetOffsetY = { it }
                ) + fadeOut(
                    animationSpec = tween(300)
                ))
            }
        },
        modifier = modifier,
        label = "digit_animation"
    ) { animatedDigit ->
        MunchkinText(
            text = animatedDigit.toString(),
            style = textStyle,
            color = color,
            minTextSize = 14.sp,
            maxTextSize = textStyle.fontSize
        )
    }
}
