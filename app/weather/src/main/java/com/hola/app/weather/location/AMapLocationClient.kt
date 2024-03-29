package com.hola.app.weather.location

import android.Manifest.permission
import android.content.Context
import android.os.Build
import android.os.Looper
import android.util.Log
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.hola.common.utils.Logcat
import com.hola.common.utils.ThreadPoolUtils
import com.hola.location.BaseLocationClient
import com.hola.location.Location
import com.hola.location.LocationListener
import com.hola.location.annotation.LocationCode
import com.hola.location.annotation.LocationMode

class AMapLocationClient(context: Context) : BaseLocationClient(context) {
    companion object {
        private const val TAG = "AMapLocationClient"
        private const val LOC_TIME_OUT = 5000L
        private const val LOC_INTERVAL = 2000L
    }

    private var isStarted = false
    private var listener: LocationListener? = null
    private val option by lazy {
        AMapLocationClientOption().apply {
            //可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
            AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTPS)
            //可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
            httpTimeOut = LOC_TIME_OUT
            //可选，设置定位间隔。默认为2秒
            interval = LOC_INTERVAL
            //可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
            locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            //可选，设置是否gps优先，只在高精度模式下有效。默认关闭
            isGpsFirst = false
            //可选，设置是否返回逆地理地址信息。默认是true
            isNeedAddress = false
            //可选，设置是否单次定位。默认是false
            isOnceLocation = true
            //可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
            isOnceLocationLatest = false
            //可选，设置是否使用传感器。默认是false
            isSensorEnable = false
            //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
            isWifiScan = false
            //可选，设置是否使用缓存定位，默认为true
            isLocationCacheEnable = false
        }
    }
    private val amapClient by lazy {
        AMapLocationClient(ThreadPoolUtils.handlerThread.looper, context).apply {
            setLocationListener {
                stopLocation()
                val location = it?.let {
                    when (it.errorCode) {
                        AMapLocation.LOCATION_SUCCESS -> Location(
                            lat = it.latitude,
                            lng = it.longitude,
                            province = it.province,
                            city = it.city,
                            district = it.district,
                            street = it.street,
                            address = it.address,
                            errorCode = LocationCode.SUCCESS
                        )
                        AMapLocation.ERROR_CODE_FAILURE_LOCATION_PERMISSION -> Location(
                            LocationCode.NO_PERMISSION,
                            it.errorInfo
                        )
                        AMapLocation.ERROR_CODE_FAILURE_NOWIFIANDAP -> Location(
                            errorCode = LocationCode.NOT_FOUND_DEVICE,
                            message = it.errorInfo
                        )
                        else -> Location(
                            errorCode = LocationCode.FAILURE,
                            message = it.errorInfo
                        )
                    }
                } ?: Location(
                    errorCode = LocationCode.FAILURE,
                    message = it.errorInfo
                )
                listener?.onCallback(this@AMapLocationClient, location)
            }
        }
    }

    init {
        AMapLocationClient.updatePrivacyShow(context, true, true)
        AMapLocationClient.updatePrivacyAgree(context, true)
    }

    override fun addPermissions(permissions: List<String>) {
        permissions.plus(permission.ACCESS_COARSE_LOCATION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissions.plus(permission.ACCESS_BACKGROUND_LOCATION)
        }
    }

    override fun timeOut(timeOut: Long) {
        option.httpTimeOut = timeOut
    }

    override fun locationMode(mode: Int) {
        option.locationMode = when (mode) {
            LocationMode.MODE_GPS -> AMapLocationClientOption.AMapLocationMode.Device_Sensors
            LocationMode.MODE_NETWORK -> AMapLocationClientOption.AMapLocationMode.Battery_Saving
            else -> AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        }
    }

    override fun startLocation() {
        Logcat.d(TAG, "startLocation current mode is ${option.locationMode}")
        amapClient.setLocationOption(option)
        amapClient.startLocation()
        isStarted = true
    }

    override fun isStarted(): Boolean = isStarted

    override fun stopLocation() {
        isStarted = false
        amapClient.stopLocation()
    }

    override fun needAddress(needAddress: Boolean) {
        option.isNeedAddress = needAddress
    }

    override fun useCache(isUse: Boolean) {
        option.isLocationCacheEnable = isUse
    }

    override fun setLocationListener(listener: LocationListener) {
        this.listener = listener
    }

    override fun release() {
        stopLocation()
        amapClient.onDestroy()
    }
}