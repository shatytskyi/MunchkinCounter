package com.shatytskyi.gamecounter.ui.screens.details

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailsAppBarActions(
    onTimerClick: () -> Unit,
    onDiceClick: () -> Unit,
    onFightClick: () -> Unit,
    animatedContentScope: AnimatedContentScope? = null,
    sharedTransitionScope: SharedTransitionScope? = null
) {
    val haptic: HapticFeedback = LocalHapticFeedback.current

    val timerModifier = if (sharedTransitionScope != null && animatedContentScope != null) {
        with(sharedTransitionScope) {
            Modifier.sharedElement(
                sharedContentState = rememberSharedContentState(key = "timer-icon"),
                animatedVisibilityScope = animatedContentScope
            )
        }
    } else {
        Modifier
    }

    val diceModifier = if (sharedTransitionScope != null && animatedContentScope != null) {
        with(sharedTransitionScope) {
            Modifier.sharedElement(
                sharedContentState = rememberSharedContentState(key = "dice-icon"),
                animatedVisibilityScope = animatedContentScope
            )
        }
    } else {
        Modifier
    }

    val fightModifier = if (sharedTransitionScope != null && animatedContentScope != null) {
        with(sharedTransitionScope) {
            Modifier.sharedElement(
                sharedContentState = rememberSharedContentState(key = "fight-icon"),
                animatedVisibilityScope = animatedContentScope
            )
        }
    } else {
        Modifier
    }

    MunchkinIconButton(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
            onTimerClick()
        },
        modifier = timerModifier
    ) {
        MunchkinIcon(
            imageVector = MunchkinIcons.Timer,
            tint = MunchkinTheme.colors.onBackground
        )
    }

    MunchkinIconButton(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
            onDiceClick()
        },
        modifier = diceModifier
    ) {
        MunchkinIcon(
            imageVector = MunchkinIcons.Dice.Dice5,
            tint = MunchkinTheme.colors.onBackground
        )
    }

    MunchkinIconButton(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.Confirm)
            onFightClick()
        },
        modifier = fightModifier
    ) {
        MunchkinIcon(
            imageVector = MunchkinIcons.Fight,
            tint = MunchkinTheme.colors.red
        )
    }
}
