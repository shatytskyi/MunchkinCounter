package com.shatytskyi.gamecounter.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shatytskyi.gamecounter.R
import com.shatytskyi.gamecounter.ui.components.MunchkinCustomDialog
import com.shatytskyi.gamecounter.ui.components.MunchkinIconTextButton
import com.shatytskyi.gamecounter.ui.components.MunchkinText
import com.shatytskyi.gamecounter.ui.components.MunchkinTextField
import com.shatytskyi.gamecounter.ui.components.icons.Add
import com.shatytskyi.gamecounter.ui.components.icons.Close
import com.shatytskyi.gamecounter.ui.components.icons.MunchkinIcons
import com.shatytskyi.gamecounter.ui.theme.MunchkinTheme
import kotlinx.coroutines.delay

@Composable
fun AddCharacterDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    val focusRequester = remember { FocusRequester() }
    val haptic = LocalHapticFeedback.current

    LaunchedEffect(Unit) {
        // Log dialog view
        delay(200) // Delay for bottom sheet animation
        focusRequester.requestFocus()
    }

    MunchkinCustomDialog(
        onDismissRequest = onDismiss,
        modifier = modifier
    ) {
        // Header
        MunchkinText(
            text = stringResource(R.string.add_new_player),
            style = MunchkinTheme.typography.headlineSmall,
            color = MunchkinTheme.colors.onBackground,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Name input section
        Column {
            MunchkinText(
                text = stringResource(R.string.player_name),
                style = MunchkinTheme.typography.labelLarge,
                color = MunchkinTheme.colors.onBackground
            )

            Spacer(modifier = Modifier.height(12.dp))

            MunchkinTextField(
                value = name,
                onValueChange = { name = it },
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (name.text.trim().isNotEmpty()) {
                            haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                            onConfirm(name.text.trim())
                        }
                    }
                ),
                focusRequester = focusRequester,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Action buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Cancel button
            MunchkinIconTextButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    onDismiss()
                },
                icon = MunchkinIcons.Close,
                text = stringResource(R.string.cancel),
                modifier = Modifier.weight(1f),
                textStyle = MunchkinTheme.typography.labelMedium,
                contentPadding = 16.dp,
                rippleColor = MunchkinTheme.colors.secondary,
                bounded = false
            )

            // Add button
            MunchkinIconTextButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                    onConfirm(name.text.trim())
                },
                icon = MunchkinIcons.Add,
                text = stringResource(R.string.add),
                modifier = Modifier.weight(1f),
                textStyle = MunchkinTheme.typography.labelMedium,
                contentPadding = 16.dp,
                rippleColor = MunchkinTheme.colors.primary,
                bounded = false,
                enabled = name.text.trim().isNotEmpty()
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun AddCharacterDialogPreview() {
    MunchkinTheme {
        AddCharacterDialog(
            onDismiss = {},
            onConfirm = { _ -> }
        )
    }
}
