package com.shatytskyi.gamecounter.ui.screens.details

import androidx.compose.runtime.Composable
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import com.shatytskyi.gamecounter.ui.components.MunchkinIcon
import com.shatytskyi.gamecounter.ui.components.MunchkinIconButton
import com.shatytskyi.gamecounter.ui.components.icons.Fight
import com.shatytskyi.gamecounter.ui.components.icons.MunchkinIcons
import com.shatytskyi.gamecounter.ui.components.icons.Timer
import com.shatytskyi.gamecounter.ui.components.icons.dice.Dice5
import com.shatytskyi.gamecounter.ui.theme.MunchkinTheme

@Composable
fun DetailsAppBarActions(
    onTimerClick: () -> Unit,
    onDiceClick: () -> Unit,
    onFightClick: () -> Unit
) {
    val haptic: HapticFeedback = LocalHapticFeedback.current

    MunchkinIconButton(onClick = {
        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
        onTimerClick()
    }) {
        MunchkinIcon(
            imageVector = MunchkinIcons.Timer,
            tint = MunchkinTheme.colors.onBackground
        )
    }
    MunchkinIconButton(onClick = {
        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
        onDiceClick()
    }) {
        MunchkinIcon(
            imageVector = MunchkinIcons.Dice.Dice5,
            tint = MunchkinTheme.colors.onBackground
        )
    }
    MunchkinIconButton(onClick = {
        haptic.performHapticFeedback(HapticFeedbackType.Confirm)
        onFightClick()
    }) {
        MunchkinIcon(
            imageVector = MunchkinIcons.Fight,
            tint = MunchkinTheme.colors.red
        )
    }
}
