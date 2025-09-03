package com.shatytskyi.munchcounter.ui.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

const val APP_BAR_HEIGHT = 64

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MunchkinTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null,
    actions: @Composable () -> Unit = {},
    animatedContentScope: AnimatedContentScope? = null,
    sharedTransitionScope: SharedTransitionScope? = null,
    titleSharedKey: String? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(APP_BAR_HEIGHT.dp)
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onBack != null) {
            MunchkinIconButton(
                onClick = onBack,
                size = 24.dp
            ) {
                MunchkinIcon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    tint = MunchkinTheme.colors.onBackground,
                    size = 24.dp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
        }

        val titleModifier = if (sharedTransitionScope != null && animatedContentScope != null && titleSharedKey != null) {
            with(sharedTransitionScope) {
                Modifier
                    .weight(1f)
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(key = titleSharedKey),
                        animatedVisibilityScope = animatedContentScope
                    )
            }
        } else {
            Modifier.weight(1f)
        }
        
        MunchkinText(
            text = title,
            style = MunchkinTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = MunchkinTheme.colors.onBackground,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = titleModifier
        )

        actions()
    }
}

@Preview(name = "TopAppBar without Back Button")
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun MunchkinTopAppBarWithoutBackPreview() {
    MunchkinTheme {
        Column {
            MunchkinTopAppBar(
                title = "Munchkin Counter"
            )
        }
    }
}

@Preview(name = "TopAppBar with Back Button")
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun MunchkinTopAppBarWithBackPreview() {
    MunchkinTheme {
        Column {
            MunchkinTopAppBar(
                title = "Settings",
                onBack = {}
            )
        }
    }
}

@Preview(name = "TopAppBar with Actions")
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun MunchkinTopAppBarWithActionsPreview() {
    MunchkinTheme {
        Column {
            MunchkinTopAppBar(
                title = "Players",
                onBack = {},
                actions = {
                    MunchkinIconButton(
                        onClick = {}
                    ) {
                        MunchkinIcon(
                            Icons.Outlined.MoreVert,
                            tint = MunchkinTheme.colors.onBackground
                        )
                    }
                }
            )
        }
    }
}
