package com.shatytskyi.munchcounter.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.data.Character
import com.shatytskyi.munchcounter.data.Gender
import com.shatytskyi.munchcounter.ui.components.GenderSelector
import com.shatytskyi.munchcounter.ui.components.MunchkinCustomDialog
import com.shatytskyi.munchcounter.ui.components.MunchkinCard
import com.shatytskyi.munchcounter.ui.components.MunchkinIconTextButton
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.components.MunchkinTextField
import com.shatytskyi.munchcounter.ui.components.icons.Close
import com.shatytskyi.munchcounter.ui.components.icons.Edit
import com.shatytskyi.munchcounter.ui.components.icons.MunchkinIcons
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme
import com.shatytskyi.munchcounter.analytics.AnalyticsManager
import com.shatytskyi.munchcounter.analytics.AnalyticsEvents
import com.shatytskyi.munchcounter.analytics.bundleOf
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

@Composable
fun EditCharacterDialog(
    character: Character,
    onDismiss: () -> Unit,
    onConfirm: (String, Int, Int, Gender) -> Unit,
    modifier: Modifier = Modifier
) {
    var nameFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = character.name,
                selection = TextRange(character.name.length)
            )
        )
    }
    var level by remember { mutableStateOf(TextFieldValue(character.level.toString())) }
    var items by remember { mutableStateOf(TextFieldValue(character.items.toString())) }
    var selectedGender by remember { mutableStateOf(character.gender) }
    val haptic = LocalHapticFeedback.current
    val analyticsManager = koinInject<AnalyticsManager>()
    val focusRequester = remember { FocusRequester() }
    
    // Track what was changed
    val hasNameChanged = remember(nameFieldValue.text) { 
        nameFieldValue.text.trim() != character.name 
    }
    val hasLevelChanged = remember(level.text) { 
        level.text.toIntOrNull() != character.level 
    }
    val hasItemsChanged = remember(items.text) { 
        items.text.toIntOrNull() != character.items 
    }
    val hasGenderChanged = remember(selectedGender) { 
        selectedGender != character.gender 
    }

    LaunchedEffect(Unit) {
        // Log dialog view
        analyticsManager.logScreenView("Edit Player Dialog", "EditCharacterDialog")
        delay(200) // Delay for bottom sheet animation
        focusRequester.requestFocus()
    }

    MunchkinCustomDialog(
        onDismissRequest = onDismiss,
        modifier = modifier
    ) {
            // Header
            MunchkinText(
                text = stringResource(R.string.edit_player),
                style = MunchkinTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MunchkinTheme.colors.onBackground,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

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

                Spacer(modifier = Modifier.height(12.dp))

                MunchkinTextField(
                    value = nameFieldValue,
                    onValueChange = { nameFieldValue = it },
                    keyboardType = KeyboardType.Text,
                    focusRequester = focusRequester,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Level and Items row
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
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

                    Spacer(modifier = Modifier.height(12.dp))

                    MunchkinTextField(
                        value = level,
                        onValueChange = { newValue ->
                            if (newValue.text.isEmpty() || newValue.text.toIntOrNull()?.let { value ->
                                    value in -999..999
                                } == true) {
                                level = newValue
                            }
                        },
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

                    Spacer(modifier = Modifier.height(12.dp))

                    MunchkinTextField(
                        value = items,
                        onValueChange = { newValue ->
                            if (newValue.text.isEmpty() || newValue.text.toIntOrNull()?.let { value ->
                                    value in -999..999
                                } == true) {
                                items = newValue
                            }
                        },
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
                    text = stringResource(R.string.gender),
                    style = MunchkinTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MunchkinTheme.colors.onBackground
                )

                Spacer(modifier = Modifier.height(12.dp))

                MunchkinCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = MunchkinTheme.colors.background,
                    color = MunchkinTheme.colors.grey,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Gender.entries.forEach { gender ->
                            GenderSelector(
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

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Cancel button
                MunchkinIconTextButton(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                        // Track cancellation with what was being edited
                        analyticsManager.logEvent(
                            "edit_player_cancelled",
                            bundleOf(
                                "had_name_change" to hasNameChanged,
                                "had_level_change" to hasLevelChanged,
                                "had_items_change" to hasItemsChanged,
                                "had_gender_change" to hasGenderChanged
                            )
                        )
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

                // Save button
                MunchkinIconTextButton(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                        val lvl = level.text.toIntOrNull()?.coerceIn(-999, 999) ?: character.level
                        val itm = items.text.toIntOrNull()?.coerceIn(-999, 999) ?: character.items
                        
                        // Track what was modified
                        analyticsManager.logEvent(
                            AnalyticsEvents.PLAYER_MODIFIED,
                            bundleOf(
                                "source" to "edit_dialog",
                                "changed_name" to hasNameChanged,
                                "changed_level" to hasLevelChanged,
                                "changed_items" to hasItemsChanged,
                                "changed_gender" to hasGenderChanged,
                                "level_delta" to (lvl - character.level),
                                "items_delta" to (itm - character.items)
                            )
                        )
                        
                        onConfirm(nameFieldValue.text.trim(), lvl, itm, selectedGender)
                    },
                    icon = MunchkinIcons.Edit,
                    text = stringResource(R.string.save),
                    modifier = Modifier.weight(1f),
                    textStyle = MunchkinTheme.typography.labelMedium,
                    contentPadding = 16.dp,
                    rippleColor = MunchkinTheme.colors.primary,
                    bounded = false,
                    enabled = nameFieldValue.text.trim().isNotEmpty()
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
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
