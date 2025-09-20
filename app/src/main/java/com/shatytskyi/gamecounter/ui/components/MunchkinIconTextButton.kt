package com.shatytskyi.gamecounter.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shatytskyi.gamecounter.ui.theme.MunchkinTheme

@Composable
fun MunchkinIconTextButton(
    onClick: () -> Unit,
    icon: ImageVector?,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    bounded: Boolean = false,
    rippleColor: Color? = MunchkinTheme.colors.primary,
    iconTint: Color = MunchkinTheme.colors.onBackground,
    textColor: Color = MunchkinTheme.colors.onBackground,
    textStyle: TextStyle = MunchkinTheme.typography.labelMedium,
    contentPadding: Dp = 16.dp
) {
    Row(
        modifier = modifier
            .munchkinClickable(
                onClick = onClick,
                enabled = enabled,
                bounded = bounded,
                rippleColor = rippleColor
            )
            .padding(vertical = contentPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        icon?.let {
            MunchkinIcon(
                imageVector = icon,
                size = 24.dp,
                tint = iconTint
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        MunchkinText(
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
private fun MunchkinIconTextButtonPreview() {
    MunchkinTheme {
        MunchkinIconTextButtonGrid()
    }
}

@Composable
private fun MunchkinIconTextButtonGrid() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Заголовок секции
        MunchkinText(
            text = "Default Buttons",
            style = MunchkinTheme.typography.labelLarge,
            color = MunchkinTheme.colors.onBackground,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Стандартные кнопки
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MunchkinIconTextButton(
                onClick = {},
                icon = Icons.Default.Add,
                text = "Add",
                modifier = Modifier.weight(1f)
            )

            MunchkinIconTextButton(
                onClick = {},
                icon = Icons.Default.Edit,
                text = "Edit",
                modifier = Modifier.weight(1f)
            )

            MunchkinIconTextButton(
                onClick = {},
                icon = Icons.Default.Delete,
                text = "Delete",
                modifier = Modifier.weight(1f)
            )
        }

        // Кнопки с разными цветами
        MunchkinText(
            text = "Colored Buttons",
            style = MunchkinTheme.typography.labelLarge,
            color = MunchkinTheme.colors.onBackground,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MunchkinIconTextButton(
                onClick = {},
                icon = Icons.Default.Favorite,
                text = "Like",
                modifier = Modifier.weight(1f),
                rippleColor = MunchkinTheme.colors.red,
                iconTint = MunchkinTheme.colors.red,
                textColor = MunchkinTheme.colors.red
            )

            MunchkinIconTextButton(
                onClick = {},
                icon = Icons.Default.Share,
                text = "Share",
                modifier = Modifier.weight(1f),
                rippleColor = MunchkinTheme.colors.green,
                iconTint = MunchkinTheme.colors.green,
                textColor = MunchkinTheme.colors.green
            )

            MunchkinIconTextButton(
                onClick = {},
                icon = Icons.Default.Settings,
                text = "Settings",
                modifier = Modifier.weight(1f),
                rippleColor = MunchkinTheme.colors.primary,
                iconTint = MunchkinTheme.colors.primary,
                textColor = MunchkinTheme.colors.primary
            )
        }

        // Кнопки с разными стилями ripple
        MunchkinText(
            text = "Ripple Styles",
            style = MunchkinTheme.typography.labelLarge,
            color = MunchkinTheme.colors.onBackground,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MunchkinIconTextButton(
                onClick = {},
                icon = Icons.Default.Add,
                text = "Bounded",
                modifier = Modifier.weight(1f),
                bounded = true
            )

            MunchkinIconTextButton(
                onClick = {},
                icon = Icons.Default.Add,
                text = "Unbounded",
                modifier = Modifier.weight(1f),
                bounded = false
            )
        }

        // Disabled кнопка
        MunchkinText(
            text = "Disabled State",
            style = MunchkinTheme.typography.labelLarge,
            color = MunchkinTheme.colors.onBackground,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        MunchkinIconTextButton(
            onClick = {},
            icon = Icons.Default.Add,
            text = "Disabled Button",
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
