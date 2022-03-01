package com.hola.app.weather.repository.remote

import com.hola.app.weather.repository.remote.dao.ApiService
import com.hola.app.weather.repository.remote.model.ApiResult
import com.hola.network.BaseNetwork
import com.hola.arch.exception.DataFailedException

object WeatherNet : BaseNetwork<ApiService>(ApiService.WEATHER_BASE_URL) {
    override fun parserResponseStatus(result: Any) {
        val apiResult = result as ApiResult
        if (apiResult.status != "ok") {
            throw DataFailedException()
        }
    }
}