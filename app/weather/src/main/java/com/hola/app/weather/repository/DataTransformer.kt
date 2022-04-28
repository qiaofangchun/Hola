package com.hola.app.weather.repository

import com.hola.app.weather.repository.locale.model.*
import com.hola.app.weather.repository.remote.model.*
import com.hola.app.weather.widget.weather.WeatherType
import kotlin.math.roundToInt

fun Realtime.toRealTimeTab(place: PlaceTab, time: String): RealTimeTab = RealTimeTab(
    lat = place.lat,
    lng = place.lng,
    time = time,
    weatherMain = this.skycon,
    temp = this.temperature.roundToInt(),
    apparentTemp = this.apparent_temperature.roundToInt(),
    pressure = this.pressure.roundToInt(),
    humidity = this.humidity,
    cloudRate = this.cloudrate,
    dswrfval = this.dswrf,
    visibility = this.visibility.roundToInt(),
    windDirection = this.wind.direction.roundToInt(),
    windSpeed = this.wind.speed.roundToInt(),
    precipitation = this.precipitation.local.intensity,
    airQualityPM25 = this.air_quality?.pm25,
    airQualityPM10 = this.air_quality?.pm10,
    airQualityNO2 = this.air_quality?.no2,
    airQualityO3 = this.air_quality?.o3,
    airQualitySO2 = this.air_quality?.so2,
    airQualityCO = this.air_quality?.co
)

fun Weather.Alerts.toAlertTab(lat: Double, lng: Double): List<AlertTab> {
    val alerts = ArrayList<AlertTab>()
    this.content.forEach {
        alerts.add(
            AlertTab(
                lat = lat,
                lng = lng,
                level = it.code.substring(0, it.code.length).toInt(),
                type = it.code.substring(0, 2).toInt(),
                title = it.title,
                description = it.description
            )
        )
    }
    return alerts
}

fun Hourly.toHourlyTab(lat: Double, lng: Double): List<HourlyTab> {
    val hourly = ArrayList<HourlyTab>()
    this.skycon.forEachIndexed { index, skycon ->
        hourly.add(
            HourlyTab(
                lat = lat,
                lng = lng,
                time = skycon.datetime,
                weatherMain = skycon.value,
                temp = this.temperature[index].value?.roundToInt() ?: 0,
                visibility = this.visibility[index].value?.roundToInt() ?: 0,
                humidity = this.humidity[index].value ?: 0.0,
                windDirection = this.wind[index].direction.roundToInt(),
                windSpeed = this.wind[index].speed.roundToInt(),
                pressure = this.pressure[index].value?.roundToInt() ?: 0,
                cloudRate = this.cloudrate[index].value ?: 0.0,
                dswrfval = this.dswrf[index].value ?: 0.0,
                precipitation = this.precipitation[index].value ?: 0.0
            )
        )
    }
    return hourly
}

fun Daily.toDailyTab(lat: Double, lng: Double): List<DailyTab> {
    val daily = ArrayList<DailyTab>()
    this.skycon.forEachIndexed { index, skyCon ->
        val time = skyCon.date
        val temp = temperature[index]
        val visibility = visibility[index]
        val humidity = humidity[index]
        val wind = wind[index]
        val pressure = pressure[index]
        val precipitation = precipitation[index]
        val cloudRate = cloudrate[index]
        val dswrf = dswrf[index]
        val uvIndex = life_index.ultraviolet[index]
        val carWash = life_index.carWashing[index]
        val coldRisk = life_index.carWashing[index]
        val comfort = life_index.carWashing[index]
        val dressing = life_index.carWashing[index]
        daily.add(
            DailyTab(
                lat = lat,
                lng = lng,
                time = time,
                weatherMain = skyCon.value,
                weatherDay = skycon_08h_20h[index].value,
                weatherNight = skycon_20h_32h[index].value,
                tempMax = temp.max?.roundToInt() ?: 0,
                tempAvg = temp.avg?.roundToInt() ?: 0,
                tempMin = temp.min?.roundToInt() ?: 0,
                visibilityMax = visibility.max?.roundToInt() ?: 0,
                visibilityAvg = visibility.avg?.roundToInt() ?: 0,
                visibilityMin = visibility.min?.roundToInt() ?: 0,
                humidityMax = humidity.max ?: 0.0,
                humidityAvg = humidity.avg ?: 0.0,
                humidityMin = humidity.min ?: 0.0,
                windDirectionMax = wind.max.direction.roundToInt(),
                windDirectionAvg = wind.avg.direction.roundToInt(),
                windDirectionMin = wind.min.direction.roundToInt(),
                windSpeedMax = wind.max.speed.roundToInt(),
                windSpeedAvg = wind.avg.speed.roundToInt(),
                windSpeedMin = wind.min.speed.roundToInt(),
                pressureMax = pressure.max?.roundToInt() ?: 0,
                pressureAvg = pressure.avg?.roundToInt() ?: 0,
                pressureMin = pressure.min?.roundToInt() ?: 0,
                precipitationMax = precipitation.max ?: 0.0,
                precipitationAvg = precipitation.avg ?: 0.0,
                precipitationMin = precipitation.min ?: 0.0,
                cloudRateMax = cloudRate.max ?: 0.0,
                cloudRateAvg = cloudRate.avg ?: 0.0,
                cloudRateMin = cloudRate.min ?: 0.0,
                dswrfMax = dswrf.max ?: 0.0,
                dswrfAvg = dswrf.avg ?: 0.0,
                dswrfMin = dswrf.min ?: 0.0,
                uvLevel = uvIndex.index.toInt(),
                uvDesc = uvIndex.desc,
                carWashLevel = carWash.index.toInt(),
                carWashDesc = carWash.desc,
                coldRiskLevel = coldRisk.index.toInt(),
                coldRiskDesc = coldRisk.desc,
                comfortLevel = comfort.index.toInt(),
                comfortDesc = comfort.desc,
                dressingLevel = dressing.index.toInt(),
                dressingDesc = dressing.desc,
            )
        )
    }
    return daily
}

fun String?.safe(): String = this ?: ""

fun String.toWeather(isNight: Boolean): Int {
    return when (this) {
        "CLEAR_DAY" -> if (isNight) WeatherType.CLEAR_N else WeatherType.CLEAR_D
        "CLEAR_NIGHT" -> if (isNight) WeatherType.CLEAR_N else WeatherType.CLEAR_D
        "PARTLY_CLOUDY_DAY" -> if (isNight) WeatherType.CLOUDY_N else WeatherType.CLOUDY_D
        "PARTLY_CLOUDY_NIGHT" -> if (isNight) WeatherType.CLOUDY_N else WeatherType.CLOUDY_D
        "CLOUDY" -> if (isNight) WeatherType.OVERCAST_N else WeatherType.OVERCAST_D
        "LIGHT_HAZE" -> if (isNight) WeatherType.HAZE_N else WeatherType.HAZE_D
        "MODERATE_HAZE" -> if (isNight) WeatherType.HAZE_N else WeatherType.HAZE_D
        "HEAVY_HAZE" -> if (isNight) WeatherType.HAZE_N else WeatherType.HAZE_D
        "LIGHT_RAIN" -> if (isNight) WeatherType.RAIN_N else WeatherType.RAIN_D
        "MODERATE_RAIN" -> if (isNight) WeatherType.RAIN_N else WeatherType.RAIN_D
        "HEAVY_RAIN" -> if (isNight) WeatherType.RAIN_N else WeatherType.RAIN_D
        "STORM_RAIN" -> if (isNight) WeatherType.RAIN_N else WeatherType.RAIN_D
        "FOG" -> if (isNight) WeatherType.FOG_N else WeatherType.FOG_D
        "LIGHT_SNOW" -> if (isNight) WeatherType.SNOW_N else WeatherType.SNOW_D
        "MODERATE_SNOW" -> if (isNight) WeatherType.SNOW_N else WeatherType.SNOW_D
        "HEAVY_SNOW" -> if (isNight) WeatherType.SNOW_N else WeatherType.SNOW_D
        "STORM_SNOW" -> if (isNight) WeatherType.SNOW_N else WeatherType.SNOW_D
        "DUST" -> if (isNight) WeatherType.SAND_N else WeatherType.SAND_D
        "SAND" -> if (isNight) WeatherType.SAND_N else WeatherType.SAND_D
        "WIND" -> if (isNight) WeatherType.WIND_N else WeatherType.WIND_D
        else -> if (isNight) WeatherType.UNKNOWN_N else WeatherType.UNKNOWN_D
    }
}