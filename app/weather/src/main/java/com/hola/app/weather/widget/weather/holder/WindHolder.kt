package com.hola.app.weather.widget.weather.holder

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.hola.app.weather.widget.weather.CalculateUtils

class WindHolder(
    private val cx: Float,
    private val cy: Float,
    private val radiusWidth: Float,
    private val radiusHeight: Float,
    private val strokeWidth: Float,
    private val fromDegree: Float,
    private val endDegree: Float,
    private val sizeDegree: Float,
    private val color: Int
) {
    private var curDegree: Float = CalculateUtils.getRandom(fromDegree, endDegree)
    private val stepDegree: Float = CalculateUtils.getRandom(0.5f, 0.9f)
    private val rectF = RectF()

    fun updateAndDraw(canvas: Canvas, paint: Paint, alpha: Float) {
        val percent = alpha * (Color.alpha(color) / 255f)
        paint.color = CalculateUtils.convertAlphaColor(percent, color)
        paint.strokeWidth = strokeWidth
        curDegree += stepDegree * CalculateUtils.getRandom(0.8f, 1.2f)
        if (curDegree > endDegree - sizeDegree) {
            curDegree = fromDegree - sizeDegree
        }
        val startAngle = curDegree
        val sweepAngle = sizeDegree
        rectF.left = cx - radiusWidth
        rectF.top = cy - radiusHeight
        rectF.right = cx + radiusWidth
        rectF.bottom = cy + radiusHeight
        canvas.drawArc(rectF, startAngle, sweepAngle, false, paint)
    }
}