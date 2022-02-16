package com.hola.app.weather.widget.weather.holder

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.hola.app.weather.widget.weather.CalculateUtils
import com.hola.app.weather.widget.weather.drawer.BaseDrawer

class SandHolder(
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
    private var curDegree: Float
    private val stepDegree: Float
    private val rectF = RectF()

    init {
        curDegree = CalculateUtils.getRandom(fromDegree, endDegree)
        stepDegree = CalculateUtils.getRandom(0.4f, 0.8f)
    }

    fun updateAndDraw(canvas: Canvas, paint: Paint, alpha: Float) {
        paint.color = CalculateUtils.convertAlphaColor(
            alpha * (Color.alpha(color) / 255f),
            color
        )
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