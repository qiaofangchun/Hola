package com.hola.app.weather.location

import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.hola.app.weather.location.exception.LocFailureException
import com.hola.app.weather.location.exception.LocNotDeviceException
import com.hola.app.weather.location.exception.LocNotPermissionException
import com.hola.common.utils.AppHelper

class AMapLocationClient : ILocationClient {
    private var listener: LocationListener? = null

    private val amapClient by lazy {
        AMapLocationClient(AppHelper.context).apply {
            //可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
            AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTPS)
            setLocationOption(option)
            setLocationListener {
                if (it == null) {
                    return@setLocationListener
                }
                when (it.errorCode) {
                    AMapLocation.LOCATION_SUCCESS -> listener?.onSuccess(
                        Location(
                            lat = it.latitude,
                            lng = it.longitude,
                            province = it.province,
                            city = it.city,
                            district = it.district,
                            street = it.street,
                            address = it.address,
                        )
                    )
                    AMapLocation.ERROR_CODE_FAILURE_LOCATION_PERMISSION -> listener?.onFailure(
                        LocNotPermissionException(it.errorInfo)
                    )
                    AMapLocation.ERROR_CODE_FAILURE_NOWIFIANDAP -> listener?.onFailure(
                        LocNotDeviceException(it.errorInfo)
                    )
                    else -> listener?.onFailure(LocFailureException(it.errorInfo))
                }
            }
        }
    }
    private val option = AMapLocationClientOption().apply {
        //可选，设置是否使用传感器。默认是false
        isSensorEnable = false
        //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        isWifiScan = false
    }

    override fun timeOut(timeOut: Long) {
        option.httpTimeOut = timeOut
    }

    override fun interval(interval: Long) {
        option.interval = interval
    }

    override fun locationMode(mode: Int) {
        option.locationMode = when (mode) {
            LocationMode.MODE_GPS -> AMapLocationClientOption.AMapLocationMode.Device_Sensors
            LocationMode.MODE_NETWORK -> AMapLocationClientOption.AMapLocationMode.Battery_Saving
            else -> AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        }
    }

    override fun startLocation() {
        amapClient.startLocation()
    }

    override fun isStarted(): Boolean = amapClient.isStarted

    override fun stopLocation() = amapClient.stopLocation()

    override fun needAddress(needAddress: Boolean) {
        option.isNeedAddress = needAddress
    }

    override fun onceLocation(isOnce: Boolean) {
        option.isOnceLocation = isOnce
    }

    override fun useCache(isUse: Boolean) {
        option.isLocationCacheEnable = isUse
    }

    override fun setLocationListener(listener: LocationListener) {
        this.listener = listener
    }

    override fun release() {
        amapClient.stopLocation()
        amapClient.onDestroy()
    }
}