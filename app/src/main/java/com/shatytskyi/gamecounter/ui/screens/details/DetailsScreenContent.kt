package com.shatytskyi.gamecounter.ui.screens.details

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shatytskyi.gamecounter.R
import com.shatytskyi.gamecounter.data.Character
import com.shatytskyi.gamecounter.data.Gender
import com.shatytskyi.gamecounter.ui.components.CharacterListItem
import com.shatytskyi.gamecounter.ui.components.MunchkinHorizontalDivider
import com.shatytskyi.gamecounter.ui.components.MunchkinIconTextButton
import com.shatytskyi.gamecounter.ui.components.MunchkinTopAppBar
import com.shatytskyi.gamecounter.ui.components.icons.Edit
import com.shatytskyi.gamecounter.ui.components.icons.MunchkinIcons
import com.shatytskyi.gamecounter.ui.components.icons.Remove
import com.shatytskyi.gamecounter.ui.components.icons.Reset
import com.shatytskyi.gamecounter.ui.theme.MunchkinTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailsScreenContent(
    character: Character,
    onLevelChange: (Int) -> Unit,
    onPowerChange: (Int) -> Unit,
    onGenderToggle: (Long) -> Unit,
    onFightClick: () -> Unit,
    onResetClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit,
    onTimerClick: () -> Unit,
    onDiceClick: () -> Unit,
    animatedContentScope: AnimatedContentScope?,
    sharedTransitionScope: SharedTransitionScope?,
    modifier: Modifier = Modifier
) {
    val haptic: HapticFeedback = LocalHapticFeedback.current

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Top app bar
        MunchkinTopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top)),
            title = "",
            onBack = onBackClick,
            animatedContentScope = animatedContentScope,
            sharedTransitionScope = sharedTransitionScope,
            titleSharedKey = null,
            actions = {
                DetailsAppBarActions(onTimerClick, onDiceClick, onFightClick)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Character card
        Box(
            modifier = if (sharedTransitionScope != null && animatedContentScope != null) {
                with(sharedTransitionScope) {
                    Modifier.sharedBounds(
                        sharedContentState = rememberSharedContentState(key = "character-card-${character.id}"),
                        animatedVisibilityScope = animatedContentScope
                    )
                }
            } else {
                Modifier
            }.padding(horizontal = 16.dp)
        ) {
            CharacterListItem(
                character = character,
                hideName = false,
                showLevelButtons = false,
                showItemsButtons = false,
                onLevelChange = onLevelChange,
                onItemsChange = onPowerChange,
                onGenderToggle = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    onGenderToggle(character.id)
                },
                animatedContentScope = null,
                sharedTransitionScope = null
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        DetailsControlsLayoutWithTransition(
            modifier = Modifier.weight(1f),
            onLevelChange = onLevelChange,
            onPowerChange = onPowerChange,
            characterId = character.id,
            animatedContentScope = animatedContentScope,
            sharedTransitionScope = sharedTransitionScope
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Divider
        MunchkinHorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Action buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Reset button
            MunchkinIconTextButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    onResetClick()
                },
                icon = MunchkinIcons.Reset,
                text = stringResource(R.string.reset),
                modifier = Modifier.weight(1f),
                textStyle = MunchkinTheme.typography.labelMedium,
                contentPadding = 16.dp,
                rippleColor = MunchkinTheme.colors.secondary,
                bounded = false
            )

            // Edit button (slightly larger)
            MunchkinIconTextButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    onEditClick()
                },
                icon = MunchkinIcons.Edit,
                text = stringResource(R.string.edit),
                modifier = Modifier.weight(1.3f),
                textStyle = MunchkinTheme.typography.labelMedium,
                contentPadding = 16.dp,
                rippleColor = MunchkinTheme.colors.primary,
                bounded = false
            )

            // Delete button
            MunchkinIconTextButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onDeleteClick()
                },
                icon = MunchkinIcons.Remove,
                text = stringResource(R.string.delete),
                modifier = Modifier.weight(1f),
                textStyle = MunchkinTheme.typography.labelMedium,
                contentPadding = 16.dp,
                rippleColor = MunchkinTheme.colors.red,
                bounded = false
            )
        }
    }
}

@Preview(
    name = "Details Content - Pixel 4",
    device = Devices.PIXEL_4,
    showSystemUi = true,
    showBackground = true
)
@Preview(
    name = "Details Content - Pixel 6 Pro",
    device = Devices.PIXEL_6_PRO,
    showSystemUi = true,
    showBackground = true
)
@Preview(
    name = "Details Content - Large Phone",
    device = "spec:width=428dp,height=926dp,dpi=460",
    showSystemUi = true,
    showBackground = true
)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun DetailsScreenContentPreview() {
    val mockCharacter = Character(
        id = 1,
        name = "Aragorn the King of Gondor",
        level = 8,
        items = 15,
        gender = Gender.MALE
    )

    MunchkinTheme {
        DetailsScreenContent(
            character = mockCharacter,
            onLevelChange = { },
            onPowerChange = { },
            onGenderToggle = { },
            onFightClick = { },
            onResetClick = { },
            onEditClick = { },
            onDeleteClick = { },
            onBackClick = { },
            onTimerClick = { },
            onDiceClick = { },
            animatedContentScope = null,
            sharedTransitionScope = null
        )
    }
}
