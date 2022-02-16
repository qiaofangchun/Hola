package com.hola.app.weather.widget.weather.holder

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.hola.app.weather.widget.weather.CalculateUtils
import com.hola.app.weather.widget.weather.drawer.BaseDrawer

class CircleHolder(
    private val cx: Float,
    private val cy: Float,
    private val dx: Float,
    private val dy: Float,
    private val radius: Float,
    private val percentSpeed: Float,
    private val color: Int
) {
    private var isGrowing = true
    private var curPercent = 0f

    fun updateAndDraw(canvas: Canvas, paint: Paint, alpha: Float) {
        val randomPercentSpeed = CalculateUtils.getRandom(percentSpeed * 0.7f, percentSpeed * 1.3f)
        if (isGrowing) {
            curPercent += randomPercentSpeed
            if (curPercent > 1f) {
                curPercent = 1f
                isGrowing = false
            }
        } else {
            curPercent -= randomPercentSpeed
            if (curPercent < 0f) {
                curPercent = 0f
                isGrowing = true
            }
        }
        val curCX = cx + dx * curPercent
        val curCY = cy + dy * curPercent
        val curColor = CalculateUtils.convertAlphaColor(alpha * (Color.alpha(color) / 255f), color)
        paint.color = curColor
        canvas.drawCircle(curCX, curCY, radius, paint)
    }
}