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

@Composable
fun AnimatedNumber(
    value: Int,
    textStyle: TextStyle,
    color: Color,
    modifier: Modifier = Modifier
) {
    val valueString = value.toString()
    
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        valueString.forEach { digitChar ->
            AnimatedDigit(
                digit = digitChar.digitToInt(),
                textStyle = textStyle,
                color = color
            )
        }
    }
}

@Composable
private fun AnimatedDigit(
    digit: Int,
    textStyle: TextStyle,
    color: Color,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = digit,
        transitionSpec = {
            // Special case: 0 -> 9 is counting down (like 10 -> 09)
            // Special case: 9 -> 0 is counting up (reset or increment)
            val isCountingDown = when {
                initialState == 0 && targetState == 9 -> true  // 0 -> 9 is countdown
                initialState == 9 && targetState == 0 -> false // 9 -> 0 is count up/reset
                else -> targetState < initialState // Normal comparison for other cases
            }
            
            if (isCountingDown) {
                // Counting down - new digit slides up from bottom with spring
                (slideInVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    initialOffsetY = { it } // Start from bottom
                ) + fadeIn(
                    animationSpec = tween(300)
                )) togetherWith (slideOutVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    targetOffsetY = { -it } // Exit to top
                ) + fadeOut(
                    animationSpec = tween(300)
                ))
            } else {
                // Counting up or reset - new digit slides down from top with spring
                (slideInVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    initialOffsetY = { -it } // Start from top
                ) + fadeIn(
                    animationSpec = tween(300)
                )) togetherWith (slideOutVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    targetOffsetY = { it } // Exit to bottom
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
            color = color
        )
    }
}