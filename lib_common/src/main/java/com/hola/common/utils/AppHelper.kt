package com.hola.common.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Resources

object AppHelper {
    @Volatile
    private var impl: Application? = null

    val context
        get() = impl ?: throw IllegalStateException("AppHelper should be initialized before get.")

    val resources: Resources get() = context.resources
    val packageName: String get() = context.packageName
    val contentResolver: ContentResolver get() = context.contentResolver
    val packageManager: PackageManager get() = context.packageManager
    val screenWidth: Int get() = resources.displayMetrics.widthPixels
    val screenHeight: Int get() = resources.displayMetrics.heightPixels

    fun init(application: Application) {
        impl ?: synchronized(AppHelper::class.java) {
            impl ?: let {
                impl = application
            }
        }
    }

    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    fun getStatusBarHeight(): Int {
        val id = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (id > 0) {
            resources.getDimensionPixelSize(id)
        } else {
            0
        }
    }

    fun getSystemService(name: String): Any? = context.getSystemService(name)

    fun getSharedPreferences(name: String, mode: Int = Context.MODE_PRIVATE): SharedPreferences =
        context.getSharedPreferences(name, mode)

    fun registerActivityLifecycleCallbacks(callback: Application.ActivityLifecycleCallbacks) {
        context.registerActivityLifecycleCallbacks(callback)
    }
}