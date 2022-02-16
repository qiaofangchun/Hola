package com.hola.app.weather.widget.weather.holder

import android.graphics.drawable.GradientDrawable
import com.hola.app.weather.widget.weather.CalculateUtils
import com.hola.app.weather.widget.weather.drawer.BaseDrawer
import kotlin.math.roundToInt

class HazeHolder(
    private var x: Float,
    private var y: Float,
    private var w: Float,
    private var h: Float
) {
    fun updateRandom(
        drawable: GradientDrawable,
        minDX: Float,
        maxDX: Float,
        minDY: Float,
        maxDY: Float,
        minX: Float,
        minY: Float,
        maxX: Float,
        maxY: Float,
        alpha: Float
    ) {
        //alpha 还没用
        require(!(maxDX < minDX || maxDY < minDY)) { "max should bigger than min!!!!" }
        x += CalculateUtils.getRandom(minDX, maxDX) * w
        y += CalculateUtils.getRandom(minDY, maxDY) * h
        //			this.x = Math.min(maxX, Math.max(this.x, minX));
//			this.y = Math.min(maxY, Math.max(this.y, minY));
        if (x > maxX) {
            x = minX
        } else if (x < minX) {
            x = maxX
        }
        if (y > maxY) {
            y = minY
        } else if (y < minY) {
            y = maxY
        }
        val left = (x - w / 2f).roundToInt()
        val right = (x + w / 2f).roundToInt()
        val top = (y - h / 2f).roundToInt()
        val bottom = (y + h / 2f).roundToInt()
        drawable.alpha = (255f * alpha).toInt()
        drawable.setBounds(left, top, right, bottom)
        drawable.gradientRadius = w / 2.2f
    }
}