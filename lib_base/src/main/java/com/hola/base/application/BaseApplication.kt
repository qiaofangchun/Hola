package com.hola.base.application

import android.app.Application
import com.hola.common.datastore.DataStore
import com.hola.common.utils.AppHelper

abstract class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppHelper.init(this)
        DataStore.init(this)
    }
}