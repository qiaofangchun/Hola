package com.hola.app.weather.widget.weather.holder

import android.graphics.drawable.GradientDrawable
import com.hola.app.weather.widget.weather.CalculateUtils
import com.hola.app.weather.widget.weather.drawer.BaseDrawer
import kotlin.math.roundToInt

class SnowHolder(
    var x: Float, // public float y;//y 表示雨滴底部的y坐标,由curTime求得
    val snowSize: Float,
    private val maxY: Float, // [0,1]
    averageSpeed: Float
) {
    private val v: Float = averageSpeed * CalculateUtils.getRandom(0.85f, 1.15f) // 速度
    private var curTime: Float = CalculateUtils.getRandom(0f, (maxY / v))// [0,1]

    fun updateRandom(drawable: GradientDrawable, alpha: Float) {
        curTime += 0.025f
        val curY = curTime * v
        if (curY - snowSize > maxY) {
            curTime = 0f
        }
        val left = (x - snowSize / 2f).roundToInt()
        val right = (x + snowSize / 2f).roundToInt()
        val top = (curY - snowSize).roundToInt()
        val bottom = curY.roundToInt()
        drawable.setBounds(left, top, right, bottom)
        drawable.gradientRadius = snowSize / 2.2f
        drawable.alpha = (255 * alpha).toInt()
    }
}