package com.hola.app.weather.ui.main

import android.util.Log
import com.hola.app.weather.repository.WeatherRepository
import com.hola.app.weather.repository.WeatherUseCase
import kotlinx.coroutines.CoroutineScope

class MainUseCase(scope: CoroutineScope) : WeatherUseCase(scope) {
    fun location() {
        doRequest {
            WeatherRepository.updateWeatherByLoc()
        }.onStart {

        }.onSuccess {
            Log.d("abcde","onSuccess $it")
        }.onFailure {
            Log.d("abcde","onFailure ${it.message}")
        }.execute()
    }

    fun update() {
        doRequest {
            //WeatherRepository.getWeatherByLocation(-74.0060, 40.7128, "zh_CN")
            //WeatherRepository.searchPlace("北京")
            WeatherRepository.updateWeatherByLoc()
            //WeatherRepository.getLocation()
        }.onStart {

        }.onSuccess {
            Log.d("abcde","onSuccess $it")
        }.onFailure {
            Log.d("abcde","onFailure ${it.message}")
        }.execute()
    }
}