package com.hola.app.weather.repository

import android.util.Log
import com.hola.app.weather.repository.locale.WeatherDb
import com.hola.app.weather.repository.locale.model.AlertTab
import com.hola.app.weather.repository.remote.WeatherNet
import com.hola.app.weather.repository.remote.dao.ApiService
import com.hola.app.weather.repository.remote.model.Alert
import com.hola.common.utils.AppHelper

object WeatherRepository {
    private val remoteApi by lazy { WeatherNet.api }
    private val localeApi by lazy { WeatherDb.getInstance(AppHelper.context) }

    suspend fun getWeatherByAdCode(adCode: String, lang: String) {
        val weather = remoteApi.getWeatherByAdCode(adCode, lang)
        val alerts = ArrayList<Alert>()
        weather.result.alert.content.asSequence().map {
            AlertTab().apply {
                alertId = it.alertId
                adcode = it.adcode
                time = it.pubtimestamp
                description = it.description
                type = it.type
            }
        }
        Log.d("WeatherRepository", weather.toString())
    }

    suspend fun getWeatherByLocation(lng: Double, lat: Double, lang: String) {
        val weather = remoteApi.getWeatherByLocation(lng, lat, lang)
        Log.d("WeatherRepository", weather.toString())
    }
}