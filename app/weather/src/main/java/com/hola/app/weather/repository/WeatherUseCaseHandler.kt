package com.hola.app.weather.repository

import com.hola.arch.exception.ApiException
import com.hola.arch.usecase.UseCaseHandler

object WeatherUseCaseHandler : UseCaseHandler {
    override fun <T> handResponse(result: Result<T>): T? {
        return result.getOrNull()
    }

    override fun handException(throwable: Throwable): ApiException {
        return object : ApiException(0, throwable) {
            override fun getMessage(throwable: Throwable): String {
                return "${throwable.message}"
            }
        }
    }
}