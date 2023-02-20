package com.hola.app.weather

import com.hola.base.application.BaseApplication
import com.hola.common.utils.AppHelper
import com.hola.common.utils.Logcat

class WeatherApp : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        Logcat.init("Weather")
        AppHelper.init(this)
        /*StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
            .detectDiskReads()
            .detectDiskWrites()
            .detectNetwork()   // or .detectAll() for all detectable problems
            .penaltyLog()
            .build());
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
            .detectLeakedSqlLiteObjects()
            .detectLeakedClosableObjects()
            .penaltyLog()
            .penaltyDeath()
            .build());*/
    }
}