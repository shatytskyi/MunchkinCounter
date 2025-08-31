package com.shatytskyi.munchcounter.ui.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.shatytskyi.munchcounter.ui.components.MunchkinDialog
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

@Composable
fun WarningDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    MunchkinDialog(
        onDismissRequest = onDismiss,
        title = title,
        content = {
            MunchkinText(
                text = message,
                style = MunchkinTheme.typography.bodyMedium,
                color = MunchkinTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            MunchkinText(
                modifier = Modifier.clickable(
                    onClick = onConfirm,
                ),
                text = "Confirm",
                color = MunchkinTheme.colors.red
            )
        },
        dismissButton = {
            MunchkinText(
                modifier = Modifier.clickable(
                    onClick = onDismiss,
                ),
                text = "Cancel"
            )
        },
        modifier = modifier
    )
}
