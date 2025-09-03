package com.shatytskyi.munchcounter.ui.components.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val MunchkinIcons.Close: ImageVector
    get() {
        if (_Close != null) {
            return _Close!!
        }
        _Close = ImageVector.Builder(
            name = "Close",
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
                moveTo(14.5f, 9.5f)
                lineTo(9.5f, 14.5f)
                moveTo(9.5f, 9.5f)
                lineTo(14.5f, 14.5f)
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

        return _Close!!
    }

@Suppress("ObjectPropertyName")
private var _Close: ImageVector? = null
