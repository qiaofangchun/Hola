package com.hola.app.weather.widget.weather.drawer

import android.content.Context
import android.graphics.Canvas
import com.hola.app.weather.widget.weather.CalculateUtils
import com.hola.app.weather.widget.weather.drawable.RainDrawable
import com.hola.app.weather.widget.weather.holder.RainHolder
import java.util.*

class RainDrawer(context: Context, isNight: Boolean) : BaseDrawer(context, isNight) {
    private val cfg_count = 50
    private val drawable = RainDrawable()
    private val holders = ArrayList<RainHolder>()

    override fun setSize(width: Int, height: Int) {
        super.setSize(width, height)
        if (holders.size == 0) {
            val rainWidth = dp2px(2f) //*(1f -  getDownRandFloat(0, 1));
            val minRainHeight = dp2px(8f)
            val maxRainHeight = dp2px(14f)
            val speed = dp2px(400f)
            for (i in 0 until cfg_count) {
                val x = CalculateUtils.getRandom(0f, width.toFloat())
                holders.add(
                    RainHolder(
                        x,
                        rainWidth,
                        minRainHeight,
                        maxRainHeight,
                        height.toFloat(),
                        speed
                    )
                )
            }
        }
    }

    override fun getSkyBackgroundGradient(): IntArray {
        return if (isNight) RAIN_N else RAIN_D
    }

    override fun drawWeather(canvas: Canvas, alpha: Float): Boolean {
        for (holder in holders) {
            holder.updateRandom(drawable, alpha)
            drawable.draw(canvas)
        }
        return true
    }
}