package com.hola.app.weather.widget.weather.drawer

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.GradientDrawable
import android.util.Log
import com.hola.app.weather.widget.weather.CalculateUtils
import com.hola.app.weather.widget.weather.holder.HazeHolder
import java.lang.Exception
import java.util.ArrayList

/**
 * 霾
 *
 * @author Mixiaoxiao
 */
class HazeDrawer(context: Context, isNight: Boolean) : BaseDrawer(context, isNight) {
    private val drawable: GradientDrawable
    private val holders = ArrayList<HazeHolder>()
    private val minDX: Float
    private val maxDX: Float
    private val minDY: Float
    private val maxDY: Float
    override fun drawWeather(canvas: Canvas, alpha: Float): Boolean {
        for (holder in holders) {
            holder.updateRandom(
                drawable,
                minDX,
                maxDX,
                minDY,
                maxDY,
                0f,
                0f,
                width.toFloat(),
                height.toFloat(),
                alpha
            )
            //				drawable.setBounds(0, 0, 360, 360);
//				drawable.setGradientRadius(360/2.2f);//测试出来2.2比较逼真
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
            val minSize = dp2px(0.8f)
            val maxSize = dp2px(4.4f)
            for (i in 0..79) {
                val starSize = CalculateUtils.getRandom(minSize, maxSize)
                val holder = HazeHolder(
                    CalculateUtils.getRandom(0f, width.toFloat()),
                    CalculateUtils.getDownRandFloat(0f, height.toFloat()),
                    starSize,
                    starSize
                )
                holders.add(holder)
            }
            //			holders.add(new StarHolder(360, 360, 200, 200));
        }
    }

    override fun getSkyBackgroundGradient(): IntArray {
        return if (isNight) HAZE_N else HAZE_D
    }

    init {
        drawable = GradientDrawable(
            GradientDrawable.Orientation.BL_TR,
            if (isNight) intArrayOf(0x55d4ba3f, 0x22d4ba3f) else intArrayOf(-0x77335999, 0x33cca667)
        ) //d4ba3f
        drawable.shape = GradientDrawable.OVAL
        drawable.gradientType = GradientDrawable.RADIAL_GRADIENT
        //		drawable.setGradientRadius((float)(Math.sqrt(2) * 60));
        minDX = 0.04f
        maxDX = 0.065f //dp2px(1.5f);
        minDY = -0.02f //-dp2px(0.5f);
        maxDY = 0.02f //dp2px(0.5f);
    }
}