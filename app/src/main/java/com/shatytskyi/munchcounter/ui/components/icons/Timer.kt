package com.shatytskyi.munchcounter.ui.components.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val MunchkinIcons.Timer: ImageVector
    get() {
        if (_Timer != null) {
            return _Timer!!
        }
        _Timer = ImageVector.Builder(
            name = "Timer",
            defaultWidth = 800.dp,
            defaultHeight = 800.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF1C274C)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(8.136f, 1.603f)
                curveTo(8.356f, 1.954f, 8.249f, 2.417f, 7.898f, 2.636f)
                lineTo(3.898f, 5.136f)
                curveTo(3.546f, 5.356f, 3.084f, 5.249f, 2.864f, 4.898f)
                curveTo(2.644f, 4.546f, 2.751f, 4.084f, 3.102f, 3.864f)
                lineTo(7.103f, 1.364f)
                curveTo(7.454f, 1.145f, 7.916f, 1.251f, 8.136f, 1.603f)
                close()
                moveTo(15.864f, 1.603f)
                curveTo(16.083f, 1.251f, 16.546f, 1.145f, 16.897f, 1.364f)
                lineTo(20.897f, 3.864f)
                curveTo(21.249f, 4.084f, 21.355f, 4.546f, 21.136f, 4.898f)
                curveTo(20.917f, 5.249f, 20.454f, 5.356f, 20.103f, 5.136f)
                lineTo(16.103f, 2.636f)
                curveTo(15.751f, 2.417f, 15.644f, 1.954f, 15.864f, 1.603f)
                close()
                moveTo(12f, 4.75f)
                curveTo(7.444f, 4.75f, 3.75f, 8.444f, 3.75f, 13f)
                curveTo(3.75f, 17.556f, 7.444f, 21.25f, 12f, 21.25f)
                curveTo(16.556f, 21.25f, 20.25f, 17.556f, 20.25f, 13f)
                curveTo(20.25f, 8.444f, 16.556f, 4.75f, 12f, 4.75f)
                close()
                moveTo(2.25f, 13f)
                curveTo(2.25f, 7.615f, 6.615f, 3.25f, 12f, 3.25f)
                curveTo(17.385f, 3.25f, 21.75f, 7.615f, 21.75f, 13f)
                curveTo(21.75f, 18.385f, 17.385f, 22.75f, 12f, 22.75f)
                curveTo(6.615f, 22.75f, 2.25f, 18.385f, 2.25f, 13f)
                close()
                moveTo(12f, 8.25f)
                curveTo(12.414f, 8.25f, 12.75f, 8.586f, 12.75f, 9f)
                verticalLineTo(12.689f)
                lineTo(15.03f, 14.97f)
                curveTo(15.323f, 15.263f, 15.323f, 15.738f, 15.03f, 16.03f)
                curveTo(14.737f, 16.323f, 14.263f, 16.323f, 13.97f, 16.03f)
                lineTo(11.47f, 13.53f)
                curveTo(11.329f, 13.39f, 11.25f, 13.199f, 11.25f, 13f)
                verticalLineTo(9f)
                curveTo(11.25f, 8.586f, 11.586f, 8.25f, 12f, 8.25f)
                close()
            }
        }.build()

        return _Timer!!
    }

@Suppress("ObjectPropertyName")
private var _Timer: ImageVector? = null
