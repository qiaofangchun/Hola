package com.hola.app.weather.widget.weather.drawer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import com.hola.app.weather.widget.weather.holder.CircleHolder
import java.util.*

class CloudyDrawer(context: Context, isNight: Boolean) : BaseDrawer(context, isNight) {
    // final ArrayList<CloudHolder> holders = new ArrayList<CloudHolder>();
    private val holders = ArrayList<CircleHolder>()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun setSize(width: Int, height: Int) {
        super.setSize(width, height)
        if (holders.size == 0) {
            holders.add(
                CircleHolder(
                    0.20f * width, -0.30f * width, 0.06f * width, 0.022f * width, 0.56f * width,
                    0.0015f, if (isNight) 0x183c6b8c else 0x28ffffff
                )
            )
            holders.add(
                CircleHolder(
                    0.58f * width, -0.35f * width, -0.15f * width, 0.032f * width, 0.6f * width,
                    0.00125f, if (isNight) 0x223c6b8c else 0x33ffffff
                )
            )
            holders.add(
                CircleHolder(
                    0.9f * width, -0.19f * width, 0.08f * width, -0.015f * width, 0.44f * width,
                    0.0025f, if (isNight) 0x153c6b8c else 0x15ffffff
                )
            )
        }
    }

    override fun drawWeather(canvas: Canvas, alpha: Float): Boolean {
        for (holder in holders) {
            holder.updateAndDraw(canvas, paint, alpha)
        }
        return true
    }

    override fun getSkyBackgroundGradient(): IntArray {
        return if (isNight) CLOUDY_N else CLOUDY_D
    }
}