package com.hola.app.weather.widget.weather.drawer

import android.content.Context
import android.graphics.Canvas

class DefaultDrawer(context: Context) : BaseDrawer(context, true) {
    override fun drawWeather(canvas: Canvas, alpha: Float): Boolean = false

    override fun getSkyBackgroundGradient(): IntArray = BLACK
}