package com.shatytskyi.munchcounter.ui.screens.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
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
    onDiceClick: () -> Unit = {}
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
            contentAlignment = Alignment.Center
        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero images - top row
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.pic_knight),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
                Image(
                    painter = painterResource(R.drawable.pic_knight_fem),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
            }

            MunchkinText(
                text = "No Players Yet",
                style = MunchkinTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MunchkinTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )

            MunchkinText(
                text = "Add your first player to get started with Munchkin!",
                style = MunchkinTheme.typography.bodyLarge,
                color = MunchkinTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )

            // Hero images - bottom row
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.pic_witch),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
                Image(
                    painter = painterResource(R.drawable.pic_wizard),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            MunchkinIconTextButton(
                onClick = onAddCharacterClick,
                icon = Icons.Default.Add,
                text = "Add Player",
                modifier = Modifier.fillMaxWidth(0.6f),
                textStyle = MunchkinTheme.typography.labelLarge,
                contentPadding = 24.dp,
                rippleColor = MunchkinTheme.colors.primary,
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
