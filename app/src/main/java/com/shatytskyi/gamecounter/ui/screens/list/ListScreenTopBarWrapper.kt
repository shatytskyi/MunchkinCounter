package com.shatytskyi.gamecounter.ui.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.shatytskyi.gamecounter.R
import com.shatytskyi.gamecounter.ui.components.MunchkinIcon
import com.shatytskyi.gamecounter.ui.components.MunchkinIconButton
import com.shatytskyi.gamecounter.ui.components.MunchkinTopAppBar
import com.shatytskyi.gamecounter.ui.components.icons.MunchkinIcons
import com.shatytskyi.gamecounter.ui.components.icons.Settings
import com.shatytskyi.gamecounter.ui.components.icons.Timer
import com.shatytskyi.gamecounter.ui.components.icons.dice.Dice5
import com.shatytskyi.gamecounter.ui.theme.MunchkinTheme

@OptIn(androidx.compose.animation.ExperimentalSharedTransitionApi::class)
@Composable
fun ListScreenTopBarWrapper(
    onDiceClick: () -> Unit = {},
    onTimerClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        content()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MunchkinTheme.colors.background.copy(alpha = 0.95f))
        ) {
            MunchkinTopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top)),
                title = stringResource(R.string.app_title_main),
                showIcon = true,
                actions = {
                    MunchkinIconButton(onClick = onTimerClick) {
                        MunchkinIcon(
                            imageVector = MunchkinIcons.Timer,
                            tint = MunchkinTheme.colors.onBackground
                        )
                    }
                    MunchkinIconButton(onClick = onDiceClick) {
                        MunchkinIcon(
                            imageVector = MunchkinIcons.Dice.Dice5,
                            tint = MunchkinTheme.colors.onBackground
                        )
                    }
                    MunchkinIconButton(onClick = onSettingsClick) {
                        MunchkinIcon(
                            imageVector = MunchkinIcons.Settings,
                            tint = MunchkinTheme.colors.onBackground
                        )
                    }
                }
            )
        }
    }
}
