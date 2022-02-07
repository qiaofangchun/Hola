package com.hola.app.weather.repository

import com.hola.arch.exception.ApiException
import com.hola.arch.usecase.UseCase
import kotlinx.coroutines.CoroutineScope

class WeatherUseCase(scope: CoroutineScope) : UseCase(scope) {
    /*fun searchPlace(placeName: String, lang: String) {
        doRequest {
            sync { WeatherNet.api.searchPlace(placeName, lang) }
        }.onStart {
            Log.d("qfc", "------>onStart, Thread->${Thread.currentThread().name}")
        }.onSuccess { result ->
            Log.d("qfc", "------>Thread->${Thread.currentThread().name}\n result:${result}")
        }.onFailure {
            Log.d("qfc", "------>Thread->${Thread.currentThread().name}\n error:${it.message}")
        }.execute()
    }*/

    override fun handException(throwable: Throwable): ApiException {
        return object : ApiException(0, throwable) {
            override fun getMessage(throwable: Throwable): String = "this is error"
        }
    }
}