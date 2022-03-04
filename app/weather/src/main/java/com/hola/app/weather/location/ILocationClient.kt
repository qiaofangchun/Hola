package com.hola.app.weather.location

interface ILocationClient {

    fun timeOut(timeOut: Long)

    fun interval(interval: Long)

    fun locationMode(@LocationMode mode: Int)

    fun startLocation()

    fun isStarted(): Boolean

    fun stopLocation()

    fun needAddress(needAddress: Boolean)

    fun onceLocation(isOnce: Boolean)

    fun useCache(isUse: Boolean)

    fun setLocationListener(listener: LocationListener)

    fun release()
}