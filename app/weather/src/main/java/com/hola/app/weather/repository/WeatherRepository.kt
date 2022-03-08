package com.hola.app.weather.repository

import android.util.Log
import com.hola.app.weather.location.Location
import com.hola.app.weather.location.LocationListener
import com.hola.app.weather.location.exception.LocFailureException
import com.hola.app.weather.repository.locale.WeatherDb
import com.hola.app.weather.repository.remote.WeatherNet
import com.hola.app.weather.repository.remote.dao.ApiService
import com.hola.app.weather.repository.remote.model.SearchResult
import com.hola.app.weather.utils.LocationHelper
import com.hola.common.utils.AppHelper
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object WeatherRepository {
    private const val TAG = "WeatherRepository"
    private val remoteApi = WeatherNet.api
    //private val localeApi = WeatherDb.getInstance(AppHelper.context)
    private val language = "zh_CN"
    private val tempUnit = ApiService.UNIT_METRIC

    suspend fun searchPlace(place: String): SearchResult = remoteApi.searchPlace(place, language)

    suspend fun getWeatherByLoc(): String {
        getLocation().address?.takeIf { it.isNotBlank() }?.let { address ->
            val place = searchPlace(address)
            place.places.takeIf { it.isNotEmpty() }?.let { places ->
                val location = places[0].location

                return getWeatherByLoc(true, location.lat, location.lng)
            }
        } ?: let {
            Log.d(TAG, "city code parser failure")
            throw LocFailureException()
        }
    }

    suspend fun getWeatherByLoc(
        isLocation: Boolean = false,
        lat: Double,
        lng: Double,
        lang: String = language,
        unit: String = tempUnit
    ): String {
        val weather = remoteApi.getWeatherByLocation(lat, lng, lang, unit)
        return weather.toString()
    }

    private suspend fun getLocation(): Location {
        return suspendCancellableCoroutine { continuation ->
            val listener = object : LocationListener {
                override fun onSuccess(loc: Location) {
                    continuation.resume(loc)
                    LocationHelper.unRegLocationListener(this)
                }

                override fun onFailure(e: Exception) {
                    continuation.resumeWithException(e)
                    LocationHelper.unRegLocationListener(this)
                }
            }
            LocationHelper.regLocationListener(listener)
            continuation.invokeOnCancellation {
                LocationHelper.unRegLocationListener(listener)
            }
            LocationHelper.startLocation()
        }
    }
}