package com.hola.app.weather.widget.weather.drawer

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.GradientDrawable
import android.util.Log
import com.hola.app.weather.widget.weather.CalculateUtils
import com.hola.app.weather.widget.weather.holder.StarHolder
import java.util.*
import kotlin.math.sqrt

/**
 * 晴天的晚上 （星空）
 *
 * @author Mixiaoxiao
 */
class StarDrawer(context: Context) : BaseDrawer(context, true) {
    companion object {
        private const val STAR_COUNT = 80
        private const val STAR_MIN_SIZE = 2f // dp
        private const val STAR_MAX_SIZE = 6f // dp
        private val drawableColors = intArrayOf(-0x1, 0x00ffffff)
    }

    private val holders = ArrayList<StarHolder>()
    private val drawable = GradientDrawable(GradientDrawable.Orientation.BL_TR, drawableColors)

    override fun drawWeather(canvas: Canvas, alpha: Float): Boolean {
        for (holder in holders) {
            holder.updateRandom(drawable, alpha)
            // drawable.setBounds(0, 0, 360, 360);
            // drawable.setGradientRadius(360/2.2f);//测试出来2.2比较逼真
            try {
                drawable.draw(canvas)
            } catch (e: Exception) {
                // TODO Auto-generated catch block
                e.printStackTrace()
                Log.e("FUCK", "drawable.draw(canvas)->" + drawable.bounds.toShortString())
            }
        }
        return true
    }

    override fun setSize(width: Int, height: Int) {
        super.setSize(width, height)
        if (holders.size == 0) {
            val starMinSize = dp2px(STAR_MIN_SIZE)
            val starMaxSize = dp2px(STAR_MAX_SIZE)
            for (i in 0 until STAR_COUNT) {
                val starSize = CalculateUtils.getRandom(starMinSize, starMaxSize)
                val y = CalculateUtils.getDownRandFloat(0f, height.toFloat())
                // 20%的上半部分屏幕最高alpha为1，其余的越靠下最高alpha越小
                val maxAlpha = 0.2f + 0.8f * (1f - y / height)
                val holder =
                    StarHolder(CalculateUtils.getRandom(0f, width.toFloat()), y, starSize, starSize, maxAlpha)
                holders.add(holder)
            }
            // holders.add(new StarHolder(360, 360, 200, 200));
        }
    }

    override fun getSkyBackgroundGradient(): IntArray {
        return CLEAR_N
    }

    init {
        drawable.shape = GradientDrawable.OVAL
        drawable.gradientType = GradientDrawable.RADIAL_GRADIENT
        drawable.gradientRadius = (sqrt(2.0) * 60).toFloat()
    }
}