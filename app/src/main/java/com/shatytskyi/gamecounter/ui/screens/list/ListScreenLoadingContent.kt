package com.shatytskyi.gamecounter.ui.screens.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.shatytskyi.gamecounter.R
import com.shatytskyi.gamecounter.ui.components.APP_BAR_HEIGHT
import com.shatytskyi.gamecounter.ui.components.MunchkinText
import com.shatytskyi.gamecounter.ui.theme.MunchkinTheme

@OptIn(androidx.compose.animation.ExperimentalSharedTransitionApi::class)
@Composable
fun ListScreenLoadingContent(
    onDiceClick: () -> Unit = {},
    onTimerClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    animatedContentScope: androidx.compose.animation.AnimatedContentScope? = null,
    sharedTransitionScope: androidx.compose.animation.SharedTransitionScope? = null
) {
    val density = LocalDensity.current
    val statusBarHeight = WindowInsets.systemBars.getTop(density)
    val topPadding = remember(statusBarHeight) {
        with(density) { statusBarHeight.toDp() + APP_BAR_HEIGHT.dp + 40.dp }
    }

    ListScreenTopBarWrapper(
        onDiceClick = onDiceClick,
        onTimerClick = onTimerClick,
        onSettingsClick = onSettingsClick,
        animatedContentScope = animatedContentScope,
        sharedTransitionScope = sharedTransitionScope
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom))
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
                .padding(top = topPadding, start = 24.dp, end = 24.dp, bottom = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                CircularProgressIndicator(
                    color = MunchkinTheme.colors.primary,
                    modifier = Modifier.size(48.dp)
                )
                
                MunchkinText(
                    text = stringResource(R.string.loading_players),
                    style = MunchkinTheme.typography.bodyLarge,
                    color = MunchkinTheme.colors.onBackground,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(
    name = "Loading State",
    device = Devices.PIXEL_4,
    showSystemUi = true,
    showBackground = true
)
@OptIn(androidx.compose.animation.ExperimentalSharedTransitionApi::class)
@Composable
private fun LoadingStateContentPreview() {
    MunchkinTheme {
        ListScreenLoadingContent()
    }
}
