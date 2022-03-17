package com.hola.app.weather.repository.remote.model

data class WeatherResult(
    val result: Weather,
    val timezone: String,
    val tzshift: Int,
    val location: Location,
    val server_time: String
) : ApiResult()