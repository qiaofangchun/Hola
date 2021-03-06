package com.hola.app.weather.ui.main

import android.util.Log
import com.hola.app.weather.repository.WeatherRepository
import com.hola.app.weather.repository.WeatherUseCase
import com.hola.app.weather.repository.locale.model.PlaceTab
import kotlinx.coroutines.CoroutineScope

class MainUseCase(scope: CoroutineScope) : WeatherUseCase(scope) {
    companion object {
        private const val TAG = "MainUseCase"
    }

    fun getWeather() {

    }

    fun update(placeTab: PlaceTab) {
        doRequest {
            Log.d(TAG, "doRequest---->thread:${Thread.currentThread().name}")
            if (placeTab.isLocation) {
                WeatherRepository.updateWeatherByLoc()
            } else {
                WeatherRepository.updateWeatherByPlace(placeTab)
            }
        }.onStart {
            // todo start UI state
            Log.d(TAG, "update onStart---->thread:${Thread.currentThread().name}")
        }.onSuccess {
            // todo end UI state
            Log.d(TAG, "update onSuccess---->$it,thread:${Thread.currentThread().name}")
        }.onFailure {
            // todo show error msg
            Log.d(TAG, "update onFailure---->${it.message},thread:${Thread.currentThread().name}")
        }.execute()
    }
}