package com.shatytskyi.gamecounter.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shatytskyi.gamecounter.R
import com.shatytskyi.gamecounter.ui.theme.MunchkinTheme

const val APP_BAR_HEIGHT = 64

@Composable
fun MunchkinTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null,
    showIcon: Boolean = false,
    actions: @Composable () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(APP_BAR_HEIGHT.dp)
            .padding(
                vertical = 8.dp
            )
            .then(
                if (onBack != null) {
                    Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onBack() }
                } else Modifier
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onBack != null) {
            MunchkinIconButton(
                onClick = onBack,
                size = 48.dp
            ) {
                MunchkinIcon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    tint = MunchkinTheme.colors.onBackground,
                    size = 24.dp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
        } else if (showIcon) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(32.dp),
                colorFilter = ColorFilter.tint(MunchkinTheme.colors.onBackground)
            )

            Spacer(modifier = Modifier.width(8.dp))
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
            modifier = Modifier.weight(1f).then(
                when {
                    onBack != null -> Modifier
                    showIcon -> Modifier
                    else -> Modifier.padding(start = 16.dp)
                }
            )
        )

        actions()
    }
}

@Preview(name = "TopAppBar without Back Button")
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
