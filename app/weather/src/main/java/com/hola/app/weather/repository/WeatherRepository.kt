package com.hola.app.weather.repository

import android.util.Log
import androidx.room.withTransaction
import com.hola.app.weather.location.Location
import com.hola.app.weather.location.LocationListener
import com.hola.app.weather.location.exception.LocFailureException
import com.hola.app.weather.repository.locale.WeatherDb
import com.hola.app.weather.repository.locale.model.*
import com.hola.app.weather.repository.remote.WeatherNet
import com.hola.app.weather.repository.remote.dao.ApiService
import com.hola.app.weather.repository.remote.model.Place
import com.hola.app.weather.repository.remote.model.Realtime
import com.hola.app.weather.utils.LocationHelper
import com.hola.common.utils.AppHelper
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.math.roundToInt

object WeatherRepository {
    private const val TAG = "WeatherRepository"
    private val remoteApi = WeatherNet.api
    private val localeApi = WeatherDb.getInstance(AppHelper.context)
    private val language = "zh_CN"
    private val tempUnit = ApiService.UNIT_METRIC

    suspend fun getWeather(lat: Double, lng: Double) {

    }

    /**
     * 获取地方列表
     */
    suspend fun getPlaces(): List<PlaceTab> = localeApi.placeDao().queryPlaces()

    /**
     * 插入或更新地方信息
     */
    suspend fun insert(place: PlaceTab) {
        localeApi.withTransaction {
            insertOrUpdatePlace(place)
        }
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
    suspend fun updateWeatherByLoc() {
        getLocation().address?.takeIf { it.isNotBlank() }?.let { address ->
            searchPlace(address).takeIf { it.isNotEmpty() }?.let { it ->
                val result = it[0]
                val loc = result.location
                updateWeatherByLoc(PlaceTab(loc.lat, loc.lng, result.name, isLocation = true))
            }
        } ?: let {
            Log.d(TAG, "city code parser failure")
            throw LocFailureException()
        }
    }

    /**
     * 根据经纬度获取天气信息
     */
    suspend fun updateWeatherByLoc(place: PlaceTab): String {
        val weather = remoteApi.getWeatherByLocation(place.lat, place.lng, language, tempUnit)
        val realtime = weather.result.realtime.toRealTimeTab(place, weather.server_time)
        val alert = weather.result.alert.toAlertTab(place.lat, place.lng)
        val hourly = weather.result.hourly.toHourlyTab(place.lat, place.lng)
        val daily = weather.result.daily.toDailyTab(place.lat, place.lng)
        localeApi.withTransaction {
            insertOrUpdatePlace(place)
            insertOrUpdateRealTime(realtime)
            insertOrUpdateAlerts(place, alert)
            insertOrUpdateHourly(place, hourly)
            insertOrUpdateDaily(place, daily)
            0
        }
        return weather.toString()
    }

    private suspend fun insertOrUpdatePlace(place: PlaceTab) {
        localeApi.placeDao().run {
            queryPlace(place.lat, place.lng)?.let {
                updatePlace(place)
            } ?: insertPlace(place)
        }
    }

    private suspend fun insertOrUpdateRealTime(realTime: RealTimeTab) {
        localeApi.realTimeDao().run {
            queryRealTime(realTime.lat, realTime.lng)?.let {
                updateRealTime(realTime)
            } ?: insertRealTime(realTime)
        }
    }

    private suspend fun insertOrUpdateAlerts(place: PlaceTab, alerts: List<AlertTab>) {
        localeApi.alertDao().run {
            deleteAlert(place.lat, place.lng)
            insertAlert(alerts)
        }
    }

    private suspend fun insertOrUpdateHourly(place: PlaceTab, hourly: List<HourlyTab>) {
        localeApi.hourlyTimeDao().run {
            deleteHourly(place.lat, place.lng)
            insertHourly(hourly)
        }
    }

    private suspend fun insertOrUpdateDaily(place: PlaceTab, daily: List<DailyTab>) {
        localeApi.dailyDao().run {
            deleteDaily(place.lat, place.lng)
            insertDaily(daily)
        }
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