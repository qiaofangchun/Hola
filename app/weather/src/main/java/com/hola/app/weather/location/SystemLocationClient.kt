package com.hola.app.weather.location

import android.Manifest.permission
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Handler
import android.os.HandlerThread
import android.os.Process
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat
import com.hola.app.weather.location.exception.LocFailureException
import com.hola.app.weather.location.exception.LocNotDeviceException
import com.hola.app.weather.location.exception.LocNotPermissionException

class SystemLocationClient(private val context: Context) : ILocationClient {
    companion object {
        private const val MSG_WHAT_TIME_OUT = 1
    }

    private val thread = HandlerThread("T_SYS_LOC", Process.THREAD_PRIORITY_FOREGROUND).also {
        it.start()
    }
    private val handler = Handler(thread.looper) {
        if (it.what == MSG_WHAT_TIME_OUT) {
            isStarted = false
            listener?.onFailure(LocFailureException())
        }
        return@Handler true
    }

    private val locationManager by lazy { context.getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    private val providers by lazy { locationManager.getProviders(true) }

    private var timeOut = 15000L
    private var interval = 5000L
    private var mode = LocationMode.MODE_NETWORK
    private var isStarted = false
    private var needAddress = false
    private var onceLocation = false
    private var isUseCache = false
    private var listener: LocationListener? = null
    private val systemLocationListener = android.location.LocationListener {
        isStarted = false
    }

    override fun timeOut(timeOut: Long) {
        this.timeOut = timeOut
    }

    override fun interval(interval: Long) {
        this.interval = interval
    }

    override fun locationMode(@LocationMode mode: Int) {
        this.mode = mode
    }

    override fun startLocation() {
        if (!LocationManagerCompat.isLocationEnabled(locationManager)) {
            listener?.onFailure(LocNotDeviceException())
            return
        }
        val provider = when (mode) {
            LocationMode.MODE_GPS -> LocationManager.GPS_PROVIDER
            LocationMode.MODE_NETWORK -> LocationManager.NETWORK_PROVIDER
            else -> LocationManager.PASSIVE_PROVIDER
        }
        if (!hasProvider(provider)) {
            listener?.onFailure(LocNotDeviceException())
            return
        }
        if (ActivityCompat.checkSelfPermission(
                context,
                permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            listener?.onFailure(LocNotPermissionException())
            return
        }
        handler.sendMessageDelayed(handler.obtainMessage(MSG_WHAT_TIME_OUT), timeOut)
        locationManager.requestLocationUpdates(
            provider,
            interval,
            1000F,
            systemLocationListener,
            thread.looper
        )
        isStarted = true
    }

    override fun isStarted(): Boolean = isStarted

    override fun stopLocation() {
        locationManager.removeUpdates(systemLocationListener)
    }

    override fun needAddress(needAddress: Boolean) {
        this.needAddress = needAddress
    }

    override fun onceLocation(isOnce: Boolean) {
        this.onceLocation = isOnce
    }

    override fun useCache(isUse: Boolean) {
        this.isUseCache = isUse
    }

    override fun setLocationListener(listener: LocationListener) {
        this.listener = listener
    }

    override fun release() {
        isStarted = false
        locationManager.removeUpdates(systemLocationListener)
    }

    private fun hasProvider(provider: String) = providers.contains(provider)
}