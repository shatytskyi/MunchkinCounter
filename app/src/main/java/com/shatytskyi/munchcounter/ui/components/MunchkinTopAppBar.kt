package com.shatytskyi.munchcounter.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.shatytskyi.munchcounter.ui.theme.Dimens
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

@Composable
fun MunchkinTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null,
    actions: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
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
            if (onBack != null) {
                MunchkinIconButton(
                    onClick = onBack
                ) {
                    MunchkinIcon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        tint = MunchkinTheme.colors.onSurface
                    )
                }

                Spacer(
                    modifier = Modifier.padding(horizontal = Dimens.paddingMedium)
                )
            }

            // Title
            MunchkinText(
                text = title,
                style = MunchkinTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MunchkinTheme.colors.onSurface,
                textAlign = TextAlign.Start
            )

            // Actions
            actions()
        }
    }
}
