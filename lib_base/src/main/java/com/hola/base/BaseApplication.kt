package com.hola.base

import android.app.Application
import com.hola.utils.AppProxy

abstract class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppProxy.init(this)
    }
}