package com.hola.app.weather.widget.weather.drawer

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.GradientDrawable
import com.hola.app.weather.widget.weather.CalculateUtils
import com.hola.app.weather.widget.weather.drawable.RainDrawable
import com.hola.app.weather.widget.weather.holder.RainHolder
import com.hola.app.weather.widget.weather.holder.SnowHolder
import java.util.ArrayList

/**
 * 雨夹雪
 *
 * @author Mixiaoxiao
 */
class RainAndSnowDrawer(context: Context, isNight: Boolean) : BaseDrawer(context, isNight) {
    private val snowDrawable: GradientDrawable = GradientDrawable(
        GradientDrawable.Orientation.BL_TR,
        intArrayOf(-0x66000001, 0x00ffffff)
    )
    private val rainDrawable: RainDrawable
    private val snowHolders = ArrayList<SnowHolder>()
    private val rainHolders = ArrayList<RainHolder>()

    init {
        snowDrawable.shape = GradientDrawable.OVAL
        snowDrawable.gradientType = GradientDrawable.RADIAL_GRADIENT
        rainDrawable = RainDrawable()
    }

    override fun drawWeather(canvas: Canvas, alpha: Float): Boolean {
        for (holder in snowHolders) {
            holder.updateRandom(snowDrawable, alpha)
            snowDrawable.draw(canvas)
        }
        for (holder in rainHolders) {
            holder.updateRandom(rainDrawable, alpha)
            rainDrawable.draw(canvas)
        }
        return true
    }

    override fun setSize(width: Int, height: Int) {
        super.setSize(width, height)
        if (snowHolders.size == 0) {
            val minSize = dp2px(MIN_SIZE)
            val maxSize = dp2px(MAX_SIZE)
            val speed = dp2px(200f) // 40当作中雪
            for (i in 0 until SNOW_COUNT) {
                val size = CalculateUtils.getRandom(minSize, maxSize)
                val holder = SnowHolder(
                    CalculateUtils.getRandom(0f, width.toFloat()), size,
                    height.toFloat(), speed
                )
                snowHolders.add(holder)
            }
        }
        if (rainHolders.size == 0) {
            val rainWidth = dp2px(2f) //*(1f -  getDownRandFloat(0, 1));
            val minRainHeight = dp2px(8f)
            val maxRainHeight = dp2px(14f)
            val speed = dp2px(360f)
            for (i in 0 until RAIN_COUNT) {
                val x = CalculateUtils.getRandom(0f, width.toFloat())
                val holder = RainHolder(
                    x, rainWidth, minRainHeight, maxRainHeight,
                    height.toFloat(), speed
                )
                rainHolders.add(holder)
            }
        }
    }

    override fun getSkyBackgroundGradient(): IntArray {
        return if (isNight) RAIN_N else RAIN_D
    }

    companion object {
        private const val SNOW_COUNT = 15
        private const val RAIN_COUNT = 30
        private const val MIN_SIZE = 6f // dp
        private const val MAX_SIZE = 14f // dp
    }
}