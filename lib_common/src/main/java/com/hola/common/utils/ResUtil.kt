package com.hola.common.utils

import android.graphics.drawable.Drawable

object ResUtil {
    private val resources = AppHelper.resources
    fun getColor(resId: Int): Int = AppHelper.context.getColor(resId)
    fun getDrawable(resId: Int): Drawable? = AppHelper.context.getDrawable(resId)
    fun getString(resId: Int): String = AppHelper.context.getString(resId)
    fun getString(resId: Int, vararg args: Any): String = AppHelper.context.getString(resId, *args)
    fun getStringArray(resId: Int): Array<String> = resources.getStringArray(resId)
    fun getDimension(resId: Int): Float = resources.getDimension(resId)
    fun getDimensionPixelSize(resId: Int): Int = resources.getDimensionPixelSize(resId)
    fun getDimensionPixelOffset(resId: Int): Int = resources.getDimensionPixelOffset(resId)
}