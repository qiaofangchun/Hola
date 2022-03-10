package com.hola.app.weather.repository

import android.util.Log
import com.hola.app.weather.location.Location
import com.hola.app.weather.location.LocationListener
import com.hola.app.weather.location.exception.LocFailureException
import com.hola.app.weather.repository.locale.WeatherDb
import com.hola.app.weather.repository.locale.model.PlaceTab
import com.hola.app.weather.repository.remote.WeatherNet
import com.hola.app.weather.repository.remote.dao.ApiService
import com.hola.app.weather.repository.remote.model.Place
import com.hola.app.weather.repository.remote.model.SearchResult
import com.hola.app.weather.utils.LocationHelper
import com.hola.common.utils.AppHelper
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object WeatherRepository {
    private const val TAG = "WeatherRepository"
    private val remoteApi = WeatherNet.api
    private val localeApi = WeatherDb.getInstance(AppHelper.context)
    private val language = "zh_CN"
    private val tempUnit = ApiService.UNIT_METRIC

    suspend fun getWeather(lat: Double, lng: Double) {

    }

    suspend fun getPlace(lat: Double, lng: Double): PlaceTab? =
        localeApi.placeDao().queryPlace(lat, lng)

    suspend fun getPlaces(): List<PlaceTab> = localeApi.placeDao().queryPlaces()

    suspend fun insertPlace(lat: Double, lng: Double, name: String, isLocation: Boolean = false) {
        val place = PlaceTab(
            lat = lat,
            lng = lng,
            name = name,
            isLocation = isLocation
        )
        localeApi.placeDao().insertPlace(place)
    }

    /**
     * 搜索城市
     */
    suspend fun searchPlace(place: String): List<Place> {
        return remoteApi.searchPlace(place, language).places
    }

    /**
     * 获取当前位置的天气信息
     */
    suspend fun updateWeatherByLoc(): String {
        getLocation().address?.takeIf { it.isNotBlank() }?.let { address ->
            searchPlace(address).takeIf { it.isNotEmpty() }?.let { it ->
                val result = it[0]
                val loc = result.location
                insertPlace(loc.lat, loc.lng, result.name, true)
                return updateWeatherByLoc(loc.lat, loc.lng)
            }
        } ?: let {
            Log.d(TAG, "city code parser failure")
            throw LocFailureException()
        }
    }

    /**
     * 根据经纬度获取天气信息
     */
    suspend fun updateWeatherByLoc(
        lat: Double,
        lng: Double,
        lang: String = language,
        unit: String = tempUnit
    ): String {
        val weather = remoteApi.getWeatherByLocation(lat, lng, lang, unit)
        return weather.toString()
    }

    /**
     * 获取当前位置
     */
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