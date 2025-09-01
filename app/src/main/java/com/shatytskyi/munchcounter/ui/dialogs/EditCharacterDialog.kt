package com.shatytskyi.munchcounter.ui.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Female
import androidx.compose.material.icons.outlined.Male
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.data.Character
import com.shatytskyi.munchcounter.data.Gender
import com.shatytskyi.munchcounter.ui.components.MunchkinCard
import com.shatytskyi.munchcounter.ui.components.MunchkinDialog
import com.shatytskyi.munchcounter.ui.components.MunchkinIcon
import com.shatytskyi.munchcounter.ui.components.MunchkinText
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

    MunchkinDialog(
        onDismissRequest = onDismiss,
        title = "Edit Player",
        content = {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Name field
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    MunchkinText(
                        text = "Name:",
                        style = MunchkinTheme.typography.labelMedium,
                        color = MunchkinTheme.colors.onBackground
                    )
                    MunchkinCard(
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        BasicTextField(
                            value = name,
                            onValueChange = { name = it },
                            textStyle = MunchkinTheme.typography.bodyMedium.copy(
                                color = MunchkinTheme.colors.onBackground
                            ),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }

                // Level and Items row
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Level field
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        MunchkinText(
                            text = "Level:",
                            style = MunchkinTheme.typography.labelMedium,
                            color = MunchkinTheme.colors.onBackground
                        )
                        MunchkinCard(
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            BasicTextField(
                                value = level,
                                onValueChange = {
                                    if (it.isEmpty() || it.toIntOrNull() != null) {
                                        level = it
                                    }
                                },
                                textStyle = MunchkinTheme.typography.bodyMedium.copy(
                                    color = MunchkinTheme.colors.onBackground,
                                    textAlign = TextAlign.Center
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }
                    }

                    // Items field
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        MunchkinText(
                            text = "Items:",
                            style = MunchkinTheme.typography.labelMedium,
                            color = MunchkinTheme.colors.onBackground
                        )
                        MunchkinCard(
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            BasicTextField(
                                value = items,
                                onValueChange = {
                                    if (it.isEmpty() || it.toIntOrNull() != null) {
                                        items = it
                                    }
                                },
                                textStyle = MunchkinTheme.typography.bodyMedium.copy(
                                    color = MunchkinTheme.colors.onBackground,
                                    textAlign = TextAlign.Center
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }
                    }
                }

                // Gender selection
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    MunchkinText(
                        text = "Gender:",
                        style = MunchkinTheme.typography.labelMedium,
                        color = MunchkinTheme.colors.onBackground
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Gender.entries.forEach { gender ->
                            MunchkinCard(
                                modifier = Modifier
                                    .weight(1f)
                                    .selectable(
                                        selected = selectedGender == gender,
                                        onClick = { selectedGender = gender }
                                    ),
                                shape = RoundedCornerShape(8.dp),
                                color = if (selectedGender == gender) MunchkinTheme.colors.primary else MunchkinTheme.colors.background
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    MunchkinIcon(
                                        imageVector = when (gender) {
                                            Gender.MALE -> Icons.Outlined.Male
                                            Gender.FEMALE -> Icons.Outlined.Female
                                        },
                                        tint = if (selectedGender == gender) MunchkinTheme.colors.onBackground else MunchkinTheme.colors.grey,
                                        size = 16.dp
                                    )
                                    MunchkinText(
                                        text = gender.displayName,
                                        style = MunchkinTheme.typography.bodyMedium,
                                        color = if (selectedGender == gender) MunchkinTheme.colors.onBackground else MunchkinTheme.colors.grey
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            MunchkinText(
                modifier = Modifier.clickable(
                    enabled = name.trim().isNotEmpty(),
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        val lvl = level.toIntOrNull() ?: character.lvl
                        val itm = items.toIntOrNull() ?: character.items
                        onConfirm(name.trim(), lvl, itm, selectedGender)
                    },
                ),
                text = "Save",
            )
        },
        dismissButton = {
            MunchkinText(
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onDismiss,
                ),
                text = "Cancel"
            )
        },
        modifier = modifier
    )
}
