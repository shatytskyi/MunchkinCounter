package com.shatytskyi.gamecounter.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shatytskyi.gamecounter.R
import com.shatytskyi.gamecounter.ui.theme.MunchkinTheme

@Composable
fun RateAppDialog(
    onRateNow: () -> Unit,
    onRemindLater: () -> Unit,
    onDismiss: () -> Unit
) {
    MunchkinDialog(
        onDismissRequest = onDismiss,
        title = stringResource(
            R.string.rate_dialog_title_level10
        ),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                MunchkinText(
                    text = stringResource(
                        R.string.rate_dialog_message_level10
                    ),
                    style = MunchkinTheme.typography.bodyMedium,
                    color = MunchkinTheme.colors.onBackground.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onRateNow) {
                MunchkinText(
                    text = stringResource(R.string.rate_dialog_rate_now),
                    style = MunchkinTheme.typography.labelLarge,
                    color = MunchkinTheme.colors.primary
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onRemindLater) {
                MunchkinText(
                    text = stringResource(R.string.rate_dialog_remind_later),
                    style = MunchkinTheme.typography.labelLarge,
                    color = MunchkinTheme.colors.onBackground.copy(alpha = 0.8f)
                )
            }
        }
    )
}
