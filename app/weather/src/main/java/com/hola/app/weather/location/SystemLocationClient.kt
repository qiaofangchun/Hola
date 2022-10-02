package com.hola.app.weather.location

import android.Manifest.permission
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Geocoder
import android.location.LocationManager
import android.os.*
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat
import com.hola.common.utils.Logcat
import com.hola.common.utils.ThreadPoolUtils
import com.hola.location.BaseLocationClient
import com.hola.location.ILocationClient
import com.hola.location.Location
import com.hola.location.LocationListener
import com.hola.location.annotation.LocationCode
import com.hola.location.annotation.LocationMode
import java.util.*

@SuppressLint("MissingPermission")
class SystemLocationClient(context: Context) : BaseLocationClient(context) {
    companion object {
        private const val TAG = "SystemLocationClient"
        private const val LOC_TIME_OUT = 5000L
        private const val LOC_INTERVAL = 2000L
        private const val LOC_MIN_DISTANCE = 1000F
        private const val MSG_WHAT_TIME_OUT = -1
        private const val MSG_WHAT_START_LOCATION = 0
    }

    private val handler = Handler(ThreadPoolUtils.handlerThread.looper) {
        when (it.what) {
            MSG_WHAT_TIME_OUT -> {
                stopLocation()
                listener?.onCallback(
                    this@SystemLocationClient,
                    Location(errorCode = LocationCode.FAILURE, message = "Location Time out!")
                )
            }
            MSG_WHAT_START_LOCATION -> realStartLocation()
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
    private var isUseCache = false
    private var listener: LocationListener? = null
    private val systemLocationListener = android.location.LocationListener {
        stopLocation()
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

    override fun addPermissions(permissions: List<String>) {
        permissions.plus(permission.ACCESS_COARSE_LOCATION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissions.plus(permission.ACCESS_BACKGROUND_LOCATION)
        }
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

    override fun startLocation() {
        Logcat.d(TAG, "startLocation current mode is $mode")
        if (!hasProvider(mode)) {
            listener?.onCallback(
                this,
                Location(LocationCode.NOT_FOUND_DEVICE, "Not found GPS devices!")
            )
        } else if (!hasPermissions()) {
            listener?.onCallback(this, Location(LocationCode.NO_PERMISSION, "No Permission!"))
        } else {
            handler.sendEmptyMessage(MSG_WHAT_START_LOCATION)
            handler.sendMessageDelayed(handler.obtainMessage(MSG_WHAT_TIME_OUT), timeOut)
        }
    }

    private fun realStartLocation() {
        isStarted = true
        locationManager.requestLocationUpdates(
            mode,
            interval,
            LOC_MIN_DISTANCE,
            systemLocationListener,
        )
    }

    override fun isStarted(): Boolean = isStarted

    override fun stopLocation() {
        isStarted = false
        handler.removeCallbacksAndMessages(null)
        locationManager.removeUpdates(systemLocationListener)
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
        stopLocation()
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

    private fun getAddress(latitude: Double, longitude: Double): Location {
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