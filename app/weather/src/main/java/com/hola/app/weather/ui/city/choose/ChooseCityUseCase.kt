package com.hola.app.weather.ui.city.choose

import android.util.Log
import com.hola.app.weather.repository.WeatherUseCase
import com.hola.app.weather.repository.remote.WeatherNet
import kotlinx.coroutines.CoroutineScope

class ChooseCityUseCase(scope: CoroutineScope) : WeatherUseCase(scope) {
    fun searchPlace(placeName: String, lang: String) {
        doRequest {
            sync { WeatherNet.api.searchPlace(placeName, lang) }
        }.onStart {
            Log.d("qfc", "------>onStart, Thread->${Thread.currentThread().name}")
        }.onSuccess { result ->
            Log.d("qfc", "------>Thread->${Thread.currentThread().name}\n result:${result}")
        }.onFailure {
            Log.d("qfc", "------>Thread->${Thread.currentThread().name}\n error:${it.message}")
        }.execute()
    }
}