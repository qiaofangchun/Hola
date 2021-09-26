package com.hola.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.annotation.*

@SuppressLint("StaticFieldLeak")
object AppProxy {
    @Volatile
    private var impl: Context? = null

    val context
        get() = impl ?: throw IllegalStateException("AppProxy should be initialized before get.")

    val resources: Resources get() = context.resources
    val packageName: String get() = context.packageName
    val contentResolver: ContentResolver get() = context.contentResolver
    val packageManager: PackageManager get() = context.packageManager

    fun init(application: Application) {
        impl ?: synchronized(AppProxy::class.java) {
            impl ?: let {
                impl = application
                registerActivityLifecycleCallbacks(application)
            }
        }
    }

    private fun registerActivityLifecycleCallbacks(application: Application) {
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityResumed(activity: Activity?) = Unit
            override fun onActivityPaused(activity: Activity?) = Unit
            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) = Unit
            override fun onActivityStarted(activity: Activity?) = Unit
            override fun onActivityStopped(activity: Activity?) = Unit
            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) = Unit
            override fun onActivityDestroyed(activity: Activity?) = Unit
        })
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
}