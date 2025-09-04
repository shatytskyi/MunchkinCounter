package com.shatytskyi.munchcounter.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Female
import androidx.compose.material.icons.outlined.Male
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.R
import com.shatytskyi.munchcounter.data.Gender
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

@Composable
fun GenderSelector(
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
            tint = if (isSelected) {
                when (gender) {
                    Gender.MALE -> MunchkinTheme.colors.primary
                    else -> MunchkinTheme.colors.secondary
                }
            } else MunchkinTheme.colors.onBackground,
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
            color = if (isSelected) {
                when (gender) {
                    Gender.MALE -> MunchkinTheme.colors.primary
                    else -> MunchkinTheme.colors.secondary
                }
            } else MunchkinTheme.colors.onBackground
        )
    }
}
