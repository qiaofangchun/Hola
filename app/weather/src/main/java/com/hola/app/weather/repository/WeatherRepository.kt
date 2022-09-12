package com.hola.app.weather.repository

import android.util.Log
import androidx.room.withTransaction
import com.hola.app.weather.location.LocationException
import com.hola.app.weather.repository.locale.WeatherDb
import com.hola.app.weather.repository.locale.model.*
import com.hola.app.weather.repository.remote.WeatherNet
import com.hola.app.weather.repository.remote.dao.ApiService
import com.hola.app.weather.repository.remote.handleNetApiResult
import com.hola.common.ext.asFlow
import com.hola.common.utils.AppHelper
import com.hola.location.ILocationClient
import com.hola.location.Location
import com.hola.location.LocationHelper
import com.hola.location.LocationListener
import com.hola.location.annotation.LocationCode
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
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

    /**
     * 获取地方列表
     */
    fun getPlaces(): Flow<List<PlaceTab>> = localeApi.placeDao().queryPlacesFlow()

    /**
     * 删除地方
     */
    suspend fun deletePlace(place: PlaceTab) = localeApi.placeDao().deletePlace(place)

    /**
     * 批量删除地方
     */
    suspend fun deletePlaces(places: List<PlaceTab>) = localeApi.placeDao().deletePlaces(places)

    /**
     * 插入或更新地方信息
     */
    suspend fun insertPlace(place: PlaceTab) {
        localeApi.withTransaction { insertOrUpdatePlace(place) }
    }

    /**
     * 搜索城市
     */
    fun searchPlace(place: String) =
        remoteApi::searchPlace.asFlow(place, language).handleNetApiResult()


    /**
     * 获取当前位置的天气信息
     */
    @OptIn(FlowPreview::class)
    fun updateWeatherByLoc(): Flow<Boolean> {
        return this::getLocation.asFlow().flatMapConcat { it ->
            Log.d(TAG, "location---->$it")
            StringBuilder().append(it.province.safe())
                .append(it.city.safe())
                .append(it.district.safe())
                .toString().takeIf { it.isNotEmpty() }?.let {
                    flowOf("北京")
                }
                ?: throw LocationException(LocationCode.FAILURE, "city code parser failure")
        }.flatMapConcat {
            Log.d(TAG, "flatMapConcat---->$it")
            searchPlace(it)
        }.flatMapConcat {
            Log.d(TAG, "flatMapConcat2---->$it")
            val result = it.places[0]
            val loc = result.location
            updateWeatherByPlace(PlaceTab(loc.lat, loc.lng, result.name, isLocation = true))
            flowOf(true)
        }
    }

    /**
     * 根据经纬度获取天气信息
     */
    suspend fun updateWeatherByPlace(place: PlaceTab) {
        val weather = remoteApi.getWeatherByLocation(place.lat, place.lng, language, tempUnit)
        val realtime = weather.result.realtime.toRealTimeTab(place, weather.server_time)
        val alert = weather.result.alert.toAlertTab(place.lat, place.lng)
        val hourly = weather.result.hourly.toHourlyTab(place.lat, place.lng)
        val daily = weather.result.daily.toDailyTab(place.lat, place.lng)
        val city = place.copy(
            timeZone = weather.timezone,
            tzshift = weather.tzshift,
            updateTime = weather.server_time
        )
        localeApi.withTransaction {
            insertOrUpdatePlace(city)
            insertOrUpdateRealTime(realtime)
            insertOrUpdateAlerts(place, alert)
            insertOrUpdateHourly(place, hourly)
            insertOrUpdateDaily(place, daily)
        }
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
        Log.d(TAG, "getLocation----》")
        return suspendCancellableCoroutine { continuation ->
            val listener = object : LocationListener {
                override fun onCallback(client: ILocationClient, loc: Location) {
                    if (loc.errorCode == LocationCode.SUCCESS) {
                        continuation.resume(loc)
                    } else {
                        val ex = LocationException(loc.errorCode, loc.message)
                        continuation.resumeWithException(ex)
                    }
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