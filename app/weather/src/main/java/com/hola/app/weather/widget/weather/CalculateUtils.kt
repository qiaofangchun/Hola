package com.hola.app.weather.widget.weather

import java.util.*
import kotlin.math.abs

object CalculateUtils {

    fun getRandom(min: Float, max: Float): Float {
        require(max >= min) { "max should bigger than min!!!!" }
        return (min + Math.random() * (max - min)).toFloat()
    }

    fun getDownRandFloat(min: Float, max: Float): Float {
        val bigend = (min + max) * max / 2f
        val x: Float = getRandom(min, bigend)
        var sum = 0
        var i = 0
        while (i < max) {
            sum += (max - i).toInt()
            if (sum > x) {
                return i.toFloat()
            }
            i++
        }
        return min
    }

    fun getAnyRandInt(n: Int): Int {
        val max = n + 1
        val bigend = (1 + max) * max / 2
        val rd = Random()
        val x = abs(rd.nextInt() % bigend)
        var sum = 0
        for (i in 0 until max) {
            sum += max - i
            if (sum > x) {
                return i
            }
        }
        return 0
    }

    fun convertAlphaColor(percent: Float, originalColor: Int): Int {
        val newAlpha = (percent * 255).toInt() and 0xFF
        return newAlpha shl 24 or (originalColor and 0xFFFFFF)
    }

    fun fixAlpha(alpha: Float): Float = when {
        alpha > 1f -> 1f
        alpha < 0f -> 0f
        else -> alpha
    }
}