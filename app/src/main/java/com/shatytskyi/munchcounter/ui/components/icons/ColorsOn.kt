package com.shatytskyi.munchcounter.ui.components.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val MunchkinIcons.ColorsOn: ImageVector
    get() {
        if (_ColorsOn != null) {
            return _ColorsOn!!
        }
        _ColorsOn = ImageVector.Builder(
            name = "ColorsOn",
            defaultWidth = 800.dp,
            defaultHeight = 800.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
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
                curveTo(2f, 10.872f, 2f, 9.878f, 2.02f, 9f)
                moveTo(12f, 2f)
                curveTo(7.286f, 2f, 4.929f, 2f, 3.464f, 3.464f)
                curveTo(3.04f, 3.889f, 2.738f, 4.389f, 2.524f, 5f)
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
            path(fill = SolidColor(Color(0xFF1C274C))) {
                moveTo(16.06f, 8.57f)
                lineTo(16.552f, 8.004f)
                lineTo(16.552f, 8.004f)
                lineTo(16.06f, 8.57f)
                close()
                moveTo(18f, 3.968f)
                lineTo(17.468f, 4.497f)
                curveTo(17.609f, 4.638f, 17.8f, 4.718f, 18f, 4.718f)
                curveTo(18.2f, 4.718f, 18.391f, 4.638f, 18.532f, 4.497f)
                lineTo(18f, 3.968f)
                close()
                moveTo(19.94f, 8.57f)
                lineTo(19.448f, 8.004f)
                lineTo(19.94f, 8.57f)
                close()
                moveTo(18f, 9.606f)
                lineTo(18f, 8.856f)
                horizontalLineTo(18f)
                lineTo(18f, 9.606f)
                close()
                moveTo(16.552f, 8.004f)
                curveTo(16.066f, 7.582f, 15.6f, 7.109f, 15.26f, 6.63f)
                curveTo(14.913f, 6.14f, 14.75f, 5.716f, 14.75f, 5.375f)
                horizontalLineTo(13.25f)
                curveTo(13.25f, 6.163f, 13.608f, 6.893f, 14.036f, 7.497f)
                curveTo(14.471f, 8.111f, 15.035f, 8.672f, 15.568f, 9.136f)
                lineTo(16.552f, 8.004f)
                close()
                moveTo(14.75f, 5.375f)
                curveTo(14.75f, 4.442f, 15.17f, 3.971f, 15.584f, 3.818f)
                curveTo(16.01f, 3.662f, 16.713f, 3.738f, 17.468f, 4.497f)
                lineTo(18.532f, 3.439f)
                curveTo(17.487f, 2.388f, 16.19f, 1.997f, 15.066f, 2.411f)
                curveTo(13.93f, 2.829f, 13.25f, 3.966f, 13.25f, 5.375f)
                horizontalLineTo(14.75f)
                close()
                moveTo(20.431f, 9.136f)
                curveTo(20.965f, 8.672f, 21.529f, 8.111f, 21.964f, 7.497f)
                curveTo(22.392f, 6.893f, 22.75f, 6.163f, 22.75f, 5.375f)
                horizontalLineTo(21.25f)
                curveTo(21.25f, 5.716f, 21.087f, 6.14f, 20.74f, 6.63f)
                curveTo(20.4f, 7.109f, 19.934f, 7.582f, 19.448f, 8.004f)
                lineTo(20.431f, 9.136f)
                close()
                moveTo(22.75f, 5.375f)
                curveTo(22.75f, 3.966f, 22.07f, 2.829f, 20.934f, 2.411f)
                curveTo(19.81f, 1.997f, 18.513f, 2.388f, 17.468f, 3.439f)
                lineTo(18.532f, 4.497f)
                curveTo(19.287f, 3.738f, 19.99f, 3.662f, 20.416f, 3.818f)
                curveTo(20.83f, 3.971f, 21.25f, 4.442f, 21.25f, 5.375f)
                horizontalLineTo(22.75f)
                close()
                moveTo(15.568f, 9.136f)
                curveTo(16.325f, 9.793f, 16.929f, 10.356f, 18f, 10.356f)
                lineTo(18f, 8.856f)
                curveTo(17.576f, 8.856f, 17.385f, 8.727f, 16.552f, 8.004f)
                lineTo(15.568f, 9.136f)
                close()
                moveTo(19.448f, 8.004f)
                curveTo(18.615f, 8.727f, 18.424f, 8.856f, 18f, 8.856f)
                lineTo(18f, 10.356f)
                curveTo(19.071f, 10.356f, 19.675f, 9.794f, 20.431f, 9.136f)
                lineTo(19.448f, 8.004f)
                close()
            }
        }.build()

        return _ColorsOn!!
    }

@Suppress("ObjectPropertyName")
private var _ColorsOn: ImageVector? = null
