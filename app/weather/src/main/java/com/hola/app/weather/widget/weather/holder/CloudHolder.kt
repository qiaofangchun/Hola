package com.hola.app.weather.widget.weather.holder

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import com.hola.app.weather.widget.weather.CalculateUtils
import com.hola.app.weather.widget.weather.drawer.BaseDrawer
import kotlin.math.abs
import kotlin.math.roundToInt

class CloudHolder(
    private val drawable: Drawable,
    private val percentWidthPerframe: Float,
    private val screenWidth: Float,
    private var drawableWidth: Float,
    private var maxAlpha: Float,
    private var canLoop: Boolean
) {
    private var drawableHeight = 0f
    private var curX = 0f
    private var minX = 0f

    init {
        if (drawableWidth < screenWidth) {
            drawableWidth = screenWidth * 1.1f
        }
        val scale = drawableWidth / drawable.intrinsicWidth
        drawableHeight = drawable.intrinsicHeight * scale
        minX = this.screenWidth - this.drawableWidth
        curX = CalculateUtils.getRandom(minX, 0f)
    }

    fun updateAndDraw(canvas: Canvas, alpha: Float) {
        curX -= percentWidthPerframe * drawableWidth * CalculateUtils.getRandom(0.5f, 1f)
        if (curX < minX) {
            curX = 0f
        }
        var curAlpha = 1f
        if (!canLoop) {
            val percent = abs(curX / minX)
            curAlpha = if (percent > 0.5f) (1 - percent) / 0.5f else percent / 0.5f
            curAlpha = CalculateUtils.fixAlpha(curAlpha) * maxAlpha
        }
        drawable.alpha = (alpha * 255f * curAlpha).roundToInt()
        val left = curX.roundToInt()
        drawable.setBounds(
            left, 0, (left + drawableWidth).roundToInt(),
            drawableHeight.roundToInt()
        )
        drawable.draw(canvas)
    }
}