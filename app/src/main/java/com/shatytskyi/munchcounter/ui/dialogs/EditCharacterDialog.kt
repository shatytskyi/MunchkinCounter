package com.shatytskyi.munchcounter.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.data.Character
import com.shatytskyi.munchcounter.data.Gender
import com.shatytskyi.munchcounter.ui.components.MunchkinCard
import com.shatytskyi.munchcounter.ui.components.MunchkinDialog
import com.shatytskyi.munchcounter.ui.components.MunchkinIcon
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.components.MunchkinTextField
import com.shatytskyi.munchcounter.ui.components.munchkinClickable
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

@Composable
fun EditCharacterDialog(
    character: Character,
    onDismiss: () -> Unit,
    onConfirm: (String, Int, Int, Gender) -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf(character.name) }
    var level by remember { mutableStateOf(character.lvl.toString()) }
    var items by remember { mutableStateOf(character.items.toString()) }
    var selectedGender by remember { mutableStateOf(character.gender) }
    val haptic = LocalHapticFeedback.current

    MunchkinDialog(
        onDismissRequest = onDismiss,
        title = stringResource(R.string.edit_player),
        content = {
            Column {
                Spacer(modifier = Modifier.height(24.dp))

                // Name field
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
                        placeholder = stringResource(R.string.enter_player_name),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Level and Items row
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Level field
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        MunchkinText(
                            text = stringResource(R.string.level),
                            style = MunchkinTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = MunchkinTheme.colors.onBackground
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        MunchkinTextField(
                            value = level,
                            onValueChange = {
                                if (it.isEmpty() || it.toIntOrNull() != null) {
                                    level = it
                                }
                            },
                            placeholder = stringResource(R.string.zero),
                            keyboardType = KeyboardType.Number,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Items field
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        MunchkinText(
                            text = stringResource(R.string.items),
                            style = MunchkinTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = MunchkinTheme.colors.onBackground
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        MunchkinTextField(
                            value = items,
                            onValueChange = {
                                if (it.isEmpty() || it.toIntOrNull() != null) {
                                    items = it
                                }
                            },
                            placeholder = stringResource(R.string.zero),
                            keyboardType = KeyboardType.Number,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Gender selection
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

                Spacer(modifier = Modifier.height(24.dp))
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
                        val lvl = level.toIntOrNull() ?: character.lvl
                        val itm = items.toIntOrNull() ?: character.items
                        onConfirm(name.trim(), lvl, itm, selectedGender)
                    }
                ),
                style = MunchkinTheme.typography.bodyLarge,
                text = stringResource(R.string.save),
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
private fun EditCharacterDialogPreview() {
    val mockCharacter = Character(1, "Aragorn", 5, 8, Gender.MALE)

    MunchkinTheme {
        EditCharacterDialog(
            character = mockCharacter,
            onDismiss = {},
            onConfirm = { _, _, _, _ -> }
        )
    }
}
