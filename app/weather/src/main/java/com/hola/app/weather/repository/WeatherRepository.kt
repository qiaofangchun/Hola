package com.hola.app.weather.repository

import android.text.TextUtils
import android.util.Log
import com.hola.app.weather.location.Location
import com.hola.app.weather.location.LocationListener
import com.hola.app.weather.location.exception.LocFailureException
import com.hola.app.weather.repository.locale.WeatherDb
import com.hola.app.weather.repository.remote.WeatherNet
import com.hola.app.weather.repository.remote.dao.ApiService
import com.hola.app.weather.utils.LocationHelper
import com.hola.common.utils.AppHelper
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object WeatherRepository {
    private const val TAG = "WeatherRepository"
    private val remoteApi = WeatherNet.api
    private val localeApi = WeatherDb.getInstance(AppHelper.context)
    private val language = Locale.getDefault().displayLanguage
    private val tempUnit = ApiService.UNIT_METRIC

    suspend fun getWeatherByLoc() {
        val address = getLocation().address
        if (TextUtils.isEmpty(address)) {
            Log.d(TAG, "current address is null")
            throw LocFailureException()
        }
        val place = address?.let { searchPlace(it) }
        place?.places?.takeIf { it.isNotEmpty() }?.let {
            getWeatherByAdCode(true, it[0].place_id)
        } ?: let {
            Log.d(TAG, "city code parser failure")
            throw LocFailureException()
        }
    }

    private suspend fun getLocation(): Location {
        return suspendCancellableCoroutine { continuation ->
            val listener = object : LocationListener {
                override fun onSuccess(loc: Location) {
                    continuation.resume(loc)
                }

                override fun onFailure(e: Exception) {
                    continuation.resumeWithException(e)
                }
            }
            LocationHelper.regLocationListener(listener)
            continuation.invokeOnCancellation {
                LocationHelper.unRegLocationListener(listener)
            }
            LocationHelper.startLocation()
        }
    }

    suspend fun searchPlace(place: String) = remoteApi.searchPlace(place, language)

    suspend fun getWeatherByAdCode(adCode: String) = getWeatherByAdCode(false, adCode)

    private suspend fun getWeatherByAdCode(
        isLocation: Boolean,
        adCode: String,
        lang: String = language,
        unit: String = tempUnit,
    ) {
        val weather = remoteApi.getWeatherByAdCode(adCode, lang, unit)
        Log.d("WeatherRepository", weather.toString())
    }

    suspend fun getWeatherByLocation(
        lng: Double, lat: Double,
        lang: String = language,
        unit: String = tempUnit
    ) {

        val weather = remoteApi.getWeatherByLocation(lng, lat, lang, unit)
        Log.d("WeatherRepository", weather.toString())
    }
}