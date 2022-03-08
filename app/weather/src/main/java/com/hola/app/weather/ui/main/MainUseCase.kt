package com.hola.app.weather.ui.main

import android.util.Log
import com.hola.app.weather.repository.WeatherRepository
import com.hola.app.weather.repository.WeatherUseCase
import kotlinx.coroutines.CoroutineScope

class MainUseCase(scope: CoroutineScope) : WeatherUseCase(scope) {
    fun location() {
        doRequest {
            WeatherRepository.getWeatherByLoc()
        }.onStart {

        }.onSuccess {
            Log.d("abcde","$it")
        }.onFailure {
            Log.d("abcde",it.message)
        }.execute()
    }

    fun update() {
        doRequest {
            //WeatherRepository.getWeatherByLocation(-74.0060, 40.7128, "zh_CN")
            //WeatherRepository.searchPlace("北京")
            WeatherRepository.getWeatherByLoc()
            //WeatherRepository.getLocation()
        }.onStart {

        }.onSuccess {
            Log.d("abcde","$it")
        }.onFailure {
            Log.d("abcde",it.message)
        }.execute()
    }
}