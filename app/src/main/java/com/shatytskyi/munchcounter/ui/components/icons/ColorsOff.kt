package com.shatytskyi.munchcounter.ui.components.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val MunchkinIcons.ColorsOff: ImageVector
    get() {
        if (_ColorsOff != null) {
            return _ColorsOff!!
        }
        _ColorsOff = ImageVector.Builder(
            name = "ColorsOff",
            defaultWidth = 800.dp,
            defaultHeight = 800.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color(0xFF1C274C)),
                strokeLineWidth = 1.5f
            ) {
                moveTo(16f, 8f)
                moveToRelative(-2f, 0f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = true, isPositiveArc = true, 4f, 0f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = true, isPositiveArc = true, -4f, 0f)
            }
            path(
                stroke = SolidColor(Color(0xFF1C274C)),
                strokeLineWidth = 1.5f,
                strokeLineCap = StrokeCap.Round
            ) {
                moveTo(2f, 12.5f)
                lineTo(3.752f, 10.967f)
                curveTo(4.663f, 10.17f, 6.036f, 10.216f, 6.892f, 11.072f)
                lineTo(11.182f, 15.362f)
                curveTo(11.869f, 16.049f, 12.951f, 16.143f, 13.746f, 15.584f)
                lineTo(14.045f, 15.374f)
                curveTo(15.189f, 14.57f, 16.737f, 14.663f, 17.777f, 15.599f)
                lineTo(21f, 18.5f)
            }
            path(
                stroke = SolidColor(Color(0xFF1C274C)),
                strokeLineWidth = 1.5f,
                strokeLineCap = StrokeCap.Round
            ) {
                moveTo(22f, 12f)
                curveTo(22f, 16.714f, 22f, 19.071f, 20.535f, 20.535f)
                curveTo(19.071f, 22f, 16.714f, 22f, 12f, 22f)
                curveTo(7.286f, 22f, 4.929f, 22f, 3.464f, 20.535f)
                curveTo(2f, 19.071f, 2f, 16.714f, 2f, 12f)
                curveTo(2f, 7.286f, 2f, 4.929f, 3.464f, 3.464f)
                curveTo(4.929f, 2f, 7.286f, 2f, 12f, 2f)
                curveTo(16.714f, 2f, 19.071f, 2f, 20.535f, 3.464f)
                curveTo(21.509f, 4.438f, 21.836f, 5.807f, 21.945f, 8f)
            }
        }.build()

        return _ColorsOff!!
    }

@Suppress("ObjectPropertyName")
private var _ColorsOff: ImageVector? = null
