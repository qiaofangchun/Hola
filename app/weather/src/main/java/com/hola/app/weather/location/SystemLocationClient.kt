package com.hola.app.weather.location

import android.Manifest.permission
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.os.Process
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat
import com.hola.app.weather.location.exception.LocFailureException
import com.hola.app.weather.location.exception.LocNotDeviceException
import com.hola.app.weather.location.exception.LocNotPermissionException
import java.util.*

class SystemLocationClient(override val context: Context) : ILocationClient {
    companion object {
        private const val TAG = "SystemLocationClient"
        private const val T_NAME = "T_SYS_LOC"
        private const val LOC_TIME_OUT = 5000L
        private const val LOC_INTERVAL = 2000L
        private const val LOC_MIN_DISTANCE = 1000F
        private const val MSG_WHAT_TIME_OUT = 1
    }

    private val thread = HandlerThread(T_NAME, Process.THREAD_PRIORITY_FOREGROUND).also {
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

    private var timeOut = LOC_TIME_OUT
    private var interval = LOC_INTERVAL
    private var mode = getDefaultProvider()
    private var isStarted = false
    private var needAddress = false
    private var onceLocation = false
    private var isUseCache = false
    private var listener: LocationListener? = null
    private val systemLocationListener = android.location.LocationListener {
        if (onceLocation) {
            isStarted = false
            stopLocation()
        }
        if (needAddress) {
            try {
                listener?.onSuccess(getAddress(it.latitude, it.longitude))
            } catch (e: Exception) {
                listener?.onFailure(e)
            }
        } else {
            listener?.onSuccess(Location(it.latitude, it.longitude))
        }
    }

    override fun getPermissions(): Array<String> = arrayOf(
        permission.ACCESS_COARSE_LOCATION,
        permission.ACCESS_FINE_LOCATION,
        permission.READ_PHONE_STATE
    )

    override fun timeOut(timeOut: Long) {
        this.timeOut = timeOut
    }

    override fun interval(interval: Long) {
        this.interval = interval
    }

    override fun locationMode(@LocationMode mode: Int) {
        this.mode = when (mode) {
            LocationMode.MODE_GPS -> LocationManager.GPS_PROVIDER
            LocationMode.MODE_NETWORK -> LocationManager.NETWORK_PROVIDER
            else -> getDefaultProvider()
        }
    }

    override fun startLocation() {
        if (!LocationManagerCompat.isLocationEnabled(locationManager)) {
            listener?.onFailure(LocNotDeviceException())
            return
        }
        Log.d(TAG, "current mode is $mode")
        if (!hasProvider(mode)) {
            listener?.onFailure(LocNotDeviceException())
            return
        }
        getPermissions().forEach {
            if (ActivityCompat.checkSelfPermission(context, it) != PERMISSION_GRANTED) {
                listener?.onFailure(LocNotPermissionException())
                return@forEach
            }
        }
        handler.sendMessageDelayed(handler.obtainMessage(MSG_WHAT_TIME_OUT), timeOut)
        locationManager.requestLocationUpdates(
            mode,
            interval,
            LOC_MIN_DISTANCE,
            systemLocationListener,
            thread.looper
        )
        isStarted = true
    }

    override fun isStarted(): Boolean = isStarted

    override fun stopLocation() {
        isStarted = false
        handler.removeMessages(MSG_WHAT_TIME_OUT)
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
        handler.removeCallbacksAndMessages(null)
    }

    private fun hasProvider(provider: String) = providers.contains(provider)

    private fun getDefaultProvider(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            LocationManager.FUSED_PROVIDER
        } else {
            LocationManager.PASSIVE_PROVIDER
        }
    }

    private fun getAddress(latitude: Double, longitude: Double): Location {
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            addresses?.takeIf { it.isNotEmpty() }?.let {
                val address = it[0]
                return Location(
                    lat = latitude,
                    lng = longitude,
                    province = address.adminArea,
                    city = address.locality,
                    district = address.subLocality,
                    street = address.thoroughfare,
                    address = address.getAddressLine(0)
                )
            } ?: throw LocFailureException()
        } catch (e: Exception) {
            throw LocFailureException()
        }
    }
}