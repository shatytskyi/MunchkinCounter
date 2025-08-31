package com.shatytskyi.munchcounter.ui.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Casino
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.ui.components.MunchkinIcon
import com.shatytskyi.munchcounter.ui.components.MunchkinIconButton
import com.shatytskyi.munchcounter.ui.components.MunchkinTopAppBar
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

@Composable
fun ListScreenTopBarWrapper(
    onDiceClick: () -> Unit = {},
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
                title = stringResource(R.string.app_name),
                actions = {
                    MunchkinIconButton(onClick = onDiceClick) {
                        MunchkinIcon(
                            imageVector = Icons.Outlined.Casino,
                            tint = MunchkinTheme.colors.onBackground
                        )
                    }
                }
            )
        }
    }
}
