package com.shatytskyi.munchcounter.ui.components.icons

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val MunchkinIcons.Timer: ImageVector
    get() {
        if (_timer != null) {
            return _timer!!
        }
        _timer = materialIcon(name = "Timer") {
            materialPath {
                moveTo(14.2f, 1.2f)
                horizontalLineTo(9.8f)
                verticalLineTo(2.8f)
                horizontalLineToRelative(4.4f)
                verticalLineTo(1.2f)
                close()
                moveTo(11.2f, 13.8f)
                horizontalLineToRelative(1.6f)
                verticalLineTo(8.2f)
                horizontalLineToRelative(-1.6f)
                verticalLineTo(13.8f)
                close()
                moveTo(18.63f, 7.51f)
                lineToRelative(1.14f, -1.14f)
                curveToRelative(-0.34f, -0.41f, -0.72f, -0.79f, -1.13f, -1.13f)
                lineToRelative(-1.14f, 1.14f)
                curveTo(16.26f, 4.99f, 14.52f, 4.2f, 12.0f, 4.2f)
                curveToRelative(-4.31f, 0.0f, -7.8f, 3.49f, -7.8f, 7.8f)
                reflectiveCurveToRelative(3.49f, 7.8f, 7.8f, 7.8f)
                reflectiveCurveToRelative(7.8f, -3.49f, 7.8f, -7.8f)
                curveTo(19.8f, 10.9f, 19.21f, 9.14f, 18.63f, 7.51f)
                close()
                moveTo(12.0f, 18.4f)
                curveToRelative(-3.42f, 0.0f, -6.2f, -2.78f, -6.2f, -6.2f)
                reflectiveCurveToRelative(2.78f, -6.2f, 6.2f, -6.2f)
                reflectiveCurveToRelative(6.2f, 2.78f, 6.2f, 6.2f)
                reflectiveCurveTo(15.42f, 18.4f, 12.0f, 18.4f)
                close()
            }
        }
        return _timer!!
    }

private var _timer: ImageVector? = null
