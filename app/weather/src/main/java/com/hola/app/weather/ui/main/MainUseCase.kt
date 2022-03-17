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
            Log.d("qfc","onSuccess")
        }.onFailure {
            Log.d("qfc", "onFailure ${it.message}")
        }.execute()
    }

    fun update() {
        doRequest {
            WeatherRepository.searchPlace("四川省达州市宣汉县芭蕉镇")
        }.onStart {

        }.onSuccess {
            Log.d("qfc","onSuccess $it")
        }.onFailure {
            Log.d("qfc", "onFailure ${it.message}")
        }.execute()
    }
}