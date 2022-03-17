package com.hola.app.weather.repository.remote.model

data class Realtime(
    val air_quality: AirQuality?,
    val apparent_temperature: Double,
    val cloudrate: Double,
    val dswrf: Double,
    val humidity: Double,
    val life_index: LifeIndex,
    val precipitation: Precipitation,
    val pressure: Double,
    val skycon: String,
    val status: String,
    val temperature: Double,
    val visibility: Double,
    val wind: Wind
) {

    data class AirQuality(
        val aqi: Standard,
        val co: Double,
        val no2: Int,
        val o3: Int,
        val pm10: Int,
        val pm25: Int,
        val so2: Int,
        val description: Standard
    )

    data class LifeIndex(
        val comfort: LifeIndexInfo,
        val ultraviolet: LifeIndexInfo
    )

    data class Precipitation(
        val local: Local,
        val nearest: Nearest
    )

    data class Local(
        val datasource: String,
        val intensity: Double,
        val status: String
    )

    data class Nearest(
        val distance: Double,
        val intensity: Double,
        val status: String
    )
}