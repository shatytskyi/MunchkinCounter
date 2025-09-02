package com.shatytskyi.munchcounter.ui.components.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val MunchkinIcons.Fight: ImageVector
    get() {
        if (_Fight != null) {
            return _Fight!!
        }
        _Fight = ImageVector.Builder(
            name = "Fight",
            defaultWidth = 800.dp,
            defaultHeight = 800.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12f, 9f)
                arcToRelative(3f, 3f, 0f, isMoreThanHalf = true, isPositiveArc = false, 3f, 3f)
                arcTo(3f, 3f, 0f, isMoreThanHalf = false, isPositiveArc = false, 12f, 9f)
                close()
                moveTo(12f, 13f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = true, isPositiveArc = true, 1f, -1f)
                arcTo(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12f, 13f)
                close()
                moveTo(21f, 11f)
                lineTo(19.93f, 11f)
                arcTo(8f, 8f, 0f, isMoreThanHalf = false, isPositiveArc = false, 13f, 4.07f)
                lineTo(13f, 3f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, -2f, 0f)
                lineTo(11f, 4.07f)
                arcTo(8f, 8f, 0f, isMoreThanHalf = false, isPositiveArc = false, 4.07f, 11f)
                lineTo(3f, 11f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, 2f)
                lineTo(4.07f, 13f)
                arcTo(8f, 8f, 0f, isMoreThanHalf = false, isPositiveArc = false, 11f, 19.93f)
                lineTo(11f, 21f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2f, 0f)
                lineTo(13f, 19.93f)
                arcTo(8f, 8f, 0f, isMoreThanHalf = false, isPositiveArc = false, 19.93f, 13f)
                lineTo(21f, 13f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, -2f)
                close()
                moveTo(12f, 18f)
                arcToRelative(6f, 6f, 0f, isMoreThanHalf = true, isPositiveArc = true, 6f, -6f)
                arcTo(6f, 6f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12f, 18f)
                close()
            }
        }.build()

        return _Fight!!
    }

@Suppress("ObjectPropertyName")
private var _Fight: ImageVector? = null
