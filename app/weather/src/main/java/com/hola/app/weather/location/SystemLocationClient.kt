package com.hola.app.weather.location

import android.Manifest.permission
import android.annotation.SuppressLint
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
import com.hola.location.Location
import com.hola.location.LocationListener
import com.hola.location.annotation.LocationCode
import com.hola.location.annotation.LocationMode
import java.util.*

class SystemLocationClient(override val context: Context) : com.hola.location.ILocationClient {
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
            listener?.onCallback(
                this@SystemLocationClient,
                Location(errorCode = LocationCode.FAILURE, message = "Location Time out!")
            )
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
        val location = if (needAddress) {
            try {
                getAddress(it.latitude, it.longitude)
            } catch (e: Exception) {
                Location(errorCode = LocationCode.FAILURE, message = e.message)
            }
        } else {
            Location(it.latitude, it.longitude, errorCode = LocationCode.SUCCESS)
        }
        listener?.onCallback(this@SystemLocationClient, location)
    }

    override fun getPermissions(): Array<String> {
        var permissions = getCheckPermissions()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissions += permission.ACCESS_BACKGROUND_LOCATION
        }
        return permissions
    }

    private fun getCheckPermissions(): Array<String> = arrayOf(
        permission.ACCESS_COARSE_LOCATION
    )

    override fun timeOut(timeOut: Long) {
        this.timeOut = timeOut
    }

    override fun locationMode(@LocationMode mode: Int) {
        this.mode = when (mode) {
            LocationMode.MODE_GPS -> LocationManager.GPS_PROVIDER
            LocationMode.MODE_NETWORK -> LocationManager.NETWORK_PROVIDER
            else -> getDefaultProvider()
        }
    }

    @SuppressLint("MissingPermission")
    override fun startLocation() {
        Log.d(TAG, "startLocation current mode is $mode")
        if (!hasProvider(mode)) {
            listener?.onCallback(this, Location(LocationCode.NOT_FOUND_DEVICE, "Not Found GPS Devices!"))
        } else if (!hasPermissions()) {
            listener?.onCallback(this, Location(LocationCode.NO_PERMISSION, "No Permission!"))
        } else {
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
    }

    override fun isStarted(): Boolean = isStarted

    override fun stopLocation() {
        isStarted = false
        handler.removeMessages(MSG_WHAT_TIME_OUT)
    }

    override fun needAddress(needAddress: Boolean) {
        this.needAddress = needAddress
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

    private fun hasProvider(provider: String) =
        providers.contains(provider) && LocationManagerCompat.isLocationEnabled(locationManager)

    private fun hasPermissions(): Boolean {
        getCheckPermissions().forEach {
            if (ActivityCompat.checkSelfPermission(context, it) != PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    private fun getDefaultProvider(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            LocationManager.FUSED_PROVIDER
        } else {
            LocationManager.PASSIVE_PROVIDER
        }
    }

    private fun getAddress(latitude: Double, longitude: Double): com.hola.location.Location {
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            return addresses?.takeIf { it.isNotEmpty() }?.let {
                val address = it[0]
                Location(
                    lat = latitude,
                    lng = longitude,
                    province = address.adminArea,
                    city = address.locality,
                    district = address.subLocality,
                    street = address.thoroughfare,
                    address = address.getAddressLine(0),
                    errorCode = LocationCode.SUCCESS
                )
            } ?: Location(LocationCode.FAILURE, "Coordinate Transform Failure!")
        } catch (e: Exception) {
            return Location(LocationCode.NOT_FOUND_DEVICE, e.message)
        }
    }
}