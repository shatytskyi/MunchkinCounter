package com.shatytskyi.gamecounter.ui.components

import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.shatytskyi.gamecounter.ui.theme.MunchkinTheme

@Composable
fun MunchkinText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MunchkinTheme.colors.onBackground,
    style: TextStyle = MunchkinTheme.typography.bodyMedium,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    minTextSize: TextUnit = 12.sp,
    maxTextSize: TextUnit = style.fontSize
) {
    BasicText(
        text = text,
        modifier = modifier,
        style = style.copy(
            color = color,
            textAlign = textAlign ?: TextAlign.Unspecified
        ),
        overflow = overflow,
        maxLines = maxLines,
        autoSize = TextAutoSize.StepBased(
            minFontSize = minTextSize,
            maxFontSize = maxTextSize,
        )
    )
}
