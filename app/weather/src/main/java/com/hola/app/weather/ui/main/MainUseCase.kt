package com.hola.app.weather.ui.main

import android.util.Log
import com.hola.app.weather.repository.WeatherRepository
import com.hola.app.weather.repository.WeatherUseCase
import com.hola.app.weather.repository.locale.model.PlaceTab
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainUseCase(scope: CoroutineScope) : WeatherUseCase(scope) {
    companion object {
        private const val TAG = "MainUseCase"
    }

    fun getWeather() {

    }

    fun update(placeTab: PlaceTab) {
        coroutineScope.launch {
            //WeatherRepository.searchPlace("北京")
            WeatherRepository.updateWeatherByLoc()
                .flowOn(Dispatchers.IO)
                .catch { ex->
                    Log.d("qfc", "ex----->${ex.message}")
                }
                .collect {
                    Log.d("qfc", "data----->$it")
                }
        }
    }
}