package com.shatytskyi.munchcounter.ui.components

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

@Composable
fun MunchkinText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MunchkinTheme.colors.onBackground,
    style: TextStyle = MunchkinTheme.typography.bodyMedium,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE
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
    )
}
