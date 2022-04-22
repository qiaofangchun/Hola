package com.hola.base.application

import android.app.Application
import com.hola.datastore.DataStore
import com.hola.common.utils.AppHelper

abstract class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppHelper.init(this)
        com.hola.datastore.DataStore.init(this)
    }
}