package com.hola.app.weather.repository.remote.model

data class Daily(
    val air_quality: AirQuality,
    val astro: List<Astro>,
    val cloudrate: List<Counts>,
    val dswrf: List<Counts>,
    val humidity: List<Counts>,
    val life_index: LifeIndex,
    val precipitation: List<Counts>,
    val pressure: List<Counts>,
    val skycon: List<SkyCon>,
    val skycon_08h_20h: List<SkyCon>,
    val skycon_20h_32h: List<SkyCon>,
    val temperature: List<Counts>,
    val visibility: List<Counts>,
    val wind: List<WindCounts>
) {
    data class WindCounts(
        val avg: Wind,
        val max: Wind,
        val min: Wind,
    )

    data class AirQuality(
        val aqi: List<Aqi>,
        val pm25: List<Counts>
    )

    data class Aqi(
        val avg: Standard,
        val max: Standard,
        val min: Standard,
    )

    data class LifeIndex(
        val carWashing: List<LifeIndexInfo>,
        val coldRisk: List<LifeIndexInfo>,
        val comfort: List<LifeIndexInfo>,
        val dressing: List<LifeIndexInfo>,
        val ultraviolet: List<LifeIndexInfo>
    )

    data class Astro(
        val sunrise: Time,
        val sunset: Time
    )

    data class Time(
        val time: String
    )
}
