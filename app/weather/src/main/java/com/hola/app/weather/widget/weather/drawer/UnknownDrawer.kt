package com.hola.app.weather.widget.weather.drawer

import android.content.Context
import android.graphics.Canvas

class UnknownDrawer(context: Context, isNight: Boolean) : BaseDrawer(context, isNight) {
    override fun drawWeather(canvas: Canvas, alpha: Float): Boolean = true
}