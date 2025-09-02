package com.shatytskyi.munchcounter.ui.components.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val MunchkinIcons.Reset: ImageVector
    get() {
        if (_Reset != null) {
            return _Reset!!
        }
        _Reset = ImageVector.Builder(
            name = "Reset",
            defaultWidth = 800.dp,
            defaultHeight = 800.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFF1C274C))) {
                moveTo(16.728f, 6f)
                curveTo(16.728f, 5.586f, 16.392f, 5.25f, 15.977f, 5.25f)
                curveTo(15.563f, 5.25f, 15.227f, 5.586f, 15.227f, 6f)
                verticalLineTo(7.023f)
                curveTo(12.988f, 5.47f, 9.911f, 5.708f, 7.928f, 7.738f)
                curveTo(5.691f, 10.028f, 5.691f, 13.735f, 7.928f, 16.025f)
                curveTo(10.175f, 18.325f, 13.825f, 18.325f, 16.072f, 16.025f)
                curveTo(17.375f, 14.691f, 17.917f, 12.878f, 17.705f, 11.151f)
                curveTo(17.655f, 10.74f, 17.281f, 10.447f, 16.87f, 10.498f)
                curveTo(16.459f, 10.548f, 16.166f, 10.922f, 16.217f, 11.333f)
                curveTo(16.376f, 12.634f, 15.967f, 13.986f, 14.999f, 14.977f)
                curveTo(13.341f, 16.674f, 10.659f, 16.674f, 9.001f, 14.977f)
                curveTo(7.333f, 13.269f, 7.333f, 10.494f, 9.001f, 8.786f)
                curveTo(10.324f, 7.432f, 12.298f, 7.158f, 13.884f, 7.965f)
                horizontalLineTo(13.326f)
                curveTo(12.912f, 7.965f, 12.576f, 8.3f, 12.576f, 8.715f)
                curveTo(12.576f, 9.129f, 12.912f, 9.465f, 13.326f, 9.465f)
                horizontalLineTo(15.977f)
                curveTo(16.392f, 9.465f, 16.728f, 9.129f, 16.728f, 8.715f)
                verticalLineTo(6f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF1C274C)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(11.943f, 1.25f)
                curveTo(9.634f, 1.25f, 7.825f, 1.25f, 6.414f, 1.44f)
                curveTo(4.969f, 1.634f, 3.829f, 2.039f, 2.934f, 2.934f)
                curveTo(2.039f, 3.829f, 1.634f, 4.969f, 1.44f, 6.414f)
                curveTo(1.25f, 7.825f, 1.25f, 9.634f, 1.25f, 11.943f)
                verticalLineTo(12.057f)
                curveTo(1.25f, 14.366f, 1.25f, 16.175f, 1.44f, 17.586f)
                curveTo(1.634f, 19.031f, 2.039f, 20.171f, 2.934f, 21.066f)
                curveTo(3.829f, 21.961f, 4.969f, 22.366f, 6.414f, 22.56f)
                curveTo(7.825f, 22.75f, 9.634f, 22.75f, 11.943f, 22.75f)
                horizontalLineTo(12.057f)
                curveTo(14.366f, 22.75f, 16.175f, 22.75f, 17.586f, 22.56f)
                curveTo(19.031f, 22.366f, 20.171f, 21.961f, 21.066f, 21.066f)
                curveTo(21.961f, 20.171f, 22.366f, 19.031f, 22.56f, 17.586f)
                curveTo(22.75f, 16.175f, 22.75f, 14.366f, 22.75f, 12.057f)
                verticalLineTo(11.943f)
                curveTo(22.75f, 9.634f, 22.75f, 7.825f, 22.56f, 6.414f)
                curveTo(22.366f, 4.969f, 21.961f, 3.829f, 21.066f, 2.934f)
                curveTo(20.171f, 2.039f, 19.031f, 1.634f, 17.586f, 1.44f)
                curveTo(16.175f, 1.25f, 14.366f, 1.25f, 12.057f, 1.25f)
                horizontalLineTo(11.943f)
                close()
                moveTo(3.995f, 3.995f)
                curveTo(4.564f, 3.425f, 5.335f, 3.098f, 6.614f, 2.926f)
                curveTo(7.914f, 2.752f, 9.622f, 2.75f, 12f, 2.75f)
                curveTo(14.378f, 2.75f, 16.086f, 2.752f, 17.386f, 2.926f)
                curveTo(18.665f, 3.098f, 19.435f, 3.425f, 20.005f, 3.995f)
                curveTo(20.575f, 4.564f, 20.902f, 5.335f, 21.074f, 6.614f)
                curveTo(21.248f, 7.914f, 21.25f, 9.622f, 21.25f, 12f)
                curveTo(21.25f, 14.378f, 21.248f, 16.086f, 21.074f, 17.386f)
                curveTo(20.902f, 18.665f, 20.575f, 19.435f, 20.005f, 20.005f)
                curveTo(19.435f, 20.575f, 18.665f, 20.902f, 17.386f, 21.074f)
                curveTo(16.086f, 21.248f, 14.378f, 21.25f, 12f, 21.25f)
                curveTo(9.622f, 21.25f, 7.914f, 21.248f, 6.614f, 21.074f)
                curveTo(5.335f, 20.902f, 4.564f, 20.575f, 3.995f, 20.005f)
                curveTo(3.425f, 19.435f, 3.098f, 18.665f, 2.926f, 17.386f)
                curveTo(2.752f, 16.086f, 2.75f, 14.378f, 2.75f, 12f)
                curveTo(2.75f, 9.622f, 2.752f, 7.914f, 2.926f, 6.614f)
                curveTo(3.098f, 5.335f, 3.425f, 4.564f, 3.995f, 3.995f)
                close()
            }
        }.build()

        return _Reset!!
    }

@Suppress("ObjectPropertyName")
private var _Reset: ImageVector? = null
