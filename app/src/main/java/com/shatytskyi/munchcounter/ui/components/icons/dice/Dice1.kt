package com.shatytskyi.munchcounter.ui.components.icons.dice

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.shatytskyi.munchcounter.ui.components.icons.MunchkinIcons

val MunchkinIcons.Dice.Dice1: ImageVector
    get() {
        if (_Dice1 != null) {
            return _Dice1!!
        }
        _Dice1 = ImageVector.Builder(
            name = "Dice1",
            defaultWidth = 800.dp,
            defaultHeight = 800.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12f, 12.76f)
                curveTo(11.801f, 12.76f, 11.611f, 12.681f, 11.47f, 12.54f)
                curveTo(11.331f, 12.398f, 11.252f, 12.208f, 11.25f, 12.01f)
                curveTo(11.25f, 11.811f, 11.329f, 11.62f, 11.47f, 11.48f)
                curveTo(11.613f, 11.344f, 11.803f, 11.269f, 12f, 11.269f)
                curveTo(12.197f, 11.269f, 12.387f, 11.344f, 12.53f, 11.48f)
                curveTo(12.671f, 11.62f, 12.75f, 11.811f, 12.75f, 12.01f)
                curveTo(12.75f, 12.209f, 12.671f, 12.4f, 12.53f, 12.54f)
                curveTo(12.39f, 12.681f, 12.199f, 12.76f, 12f, 12.76f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(12f, 22.86f)
                curveTo(11.507f, 22.861f, 11.02f, 22.765f, 10.565f, 22.576f)
                curveTo(10.11f, 22.387f, 9.697f, 22.11f, 9.35f, 21.76f)
                lineTo(2.25f, 14.66f)
                curveTo(1.901f, 14.313f, 1.624f, 13.899f, 1.435f, 13.445f)
                curveTo(1.246f, 12.99f, 1.149f, 12.502f, 1.149f, 12.01f)
                curveTo(1.149f, 11.518f, 1.246f, 11.03f, 1.435f, 10.575f)
                curveTo(1.624f, 10.12f, 1.901f, 9.707f, 2.25f, 9.36f)
                lineTo(9.35f, 2.26f)
                curveTo(10.063f, 1.577f, 11.012f, 1.195f, 12f, 1.195f)
                curveTo(12.988f, 1.195f, 13.937f, 1.577f, 14.65f, 2.26f)
                lineTo(21.75f, 9.36f)
                curveTo(22.099f, 9.707f, 22.376f, 10.12f, 22.565f, 10.575f)
                curveTo(22.754f, 11.03f, 22.851f, 11.518f, 22.851f, 12.01f)
                curveTo(22.851f, 12.502f, 22.754f, 12.99f, 22.565f, 13.445f)
                curveTo(22.376f, 13.899f, 22.099f, 14.313f, 21.75f, 14.66f)
                lineTo(14.65f, 21.76f)
                curveTo(14.303f, 22.11f, 13.89f, 22.387f, 13.435f, 22.576f)
                curveTo(12.98f, 22.765f, 12.493f, 22.861f, 12f, 22.86f)
                close()
                moveTo(12f, 2.66f)
                curveTo(11.704f, 2.658f, 11.411f, 2.715f, 11.138f, 2.829f)
                curveTo(10.865f, 2.942f, 10.617f, 3.109f, 10.41f, 3.32f)
                lineTo(3.31f, 10.42f)
                curveTo(2.889f, 10.842f, 2.652f, 11.414f, 2.652f, 12.01f)
                curveTo(2.652f, 12.606f, 2.889f, 13.178f, 3.31f, 13.6f)
                lineTo(10.41f, 20.7f)
                curveTo(10.838f, 21.11f, 11.408f, 21.338f, 12f, 21.338f)
                curveTo(12.592f, 21.338f, 13.162f, 21.11f, 13.59f, 20.7f)
                lineTo(20.69f, 13.6f)
                curveTo(21.111f, 13.178f, 21.348f, 12.606f, 21.348f, 12.01f)
                curveTo(21.348f, 11.414f, 21.111f, 10.842f, 20.69f, 10.42f)
                lineTo(13.59f, 3.32f)
                curveTo(13.383f, 3.109f, 13.135f, 2.942f, 12.862f, 2.829f)
                curveTo(12.589f, 2.715f, 12.296f, 2.658f, 12f, 2.66f)
                close()
            }
        }.build()

        return _Dice1!!
    }

@Suppress("ObjectPropertyName")
private var _Dice1: ImageVector? = null
