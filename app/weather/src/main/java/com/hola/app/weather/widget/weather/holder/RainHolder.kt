package com.hola.app.weather.widget.weather.holder

import android.graphics.Color
import com.hola.app.weather.widget.weather.CalculateUtils
import com.hola.app.weather.widget.weather.drawer.BaseDrawer
import com.hola.app.weather.widget.weather.drawable.RainDrawable

class RainHolder {
    private val x: Float //雨滴中心的x坐标

    //private var y:Float,//y 表示雨滴底部的y坐标,由curTime求得
    private val rainWidth: Float//雨滴宽度
    private val rainHeight: Float//雨滴长度
    private val maxY: Float //屏幕高度[0,1]
    private var curTime: Float // [0,1]
    private val rainColor: Int
    private val v: Float//速度

    constructor(
        x: Float,
        rainWidth: Float,
        minRainHeight: Float,
        maxRainHeight: Float,
        maxY: Float,
        speed: Float
    ) {
        this.x = x
        this.rainWidth = rainWidth
        this.rainHeight = CalculateUtils.getRandom(minRainHeight, maxRainHeight)
        val alpha = (CalculateUtils.getRandom(0.1f, 0.5f) * 255f).toInt()
        this.rainColor = Color.argb(alpha, 0xff, 0xff, 0xff)
        this.maxY = maxY
        this.v = speed * CalculateUtils.getRandom(0.9f, 1.1f)
        val maxTime = maxY / v
        curTime = CalculateUtils.getRandom(0f, maxTime)
    }

    fun updateRandom(drawable: RainDrawable, alpha: Float) {
        curTime += 0.025f
        val curY = curTime * v
        if (curY - rainHeight > maxY) {
            curTime = 0f
        }
        val colorAlpha = (Color.alpha(rainColor) * alpha).toInt()
        drawable.setColor(Color.argb(colorAlpha, 0xff, 0xff, 0xff))
        drawable.setStrokeWidth(rainWidth)
        drawable.setLocation(x, curY, rainHeight)
    }
}