package com.hola.app.weather.repository

import android.util.Log
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationClientOption.*
import com.hola.app.weather.repository.locale.WeatherDb
import com.hola.app.weather.repository.remote.WeatherNet
import com.hola.app.weather.repository.remote.dao.ApiService
import com.hola.common.utils.AppHelper
import java.util.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object WeatherRepository {
    private val remoteApi = WeatherNet.api
    private val localeApi = WeatherDb.getInstance(AppHelper.context)
    private val language = Locale.getDefault().displayLanguage
    private val tempUnit = ApiService.UNIT_METRIC

    suspend fun getWeatherByLoc(): AMapLocation? {
        return null
    }

    suspend fun searchPlace(place: String) = remoteApi.searchPlace(place, language)

    suspend fun getWeatherByAdCode(
        adCode: String,
        lang: String = language,
        unit: String = tempUnit
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