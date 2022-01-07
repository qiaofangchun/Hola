package com.hola.app.weather.repository.remote.model

data class Hourly(
    val air_quality: AirQuality,
    val cloudrate: List<Counts>,
    val description: String,
    val dswrf: List<Counts>,
    val humidity: List<Counts>,
    val precipitation: List<Counts>,
    val pressure: List<Counts>,
    val skycon: List<SkyCon>,
    val temperature: List<Counts>,
    val visibility: List<Counts>,
    val wind: List<Wind>
) {
    data class AirQuality(
        val aqi: List<AqiX>,
        val pm25: List<Counts>
    )

    data class AqiX(
        val value: Standard
    )
}
