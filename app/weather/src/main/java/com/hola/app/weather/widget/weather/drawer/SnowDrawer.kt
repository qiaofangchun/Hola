package com.hola.app.weather.widget.weather.drawer

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.GradientDrawable
import com.hola.app.weather.widget.weather.CalculateUtils
import com.hola.app.weather.widget.weather.holder.SnowHolder
import java.util.*

/**
 * 下雪
 */
class SnowDrawer(context: Context, isNight: Boolean) : BaseDrawer(context, isNight) {
    private val holders = ArrayList<SnowHolder>()
    private val drawable: GradientDrawable = GradientDrawable(
        GradientDrawable.Orientation.BL_TR,
        intArrayOf(-0x66000001, 0x00ffffff)
    )

    init {
        drawable.shape = GradientDrawable.OVAL
        drawable.gradientType = GradientDrawable.RADIAL_GRADIENT
    }

    override fun drawWeather(canvas: Canvas, alpha: Float): Boolean {
        for (holder in holders) {
            holder.updateRandom(drawable, alpha)
            drawable.draw(canvas)
        }
        return true
    }

    override fun setSize(width: Int, height: Int) {
        super.setSize(width, height)
        if (holders.size == 0) {
            val minSize = dp2px(MIN_SIZE)
            val maxSize = dp2px(MAX_SIZE)
            val speed = dp2px(80f) // 40当作中雪80
            for (i in 0 until COUNT) {
                val size = CalculateUtils.getRandom(minSize, maxSize)
                val holder = SnowHolder(
                    CalculateUtils.getRandom(0f, width.toFloat()), size,
                    height.toFloat(), speed
                )
                holders.add(holder)
            }
        }
    }

    override fun getSkyBackgroundGradient(): IntArray {
        return if (isNight) SNOW_N else SNOW_D
    }

    companion object {
        private const val COUNT = 30
        private const val MIN_SIZE = 12f // dp
        private const val MAX_SIZE = 30f // dp
    }
}