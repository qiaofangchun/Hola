package com.hola.common.manager

import android.content.Context
import android.net.wifi.WifiManager
import com.hola.common.utils.AppHelper

object WifiLockManager {
    private const val TAG = "WifiLockManager"
    private const val TIME_OUT = 5000L

    private val mLock by lazy {
        val manager = AppHelper.context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        //设置WifiLock，可以让在后台也可以运行wifi下载加载数据
        manager.createWifiLock(WifiManager.WIFI_MODE_FULL_HIGH_PERF, TAG)
    }

    fun acquire() {
        mLock.takeIf { !it.isHeld }?.acquire()
    }

    fun release() {
        mLock.takeIf { it.isHeld }?.release()
    }
}