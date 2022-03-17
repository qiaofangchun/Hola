package com.hola.app.weather.repository

import com.hola.arch.exception.ApiException
import com.hola.arch.usecase.UseCase
import kotlinx.coroutines.CoroutineScope

open class WeatherUseCase(scope: CoroutineScope) : UseCase(scope) {
    override fun handException(throwable: Throwable): ApiException {
        throwable.printStackTrace()
        return object : ApiException(0, throwable) {
            override fun getMessage(throwable: Throwable): String = throwable.message?:"this is error"
        }
    }
}