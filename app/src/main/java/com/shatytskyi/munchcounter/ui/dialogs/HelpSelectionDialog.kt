package com.shatytskyi.munchcounter.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.analytics.AnalyticsManager
import com.shatytskyi.munchcounter.data.Character
import com.shatytskyi.munchcounter.ui.components.MunchkinCard
import com.shatytskyi.munchcounter.ui.components.MunchkinCustomDialog
import com.shatytskyi.munchcounter.ui.components.MunchkinIcon
import com.shatytskyi.munchcounter.ui.components.MunchkinIconTextButton
import com.shatytskyi.munchcounter.ui.components.MunchkinText
import com.shatytskyi.munchcounter.ui.components.icons.ArrowTop
import com.shatytskyi.munchcounter.ui.components.icons.Close
import com.shatytskyi.munchcounter.ui.components.icons.MunchkinIcons
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme
import org.koin.compose.koinInject

data class HelperOption(
    val character: Character,
    val isClone: Boolean = false,
    val currentPower: Int = 0
)

@Composable
fun HelpSelectionDialog(
    characters: List<Character>,
    currentPlayer: Character,
    currentPlayerTempPower: Int,
    currentMonsterPower: Int,
    onDismiss: () -> Unit,
    onConfirm: (HelperOption) -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    val analyticsManager = koinInject<AnalyticsManager>()

    LaunchedEffect(Unit) {
        analyticsManager.logScreenView("Help Selection Dialog", "HelpSelectionDialog")
    }

    val currentPlayerTotalPower = currentPlayer.level + currentPlayer.items + currentPlayerTempPower

    // Create helper options list and sort by power (descending)
    val helperOptions = buildList {
        // Add clone of current player with temp power
        add(
            HelperOption(
                character = currentPlayer,
                isClone = true,
                currentPower = currentPlayerTotalPower
            )
        )

        // Add other characters
        characters.filter { it.id != currentPlayer.id }.forEach { character ->
            add(
                HelperOption(
                    character = character,
                    isClone = false,
                    currentPower = character.level + character.items
                )
            )
        }
    }.sortedByDescending { it.currentPower }

    MunchkinCustomDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
        header = {
            // Title in header with same total height as footer
            MunchkinText(
                text = stringResource(R.string.select_helper),
                style = MunchkinTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MunchkinTheme.colors.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(vertical = 32.dp), // Match total footer padding (16dp external + 16dp internal from button)
                textAlign = TextAlign.Center
            )
        },
        footer = {
            // Close button in footer
            MunchkinIconTextButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    onDismiss()
                },
                icon = MunchkinIcons.Close,
                text = stringResource(R.string.close),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 16.dp, bottom = 16.dp),
                textStyle = MunchkinTheme.typography.labelMedium,
                contentPadding = 16.dp,
                rippleColor = MunchkinTheme.colors.secondary,
                bounded = false
            )
        }
    ) {
        // Helper options list
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            helperOptions.forEach { option ->
                HelperOptionItem(
                    option = option,
                    currentPlayerPower = currentPlayerTotalPower,
                    currentMonsterPower = currentMonsterPower,
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                        onConfirm(option)
                    }
                )
            }
        }
    }
}

@Composable
private fun HelperOptionItem(
    option: HelperOption,
    currentPlayerPower: Int,
    currentMonsterPower: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalPowerWithHelper = currentPlayerPower + option.currentPower
    val battleResult = totalPowerWithHelper - currentMonsterPower

    val resultText = when {
        battleResult > 0 -> "+$battleResult"
        battleResult < 0 -> "$battleResult"
        else -> "="
    }

    val resultColor = when {
        battleResult > 0 -> MunchkinTheme.colors.primary
        battleResult < 0 -> MunchkinTheme.colors.red
        else -> MunchkinTheme.colors.primary
    }

    MunchkinCard(
        modifier = modifier
            .fillMaxWidth()
            .alpha(if (option.isClone) 0.9f else 1f),
        color = MunchkinTheme.colors.grey,
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Character info
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    MunchkinText(
                        text = option.character.name,
                        style = MunchkinTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = MunchkinTheme.colors.onBackground,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (option.isClone) {
                        MunchkinText(
                            text = "(${stringResource(R.string.clone)})",
                            style = MunchkinTheme.typography.bodyMedium,
                            color = MunchkinTheme.colors.grey,
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                    } else {
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    MunchkinText(
                        text = "${stringResource(R.string.level)} ${if (option.isClone) option.character.level else option.character.level} • ${
                            stringResource(
                                R.string.power
                                
                            )
                        } ${option.currentPower}",
                        style = MunchkinTheme.typography.labelSmall,
                        color = MunchkinTheme.colors.grey
                    )
                }
            }

            // Battle result
            Box(
                modifier = Modifier
                    .background(
                        color = resultColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    MunchkinIcon(
                        imageVector = MunchkinIcons.ArrowTop,
                        tint = resultColor,
                        size = 18.dp,
                        modifier = Modifier.rotate(90f)
                    )
                    MunchkinText(
                        textAlign = TextAlign.Center,
                        text = resultText,
                        style = MunchkinTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = resultColor
                    )
                }
            }
        }
    }
}
