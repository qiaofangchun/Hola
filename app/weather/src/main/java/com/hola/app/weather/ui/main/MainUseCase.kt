package com.hola.app.weather.ui.main

import android.util.Log
import com.hola.app.weather.repository.WeatherRepository
import com.hola.app.weather.repository.WeatherUseCase
import kotlinx.coroutines.CoroutineScope

class MainUseCase(scope: CoroutineScope) : WeatherUseCase(scope) {
    fun location() {

    }

    fun update() {
        doRequest {
            WeatherRepository.getWeatherByLocation(-74.0060, 40.7128, "zh_CN")
        }.onStart {

        }.onSuccess {

        }.onFailure {
            Log.d("abcde",it.message)
        }.execute()
    }
}