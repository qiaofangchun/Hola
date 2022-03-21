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

    fun deletePlace() {
        doRequest {
            WeatherRepository.deletePlace(PlaceTab(lat = 23.103174, lng = 113.336746))
        }.onStart {
            // todo start UI state
            Log.d(TAG, "onStart---->")
        }.onSuccess {
            // todo end UI state
            Log.d(TAG, "onSuccess---->$it")
        }.onFailure {
            // todo show error msg
            Log.d(TAG, "onFailure---->${it.message}")
        }.execute()
    }

    fun update(placeTab: PlaceTab) {
        doRequest {
            if (placeTab.isLocation) {
                WeatherRepository.updateWeatherByLoc()
            } else {
                WeatherRepository.updateWeatherByPlace(placeTab)
            }
        }.onStart {
            // todo start UI state
            Log.d(TAG, "onStart---->")
        }.onSuccess {
            // todo end UI state
            Log.d(TAG, "onSuccess---->$it")
        }.onFailure {
            // todo show error msg
            Log.d(TAG, "onFailure---->${it.message}")
        }.execute()
    }
}