package com.shatytskyi.munchcounter.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.data.Character
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
            MunchkinTextButton(
                onClick = onConfirm,
                text = "Confirm",
                color = MunchkinTheme.colors.red
            )
        },
        dismissButton = {
            MunchkinTextButton(
                onClick = onDismiss,
                text = "Cancel"
            )
        },
        modifier = modifier
    )
}

@Composable
fun AddCharacterDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }

    MunchkinDialog(
        onDismissRequest = onDismiss,
        title = "Add New Player",
        content = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
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
                            .padding(12.dp)
                    )
                }
            }
        },
        confirmButton = {
            MunchkinTextButton(
                onClick = { onConfirm(name.trim()) },
                text = "Add",
                enabled = name.trim().isNotEmpty()
            )
        },
        dismissButton = {
            MunchkinTextButton(
                onClick = onDismiss,
                text = "Cancel"
            )
        },
        modifier = modifier
    )
}

@Composable
fun EditCharacterDialog(
    character: Character,
    onDismiss: () -> Unit,
    onConfirm: (String, Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf(character.name) }
    var level by remember { mutableStateOf(character.lvl.toString()) }
    var items by remember { mutableStateOf(character.items.toString()) }

    MunchkinDialog(
        onDismissRequest = onDismiss,
        title = "Edit Player",
        content = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Name field
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
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
                                .padding(12.dp)
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
                        verticalArrangement = Arrangement.spacedBy(4.dp)
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
                                    .padding(12.dp)
                            )
                        }
                    }

                    // Items field
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
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
                                    .padding(12.dp)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            MunchkinTextButton(
                onClick = {
                    val lvl = level.toIntOrNull() ?: character.lvl
                    val itm = items.toIntOrNull() ?: character.items
                    onConfirm(name.trim(), lvl, itm)
                },
                text = "Save",
                enabled = name.trim().isNotEmpty()
            )
        },
        dismissButton = {
            MunchkinTextButton(
                onClick = onDismiss,
                text = "Cancel"
            )
        },
        modifier = modifier
    )
}

@Composable
fun CommonDiceDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var result by remember { mutableStateOf<Int?>(null) }

    MunchkinDialog(
        onDismissRequest = onDismiss,
        title = "Roll Dice",
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Result display
                result?.let { diceResult ->
                    MunchkinText(
                        text = "Result: $diceResult",
                        style = MunchkinTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MunchkinTheme.colors.primary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Roll button
                MunchkinOutlinedButton(
                    onClick = { result = (1..6).random() },
                    containerColor = MunchkinTheme.colors.primary
                ) {
                    MunchkinText(
                        text = "Roll",
                        style = MunchkinTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = MunchkinTheme.colors.onBackground
                    )
                }
            }
        },
        confirmButton = {
            MunchkinTextButton(
                onClick = onDismiss,
                text = "Close"
            )
        },
        modifier = modifier
    )
}
