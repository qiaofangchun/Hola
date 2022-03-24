package com.hola.app.weather.utils

import android.util.Log
import com.hola.app.weather.location.*
import com.hola.app.weather.location.exception.LocNotPermissionException
import com.hola.common.utils.AppHelper
import java.util.concurrent.CopyOnWriteArrayList

object LocationHelper {
    private const val TAG = "LocationHelper"
    private const val LOC_TIME_OUT = 5000L
    private const val LOC_INTERVAL = 2000L

    private val callback = CopyOnWriteArrayList<LocationListener>()

    private val sysClient by lazy {
        SystemLocationClient(AppHelper.context).apply { configClient(this) }
    }
    private val amapClient by lazy {
        AMapLocationClient(AppHelper.context).apply { configClient(this) }
    }

    fun startLocation() {
        if (amapClient.isStarted()) {
            Log.d(TAG, "amap started!")
            return
        }
        if (sysClient.isStarted()) {
            Log.d(TAG, "system started!")
            return
        }
        amapClient.startLocation()
    }

    fun stopLocation() {
        amapClient.stopLocation()
        sysClient.stopLocation()
    }

    fun getPermissions(): Array<String> {
        val all = sysClient.getPermissions() + amapClient.getPermissions()
        return all.distinct().toTypedArray()
    }

    fun regLocationListener(listener: LocationListener) {
        callback += listener
    }

    fun unRegLocationListener(listener: LocationListener) {
        callback -= listener
    }

    private fun configClient(client: ILocationClient) {
        client.timeOut(LOC_TIME_OUT)
        client.interval(LOC_INTERVAL)
        client.needAddress(true)
        client.onceLocation(true)
        client.locationMode(LocationMode.MODE_NETWORK)
        client.setLocationListener(if (client is AMapLocationClient) {
            object : LocationListener {
                override fun onSuccess(loc: Location) {
                    handSuccess(loc)
                }

                override fun onFailure(e: Exception) {
                    if (e is LocNotPermissionException) {
                        handFailure(e)
                        return
                    }
                    sysClient.startLocation()
                }
            }
        } else {
            object : LocationListener {
                override fun onSuccess(loc: Location) {
                    handSuccess(loc)
                }

                override fun onFailure(e: Exception) {
                    handFailure(e)
                }
            }
        })
    }

    private fun handSuccess(loc: Location) {
        callback.forEach {
            it.onSuccess(loc)
        }
    }

    private fun handFailure(e: Exception) {
        callback.forEach {
            it.onFailure(e)
        }
    }
}
