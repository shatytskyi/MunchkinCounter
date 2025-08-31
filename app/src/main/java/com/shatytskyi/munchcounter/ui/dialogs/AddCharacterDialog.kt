package com.shatytskyi.munchcounter.ui.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.ui.components.MunchkinCard
import com.shatytskyi.munchcounter.ui.components.MunchkinDialog
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

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
                verticalArrangement = Arrangement.spacedBy(16.dp)
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
                            .padding(16.dp)
                    )
                }
            }
        },
        confirmButton = {
            MunchkinText(
                modifier = Modifier.clickable(
                    enabled = name.trim().isNotEmpty(),
                    onClick = { onConfirm(name.trim()) },
                ),
                text = "Add",
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
