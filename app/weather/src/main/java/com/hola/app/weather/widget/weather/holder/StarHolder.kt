package com.hola.app.weather.widget.weather.holder

import android.graphics.drawable.GradientDrawable
import com.hola.app.weather.widget.weather.CalculateUtils
import com.hola.app.weather.widget.weather.drawer.BaseDrawer
import kotlin.math.roundToInt

class StarHolder(
    private var x: Float,
    private var y: Float,
    private var w: Float,
    private var h: Float, // [0,1]
    private val maxAlpha: Float
) {
    private var alphaIsGrowing = true
    private var curAlpha: Float = CalculateUtils.getRandom(0f, maxAlpha)// [0,1]

    fun updateRandom(drawable: GradientDrawable, alpha: Float) {
        // curAlpha += getRandom(-0.01f, 0.01f);
        // curAlpha = Math.max(0f, Math.min(maxAlpha, curAlpha));
        val delta = CalculateUtils.getRandom(0.003f * maxAlpha, 0.012f * maxAlpha)
        if (alphaIsGrowing) {
            curAlpha += delta
            if (curAlpha > maxAlpha) {
                curAlpha = maxAlpha
                alphaIsGrowing = false
            }
        } else {
            curAlpha -= delta
            if (curAlpha < 0) {
                curAlpha = 0f
                alphaIsGrowing = true
            }
        }
        val left = (x - w / 2f).roundToInt()
        val right = (x + w / 2f).roundToInt()
        val top = (y - h / 2f).roundToInt()
        val bottom = (y + h / 2f).roundToInt()
        drawable.setBounds(left, top, right, bottom)
        drawable.gradientRadius = w / 2.2f
        drawable.alpha = (255 * curAlpha * alpha).toInt()
    }
}