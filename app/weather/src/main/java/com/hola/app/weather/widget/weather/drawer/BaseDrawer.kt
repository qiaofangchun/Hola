package com.hola.app.weather.widget.weather.drawer

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.GradientDrawable
import com.hola.app.weather.widget.weather.WeatherType
import kotlin.math.roundToInt

abstract class BaseDrawer(val context: Context, val isNight: Boolean) {
    protected var width = 0
    protected var height = 0
    private val desity = context.resources.displayMetrics.density
    private var skyDrawable = GradientDrawable().apply {
        colors = getSkyBackgroundGradient()
        orientation = GradientDrawable.Orientation.TOP_BOTTOM
        setBounds(0, 0, width, height)
    }

    open fun setSize(width: Int, height: Int) {
        if (this.width != width && this.height != height) {
            this.width = width
            this.height = height
            skyDrawable.setBounds(0, 0, width, height)
        }
    }

    open fun draw(canvas: Canvas, alpha: Float): Boolean {
        drawSkyBackground(canvas, alpha)
        return drawWeather(canvas, alpha)
    }

    // needDrawNextFrame
    protected open fun getSkyBackgroundGradient(): IntArray? {
        return if (isNight) CLEAR_N else CLEAR_D
    }

    protected open fun drawSkyBackground(canvas: Canvas, alpha: Float) {
        skyDrawable.alpha = (alpha * 255f).roundToInt()
        skyDrawable.draw(canvas)
    }

    abstract fun drawWeather(canvas: Canvas, alpha: Float): Boolean

    protected open fun dp2px(dp: Float): Float {
        return dp * desity
    }

    protected fun reset() {}

    companion object {
        val BLACK = intArrayOf(-0x1000000, -0x1000000)
        val CLEAR_D = intArrayOf(-0xc2663e, -0xb0613b)
        val CLEAR_N = intArrayOf(-0xf4f0db, -0xdad4be)

        val OVERCAST_D = intArrayOf(-0xccbda1, -0x9e8978) //0xff748798, 0xff617688
        val OVERCAST_N = intArrayOf(-0xd9d6df, -0xdcd6c2) //0xff1b2229, 0xff262921

        val RAIN_D = intArrayOf(-0xb07f60, -0xb28b72)
        val RAIN_N = intArrayOf(-0xf2f2eb, -0xdddbd1)

        val FOG_D = intArrayOf(-0x977a69, -0xbbaea5)
        val FOG_N = intArrayOf(-0xd0c3b9, -0xdbcec5)

        val SNOW_D = intArrayOf(-0xb07f60, -0xb28b72) //临时用RAIN_D凑数的
        val SNOW_N = intArrayOf(-0xe1dfd7, -0xded9d0)

        val CLOUDY_D = intArrayOf(-0xb07f60, -0xb28b72) //临时用RAIN_D凑数的
        val CLOUDY_N = intArrayOf(-0xf8ead9, -0xdad4be) // 0xff193353 };//{ 0xff0e1623, 0xff222830 }

        val HAZE_D = intArrayOf(-0x9e9190, -0xb8b9bc) // 0xff999b95, 0xff818e90
        val HAZE_N = intArrayOf(-0xc8c9cc, -0xdadde3)

        val SAND_D = intArrayOf(-0x4a5f9a, -0x2a3f7a) //0xffa59056
        val SAND_N = intArrayOf(-0xced7e0, -0xaeb7c0)

        fun makeDrawerByType(context: Context, @WeatherType type: Int): BaseDrawer {
            return when (type) {
                WeatherType.CLEAR_D -> SunnyDrawer(context)
                WeatherType.CLEAR_N -> StarDrawer(context)
                WeatherType.RAIN_D -> RainDrawer(context, false)
                WeatherType.RAIN_N -> RainDrawer(context, true)
                WeatherType.SNOW_D -> SnowDrawer(context, false)
                WeatherType.SNOW_N -> SnowDrawer(context, true)
                WeatherType.CLOUDY_D -> CloudyDrawer(context, false)
                WeatherType.CLOUDY_N -> CloudyDrawer(context, true)
                WeatherType.OVERCAST_D -> OvercastDrawer(context, false)
                WeatherType.OVERCAST_N -> OvercastDrawer(context, true)
                WeatherType.FOG_D -> FogDrawer(context, false)
                WeatherType.FOG_N -> FogDrawer(context, true)
                WeatherType.HAZE_D -> HazeDrawer(context, false)
                WeatherType.HAZE_N -> HazeDrawer(context, true)
                WeatherType.SAND_D -> SandDrawer(context, false)
                WeatherType.SAND_N -> SandDrawer(context, true)
                WeatherType.WIND_D -> WindDrawer(context, false)
                WeatherType.WIND_N -> WindDrawer(context, true)
                WeatherType.RAIN_SNOW_D -> RainAndSnowDrawer(context, false)
                WeatherType.RAIN_SNOW_N -> RainAndSnowDrawer(context, true)
                WeatherType.UNKNOWN_D -> UnknownDrawer(context, false)
                WeatherType.UNKNOWN_N -> UnknownDrawer(context, true)
                WeatherType.DEFAULT -> DefaultDrawer(context)
                else -> DefaultDrawer(context)
            }
        }
    }
}