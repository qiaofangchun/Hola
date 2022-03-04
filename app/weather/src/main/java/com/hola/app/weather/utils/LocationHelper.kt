package com.hola.app.weather.utils

import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.hola.common.utils.AppHelper
import java.lang.NullPointerException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/*
object LocationHelper {
    private const val LOC_TIME_OUT = 5000L
    private const val LOC_INTERVAL = 2000L

    private val amapClient by lazy {
        val option = AMapLocationClientOption().apply {
            //可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
            httpTimeOut = AMAP_HTTP_TIME_OUT
            //可选，设置定位间隔。默认为2秒
            interval = AMAP_LOC_INTERVAL
            //可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
            locationMode = AMapLocationClientOption.AMapLocationMode.Battery_Saving
            //可选，设置是否gps优先，只在高精度模式下有效。默认关闭
            isGpsFirst = false
            //可选，设置是否返回逆地理地址信息。默认是true
            isNeedAddress = true
            //可选，设置是否单次定位。默认是false
            isOnceLocation = false
            //可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
            isOnceLocationLatest = false
            //可选，设置是否使用传感器。默认是false
            isSensorEnable = false
            //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
            isWifiScan = false
            //可选，设置是否使用缓存定位，默认为true
            isLocationCacheEnable = false
        }
        AMapLocationClient(AppHelper.context).apply {
            //可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
            AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTPS)
            setLocationOption(option)
        }
    }

    val isStarted = amapClient.isStarted

    private val sysClient by lazy {

    }

    suspend fun getLocation(): Array<Double> {
        return suspendCoroutine { continuation ->
            amapClient.startLocation()
            amapClient.setLocationListener { result ->
                result?.takeIf { it.errorCode == AMapLocation.LOCATION_SUCCESS }
                    ?.let {
                        continuation.resume(arrayOf(it.latitude, it.longitude))
                    } ?: continuation.resumeWithException(NullPointerException())
            }
        }
    }
}*/
