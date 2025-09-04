package com.shatytskyi.munchcounter.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.ui.components.MunchkinDialog
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.components.munchkinClickable
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

@Composable
fun WarningDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    
    MunchkinDialog(
        onDismissRequest = onDismiss,
        title = title,
        content = {
            Column {
                Spacer(modifier = Modifier.height(24.dp))
                
                MunchkinText(
                    text = message,
                    style = MunchkinTheme.typography.bodyLarge,
                    color = MunchkinTheme.colors.onBackground,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(24.dp))
            }
        },
        confirmButton = {
            MunchkinText(
                modifier = Modifier.munchkinClickable(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                        onConfirm()
                    },
                    bounded = false,
                    rippleColor = MunchkinTheme.colors.red
                ),
                text = stringResource(R.string.confirm),
                style = MunchkinTheme.typography.bodyLarge,
                color = MunchkinTheme.colors.red
            )
        },
        dismissButton = {
            MunchkinText(
                modifier = Modifier.munchkinClickable(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                        onDismiss()
                    },
                    bounded = false,
                    rippleColor = MunchkinTheme.colors.secondary
                ),
                text = stringResource(R.string.cancel),
                style = MunchkinTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MunchkinTheme.colors.onBackground
            )
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun WarningDialogPreview() {
    MunchkinTheme {
        WarningDialog(
            title = "Delete Player?",
            message = "Player will be permanently deleted",
            onDismiss = {},
            onConfirm = {}
        )
    }
}
