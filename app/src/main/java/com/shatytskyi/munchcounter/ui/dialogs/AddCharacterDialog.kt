package com.shatytskyi.munchcounter.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Female
import androidx.compose.material.icons.outlined.Male
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.data.Gender
import com.shatytskyi.munchcounter.ui.components.MunchkinCard
import com.shatytskyi.munchcounter.ui.components.MunchkinDialog
import com.shatytskyi.munchcounter.ui.components.MunchkinIcon
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.components.MunchkinTextField
import com.shatytskyi.munchcounter.ui.components.munchkinClickable
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme
import kotlinx.coroutines.delay

@Composable
fun AddCharacterDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, Gender) -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf(Gender.MALE) }
    val focusRequester = remember { FocusRequester() }
    val haptic = LocalHapticFeedback.current

    LaunchedEffect(Unit) {
        delay(100)
        focusRequester.requestFocus()
    }

    MunchkinDialog(
        onDismissRequest = onDismiss,
        title = stringResource(R.string.add_new_player),
        content = {
            Column {
                Spacer(modifier = Modifier.height(24.dp))

                Column {
                    MunchkinText(
                        text = stringResource(R.string.player_name),
                        style = MunchkinTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MunchkinTheme.colors.onBackground
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    MunchkinTextField(
                        value = name,
                        onValueChange = { name = it },
                        focusRequester = focusRequester,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Column {
                    MunchkinText(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = stringResource(R.string.gender),
                        style = MunchkinTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MunchkinTheme.colors.onBackground
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    MunchkinCard(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = MunchkinTheme.colors.background,
                        color = MunchkinTheme.colors.primary
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Gender.entries.forEach { gender ->
                                GenderOption(
                                    gender = gender,
                                    isSelected = selectedGender == gender,
                                    onClick = {
                                        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                        selectedGender = gender
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            MunchkinText(
                modifier = Modifier.munchkinClickable(
                    enabled = name.trim().isNotEmpty(),
                    bounded = false,
                    rippleColor = MunchkinTheme.colors.primary,
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                        onConfirm(name.trim(), selectedGender)
                    }
                ),
                style = MunchkinTheme.typography.bodyLarge,
                text = stringResource(R.string.add),
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
                    rippleColor = MunchkinTheme.colors.secondary,
                ),
                style = MunchkinTheme.typography.bodyMedium,
                text = stringResource(R.string.cancel)
            )
        },
        modifier = modifier
    )
}

@Composable
private fun GenderOption(
    gender: Gender,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .munchkinClickable(
                onClick = onClick,
                bounded = false,
                rippleColor = when (gender) {
                    Gender.MALE -> MunchkinTheme.colors.primary
                    else -> MunchkinTheme.colors.secondary
                }
            )
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MunchkinIcon(
            imageVector = when (gender) {
                Gender.MALE -> Icons.Outlined.Male
                Gender.FEMALE -> Icons.Outlined.Female
            },
            tint = if (isSelected) MunchkinTheme.colors.primary else MunchkinTheme.colors.grey,
            size = 24.dp
        )

        MunchkinText(
            text = when (gender) {
                Gender.MALE -> stringResource(R.string.gender_male)
                Gender.FEMALE -> stringResource(R.string.gender_female)
            },
            style = MunchkinTheme.typography.labelMedium.copy(
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
            ),
            color = if (isSelected) MunchkinTheme.colors.primary else MunchkinTheme.colors.onBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddCharacterDialogPreview() {
    MunchkinTheme {
        AddCharacterDialog(
            onDismiss = {},
            onConfirm = { _, _ -> }
        )
    }
}
