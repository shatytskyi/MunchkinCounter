package com.shatytskyi.munchcounter.ui.components.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val MunchkinIcons.ArrowTop: ImageVector
    get() {
        if (_ArrowTop != null) {
            return _ArrowTop!!
        }
        _ArrowTop = ImageVector.Builder(
            name = "ArrowTop",
            defaultWidth = 800.dp,
            defaultHeight = 800.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF1C274C)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(6.47f, 10.03f)
                curveTo(6.177f, 9.737f, 6.177f, 9.263f, 6.47f, 8.97f)
                lineTo(11.47f, 3.97f)
                curveTo(11.763f, 3.677f, 12.237f, 3.677f, 12.53f, 3.97f)
                lineTo(17.53f, 8.97f)
                curveTo(17.823f, 9.263f, 17.823f, 9.737f, 17.53f, 10.03f)
                curveTo(17.237f, 10.323f, 16.763f, 10.323f, 16.47f, 10.03f)
                lineTo(12.75f, 6.311f)
                lineTo(12.75f, 14.5f)
                curveTo(12.75f, 15.213f, 12.97f, 16.3f, 13.609f, 17.187f)
                curveTo(14.22f, 18.035f, 15.244f, 18.75f, 17f, 18.75f)
                curveTo(17.414f, 18.75f, 17.75f, 19.086f, 17.75f, 19.5f)
                curveTo(17.75f, 19.914f, 17.414f, 20.25f, 17f, 20.25f)
                curveTo(14.756f, 20.25f, 13.28f, 19.298f, 12.391f, 18.063f)
                curveTo(11.53f, 16.867f, 11.25f, 15.453f, 11.25f, 14.5f)
                lineTo(11.25f, 6.311f)
                lineTo(7.53f, 10.03f)
                curveTo(7.237f, 10.323f, 6.763f, 10.323f, 6.47f, 10.03f)
                close()
            }
        }.build()

        return _ArrowTop!!
    }

@Suppress("ObjectPropertyName")
private var _ArrowTop: ImageVector? = null
