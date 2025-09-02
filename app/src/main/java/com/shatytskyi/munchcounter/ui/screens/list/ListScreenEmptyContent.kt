package com.shatytskyi.munchcounter.ui.screens.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.ui.components.APP_BAR_HEIGHT
import com.shatytskyi.munchcounter.ui.components.MunchkinIconTextButton
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

@Composable
fun ListScreenEmptyContent(
    onAddCharacterClick: () -> Unit,
    onDiceClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    val density = LocalDensity.current
    val statusBarHeight = WindowInsets.systemBars.getTop(density)
    val topPadding = remember(statusBarHeight) {
        with(density) { statusBarHeight.toDp() + APP_BAR_HEIGHT.dp + 40.dp }
    }

    ListScreenTopBarWrapper(onDiceClick = onDiceClick) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom))
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
                .padding(top = topPadding, start = 24.dp, end = 24.dp, bottom = 24.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Image(
                        painter = painterResource(R.drawable.pic_knight),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        colorFilter = ColorFilter.tint(MunchkinTheme.colors.onBackground)
                    )

                    Spacer(modifier = Modifier.width(24.dp))

                    Image(
                        painter = painterResource(R.drawable.pic_knight_fem),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        colorFilter = ColorFilter.tint(MunchkinTheme.colors.onBackground)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                MunchkinText(
                    text = stringResource(R.string.no_players),
                    style = MunchkinTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MunchkinTheme.colors.onBackground,
                    textAlign = TextAlign.Center
                )

                MunchkinText(
                    text = stringResource(R.string.no_players_description),
                    style = MunchkinTheme.typography.bodyLarge,
                    color = MunchkinTheme.colors.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Hero images - bottom row
                Row {
                    Image(
                        painter = painterResource(R.drawable.pic_witch),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        colorFilter = ColorFilter.tint(MunchkinTheme.colors.onBackground)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Image(
                        painter = painterResource(R.drawable.pic_wizard),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        colorFilter = ColorFilter.tint(MunchkinTheme.colors.onBackground)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                HorizontalDivider()

                Spacer(modifier = Modifier.height(26.dp))

                MunchkinIconTextButton(
                    onClick = onAddCharacterClick,
                    icon = Icons.Default.Add,
                    text = stringResource(R.string.add_player),
                    modifier = Modifier.fillMaxWidth(0.6f),
                    textStyle = MunchkinTheme.typography.labelLarge,
                    contentPadding = 24.dp,
                    rippleColor = MunchkinTheme.colors.primary,
                    bounded = false
                )

                MunchkinIconTextButton(
                    onClick = onSettingsClick,
                    icon = Icons.Outlined.Settings,
                    text = stringResource(R.string.settings),
                    modifier = Modifier.fillMaxWidth(0.5f),
                    textStyle = MunchkinTheme.typography.labelMedium,
                    contentPadding = 24.dp,
                    rippleColor = MunchkinTheme.colors.grey,
                    bounded = false
                )
            }
        }
    }
}

@Preview(
    name = "Empty State",
    device = Devices.PIXEL_4,
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun EmptyStateContentPreview() {
    MunchkinTheme {
        ListScreenEmptyContent(
            onAddCharacterClick = {}
        )
    }
}
