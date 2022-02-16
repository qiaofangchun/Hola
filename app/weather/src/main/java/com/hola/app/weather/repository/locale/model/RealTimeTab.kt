package com.hola.app.weather.repository.locale.model

import androidx.room.Entity
import java.util.*

@Entity(tableName = "current")
class RealTimeTab {
    // base.
    var cityId: String? = null
    var weatherSource: String? = null
    var timeStamp: Long = 0
    var publishDate: Date? = null
    var publishTime: Long = 0
    var updateDate: Date? = null
    var updateTime: Long = 0

    // current.
    var weatherText: String? = null
    var weatherCode: String? = null
    var temperature = 0
    var realFeelTemperature: Int? = null
    var realFeelShaderTemperature: Int? = null
    var apparentTemperature: Int? = null
    var windChillTemperature: Int? = null
    var wetBulbTemperature: Int? = null
    var degreeDayTemperature: Int? = null

    var totalPrecipitation: Float? = null
    var thunderstormPrecipitation: Float? = null
    var rainPrecipitation: Float? = null
    var snowPrecipitation: Float? = null
    var icePrecipitation: Float? = null

    var totalPrecipitationProbability: Float? = null
    var thunderstormPrecipitationProbability: Float? = null
    var rainPrecipitationProbability: Float? = null
    var snowPrecipitationProbability: Float? = null
    var icePrecipitationProbability: Float? = null

    var windDirection: String? = null

    var windDegree: Float? = null
    var windSpeed: Float? = null
    var windLevel: String? = null

    var uvIndex: Int? = null
    var uvLevel: String? = null
    var uvDescription: String? = null

    var aqiText: String? = null
    var aqiIndex: Int? = null
    var pm25: Float? = null
    var pm10: Float? = null
    var so2: Float? = null
    var no2: Float? = null
    var o3: Float? = null
    var co: Float? = null

    var relativeHumidity: Float? = null
    var pressure: Float? = null
    var visibility: Float? = null
    var dewPoint: Int? = null
    var cloudCover: Int? = null
    var ceiling: Float? = null

    var dailyForecast: String? = null
    var hourlyForecast: String? = null

    /*@ToMany(
        joinProperties = [JoinProperty(
            name = "cityId",
            referencedName = "cityId"
        ), JoinProperty(name = "weatherSource", referencedName = "weatherSource")]
    )
    @OrderBy("date ASC")*/
    var daily: List<DailyTab>? = null

    /*@ToMany(
        joinProperties = [JoinProperty(
            name = "cityId",
            referencedName = "cityId"
        ), JoinProperty(name = "weatherSource", referencedName = "weatherSource")]
    )
    @OrderBy("date ASC")*/
    var hourly: List<HourlyTab>? = null

    /*@ToMany(
        joinProperties = [JoinProperty(
            name = "cityId",
            referencedName = "cityId"
        ), JoinProperty(name = "weatherSource", referencedName = "weatherSource")]
    )
    @OrderBy("date ASC")*/
    var minutely: List<MinutelyTab>? = null

    /*@ToMany(
        joinProperties = [JoinProperty(
            name = "cityId",
            referencedName = "cityId"
        ), JoinProperty(name = "weatherSource", referencedName = "weatherSource")]
    )
    @OrderBy("date ASC")*/
    var alert: List<AlertTab>? = null
}