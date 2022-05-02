package com.hola.location

import android.content.Context
import com.hola.location.annotation.LocationMode

interface ILocationClient {
    val context: Context

    fun getPermissions(): Array<String>

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