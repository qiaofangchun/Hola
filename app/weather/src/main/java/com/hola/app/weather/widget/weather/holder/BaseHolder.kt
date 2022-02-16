package com.hola.app.weather.widget.weather.holder

class BaseHolder {

    fun fixAlpha(alpha: Float): Float = when {
        alpha > 1f -> 1f
        alpha < 0f -> 0f
        else -> alpha
    }
}