package com.shatytskyi.munchcounter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.ui.theme.Dimens
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

@Composable
fun MunchkinTopAppBar(
    title: String,
    onBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    actions: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MunchkinTheme.colors.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(
                    horizontal = Dimens.screenPaddingHorizontal,
                    vertical = Dimens.paddingLarge
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back button
            if (onBack != null) {
                MunchkinIconButton(
                    onClick = onBack,
                    borderColor = MunchkinTheme.colors.outline.copy(alpha = 0.3f)
                ) {
                    MunchkinIcon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        tint = MunchkinTheme.colors.onSurface
                    )
                }
            }

            // Title
            MunchkinText(
                text = title,
                style = MunchkinTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MunchkinTheme.colors.onSurface,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            // Actions
            actions()
        }
    }
}
