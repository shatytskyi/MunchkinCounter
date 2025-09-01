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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Female
import androidx.compose.material.icons.outlined.Male
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.data.Gender
import com.shatytskyi.munchcounter.ui.components.MunchkinCard
import com.shatytskyi.munchcounter.ui.components.MunchkinDialog
import com.shatytskyi.munchcounter.ui.components.MunchkinIcon
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

@Composable
fun AddCharacterDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, Gender) -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf(Gender.MALE) }

    MunchkinDialog(
        onDismissRequest = onDismiss,
        title = "Add New Player",
        content = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Name input
                MunchkinText(
                    text = "Player Name:",
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

                // Gender selection
                MunchkinText(
                    text = "Gender:",
                    style = MunchkinTheme.typography.labelMedium,
                    color = MunchkinTheme.colors.onBackground
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
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
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                MunchkinIcon(
                                    imageVector = when (gender) {
                                        Gender.MALE -> Icons.Outlined.Male
                                        Gender.FEMALE -> Icons.Outlined.Female
                                    },
                                    tint = if (selectedGender == gender) MunchkinTheme.colors.onBackground else MunchkinTheme.colors.grey
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
        },
        confirmButton = {
            MunchkinText(
                modifier = Modifier.clickable(
                    enabled = name.trim().isNotEmpty(),
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = { onConfirm(name.trim(), selectedGender) },
                ),
                text = "Add",
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
