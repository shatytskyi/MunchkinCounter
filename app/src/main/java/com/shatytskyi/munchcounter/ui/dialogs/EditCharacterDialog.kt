package com.shatytskyi.munchcounter.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Female
import androidx.compose.material.icons.outlined.Male
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
import com.shatytskyi.munchcounter.ui.components.MunchkinIcon
import com.shatytskyi.munchcounter.ui.components.MunchkinIconTextButton
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.components.MunchkinTextField
import com.shatytskyi.munchcounter.ui.components.icons.Close
import com.shatytskyi.munchcounter.ui.components.icons.Edit
import com.shatytskyi.munchcounter.ui.components.icons.MunchkinIcons
import com.shatytskyi.munchcounter.ui.components.munchkinClickable
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
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
    var level by remember { mutableStateOf(character.level.toString()) }
    var items by remember { mutableStateOf(character.items.toString()) }
    var selectedGender by remember { mutableStateOf(character.gender) }
    val haptic = LocalHapticFeedback.current
    val focusRequester = remember { FocusRequester() }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    LaunchedEffect(Unit) {
        delay(200) // Delay for bottom sheet animation
        focusRequester.requestFocus()
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = bottomSheetState,
        containerColor = MunchkinTheme.colors.background,
        modifier = modifier
    ) {
        val scrollState = rememberScrollState()
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
                .windowInsetsPadding(WindowInsets.ime)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(bottom = 16.dp)
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

                // Custom TextField with TextFieldValue support for cursor positioning
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MunchkinTheme.colors.background)
                        .border(
                            width = 2.dp,
                            color = MunchkinTheme.colors.primary,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    val customTextSelectionColors = TextSelectionColors(
                        handleColor = MunchkinTheme.colors.primary,
                        backgroundColor = MunchkinTheme.colors.primary.copy(alpha = 0.4f)
                    )
                    
                    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
                        BasicTextField(
                            value = nameFieldValue,
                            onValueChange = { nameFieldValue = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .focusRequester(focusRequester),
                            textStyle = MunchkinTheme.typography.bodyLarge.copy(
                                color = MunchkinTheme.colors.onBackground,
                                textAlign = TextAlign.Start
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions.Default,
                            cursorBrush = SolidColor(MunchkinTheme.colors.primary)
                        )
                    }
                }
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
                        onValueChange = {
                            if (it.isEmpty() || it.toIntOrNull()?.let { value ->
                                    value in -999..999
                                } == true) {
                                level = it
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
                        onValueChange = {
                            if (it.isEmpty() || it.toIntOrNull()?.let { value ->
                                    value in -999..999
                                } == true) {
                                items = it
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
                    color = MunchkinTheme.colors.primary,
                    shape = RoundedCornerShape(16.dp)
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

                // Save button
                MunchkinIconTextButton(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                        val lvl = level.toIntOrNull()?.coerceIn(-999, 999) ?: character.level
                        val itm = items.toIntOrNull()?.coerceIn(-999, 999) ?: character.items
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
