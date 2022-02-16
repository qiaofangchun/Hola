package com.hola.app.weather.widget.weather.drawer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import com.hola.app.weather.widget.weather.CalculateUtils
import com.hola.app.weather.widget.weather.holder.WindHolder
import java.util.*

class WindDrawer(context: Context, isNight: Boolean) : BaseDrawer(context, isNight) {
    private val count = 30
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val holders = ArrayList<WindHolder>()

    init {
        paint.style = Paint.Style.STROKE
    }

    override fun setSize(width: Int, height: Int) {
        super.setSize(width, height)
        if (holders.size == 0) {
            val cx = -width * 0.3f
            val cy = -width * 1.5f
            for (i in 0 until count) {
                val radiusWidth = CalculateUtils.getRandom(width * 1.3f, width * 3.0f)
                val radiusHeight =
                    radiusWidth * CalculateUtils.getRandom(
                        0.92f,
                        0.96f
                    ) //getRandom(width * 0.02f,  width * 1.6f);
                val strokeWidth = dp2px(CalculateUtils.getDownRandFloat(1f, 2.5f))
                val sizeDegree = CalculateUtils.getDownRandFloat(8f, 15f)
                holders.add(
                    WindHolder(
                        cx, cy, radiusWidth, radiusHeight, strokeWidth, 30f, 99f, sizeDegree,
                        if (isNight) 0x33ffffff else 0x66ffffff
                    )
                )
            }
        }
    }

    override fun drawWeather(canvas: Canvas, alpha: Float): Boolean {
        for (holder in holders) {
            holder.updateAndDraw(canvas, paint, alpha)
        }
        return true
    }

    override fun getSkyBackgroundGradient(): IntArray {
        return if (isNight) RAIN_N else RAIN_D
    }
}