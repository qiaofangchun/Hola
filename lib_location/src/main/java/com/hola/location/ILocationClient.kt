package com.hola.location

import com.hola.location.annotation.LocationMode

interface ILocationClient {
    fun getPermissions(): List<String>

    fun timeOut(timeOut: Long)

    fun locationMode(@LocationMode mode: Int)

    fun startLocation()

    fun isStarted(): Boolean

    fun stopLocation()

    fun needAddress(needAddress: Boolean)

    fun useCache(isUse: Boolean)

    fun setLocationListener(listener: LocationListener)

    fun release()
}