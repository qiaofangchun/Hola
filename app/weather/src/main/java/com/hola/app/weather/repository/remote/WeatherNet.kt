package com.hola.app.weather.repository.remote

import com.hola.app.weather.repository.remote.dao.ApiService
import com.hola.app.weather.repository.remote.model.ApiResult
import com.hola.arch.exception.RemoteException
import com.hola.network.BaseNetwork

object WeatherNet : BaseNetwork<ApiService>(ApiService.WEATHER_BASE_URL) {
    private const val STATUS_SUCCESS = "ok"
    private const val STATUS_FAILURE = "failed"

    override fun parserResponseStatus(result: Any) {
        if (result !is ApiResult) {
            return
        }
        when (result.status) {
            STATUS_SUCCESS -> return
            STATUS_FAILURE -> {
                val msg = "Service Api status:${result.status}, msg:${result.error}"
                throw RemoteException(0, msg)
            }
            else -> throw RemoteException(0, result.status)
        }
    }
}