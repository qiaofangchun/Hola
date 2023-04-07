package com.hola.base.application

import android.app.Application
import com.hola.common.utils.AppHelper
import com.hola.common.utils.Logcat

abstract class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppHelper.init(this)
        Logcat.init(getAppName())
    }

    abstract fun getAppName(): String
}