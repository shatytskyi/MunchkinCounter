package com.shatytskyi.munchcounter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

@Composable
fun MunchkinDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    title: String? = null,
    content: @Composable () -> Unit,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable (() -> Unit)? = null
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .background(MunchkinTheme.colors.surface)
                .padding(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title
                title?.let {
                    MunchkinText(
                        text = it,
                        style = MunchkinTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MunchkinTheme.colors.onSurface,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Content
                content()

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    dismissButton?.let {
                        it()
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    confirmButton()
                }
            }
        }
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
    MunchkinButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = MunchkinButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = color,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = color.copy(alpha = 0.38f)
        ),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp
        )
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
