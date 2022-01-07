package com.hola.app.weather.repository

import android.util.Log
import com.hola.app.weather.repository.remote.WeatherNet
import com.hola.arch.usecase.UseCase
import kotlinx.coroutines.CoroutineScope

class WeatherUseCase(scope: CoroutineScope) : UseCase(scope, WeatherUseCaseHandler) {
    fun searchPlace(placeName: String, lang: String) {
        doRequest {
            WeatherNet.api.searchPlace(placeName, lang)
        }.onStart {
            Log.d("qfc", "------>onStart, Thread->${Thread.currentThread().name}")
        }.onSuccess { result ->
            Log.d("qfc", "------>Thread->${Thread.currentThread().name}\n result:${result}")
        }.onFailure {
            Log.d("qfc", "------>Thread->${Thread.currentThread().name}\n error:${it.message}")
        }.execute()
    }
}