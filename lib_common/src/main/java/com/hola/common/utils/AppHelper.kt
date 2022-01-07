package com.hola.common.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentResolver
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.*

@SuppressLint("StaticFieldLeak")
object AppHelper {
    @Volatile
    private var impl: Application? = null

    val context
        get() = impl ?: throw IllegalStateException("AppProxy should be initialized before get.")

    val resources: Resources get() = context.resources
    val packageName: String get() = context.packageName
    val contentResolver: ContentResolver get() = context.contentResolver
    val packageManager: PackageManager get() = context.packageManager

    fun init(application: Application) {
        impl ?: synchronized(AppHelper::class.java) {
            impl ?: let {
                impl = application
            }
        }
    }

    fun getString(@StringRes resId: Int): String = context.getString(resId)
    fun getString(@StringRes resId: Int, vararg args: Any): String = context.getString(resId, *args)
    fun getStringArray(@ArrayRes resId: Int): Array<String> = resources.getStringArray(resId)
    fun getSystemService(name: String): Any? = context.getSystemService(name)
    fun getSharedPreferences(name: String, mode: Int): SharedPreferences = context.getSharedPreferences(name, mode)
    fun getColor(@ColorRes resId: Int): Int = context.getColor(resId)
    fun getDrawable(@DrawableRes resId: Int): Drawable? = context.getDrawable(resId)
    fun getDimension(@DimenRes resId: Int): Float = resources.getDimension(resId)
    fun getDimensionPixelOffset(@DimenRes resId: Int): Int = resources.getDimensionPixelOffset(resId)
    fun getDimensionPixelSize(@DimenRes resId: Int): Int = resources.getDimensionPixelSize(resId)
    fun registerActivityLifecycleCallbacks(callback: Application.ActivityLifecycleCallbacks) {
        context.registerActivityLifecycleCallbacks(callback)
    }
}