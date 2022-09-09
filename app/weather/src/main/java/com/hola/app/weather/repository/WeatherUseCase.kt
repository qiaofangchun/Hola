package com.hola.app.weather.repository

import com.hola.arch.exception.RemoteException
import com.hola.arch.domain.UseCase
import kotlinx.coroutines.CoroutineScope

open class WeatherUseCase(scope: CoroutineScope) : UseCase(scope) {
    override fun handException(throwable: Throwable): RemoteException {
        return RemoteException(0, throwable)
    }
}