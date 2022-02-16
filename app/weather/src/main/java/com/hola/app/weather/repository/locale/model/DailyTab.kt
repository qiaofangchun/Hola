package com.hola.app.weather.repository.locale.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "daily")
class DailyTab {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    var cityId: String? = null
    var weatherSource: String? = null

    var date: Date? = null
    var time: Long = 0

    // daytime.
    var daytimeWeatherText: String? = null
    var daytimeWeatherPhase: String? = null

    var daytimeWeatherCode: String? = null

    var daytimeTemperature = 0
    var daytimeRealFeelTemperature: Int? = null
    var daytimeRealFeelShaderTemperature: Int? = null
    var daytimeApparentTemperature: Int? = null
    var daytimeWindChillTemperature: Int? = null
    var daytimeWetBulbTemperature: Int? = null
    var daytimeDegreeDayTemperature: Int? = null

    var daytimeTotalPrecipitation: Float? = null
    var daytimeThunderstormPrecipitation: Float? = null
    var daytimeRainPrecipitation: Float? = null
    var daytimeSnowPrecipitation: Float? = null
    var daytimeIcePrecipitation: Float? = null

    var daytimeTotalPrecipitationProbability: Float? = null
    var daytimeThunderstormPrecipitationProbability: Float? = null
    var daytimeRainPrecipitationProbability: Float? = null
    var daytimeSnowPrecipitationProbability: Float? = null
    var daytimeIcePrecipitationProbability: Float? = null

    var daytimeTotalPrecipitationDuration: Float? = null
    var daytimeThunderstormPrecipitationDuration: Float? = null
    var daytimeRainPrecipitationDuration: Float? = null
    var daytimeSnowPrecipitationDuration: Float? = null
    var daytimeIcePrecipitationDuration: Float? = null

    var daytimeWindDirection: String? = null

    var daytimeWindDegree: Float? = null
    var daytimeWindSpeed: Float? = null
    var daytimeWindLevel: String? = null

    var daytimeCloudCover: Int? = null

    // nighttime.
    var nighttimeWeatherText: String? = null
    var nighttimeWeatherPhase: String? = null

    var nighttimeWeatherCode: String? = null

    var nighttimeTemperature = 0
    var nighttimeRealFeelTemperature: Int? = null
    var nighttimeRealFeelShaderTemperature: Int? = null
    var nighttimeApparentTemperature: Int? = null
    var nighttimeWindChillTemperature: Int? = null
    var nighttimeWetBulbTemperature: Int? = null
    var nighttimeDegreeDayTemperature: Int? = null

    var nighttimeTotalPrecipitation: Float? = null
    var nighttimeThunderstormPrecipitation: Float? = null
    var nighttimeRainPrecipitation: Float? = null
    var nighttimeSnowPrecipitation: Float? = null
    var nighttimeIcePrecipitation: Float? = null

    var nighttimeTotalPrecipitationProbability: Float? = null
    var nighttimeThunderstormPrecipitationProbability: Float? = null
    var nighttimeRainPrecipitationProbability: Float? = null
    var nighttimeSnowPrecipitationProbability: Float? = null
    var nighttimeIcePrecipitationProbability: Float? = null

    var nighttimeTotalPrecipitationDuration: Float? = null
    var nighttimeThunderstormPrecipitationDuration: Float? = null
    var nighttimeRainPrecipitationDuration: Float? = null
    var nighttimeSnowPrecipitationDuration: Float? = null
    var nighttimeIcePrecipitationDuration: Float? = null

    var nighttimeWindDirection: String? = null

    var nighttimeWindDegree: Float? = null
    var nighttimeWindSpeed: Float? = null
    var nighttimeWindLevel: String? = null

    var nighttimeCloudCover: Int? = null

    // sun.
    var sunRiseDate: Date? = null
    var sunSetDate: Date? = null

    // moon.
    var moonRiseDate: Date? = null
    var moonSetDate: Date? = null

    // moon phase.
    var moonPhaseAngle: Int? = null
    var moonPhaseDescription: String? = null

    // aqi.
    var aqiText: String? = null
    var aqiIndex: Int? = null
    var pm25: Float? = null
    var pm10: Float? = null
    var so2: Float? = null
    var no2: Float? = null
    var o3: Float? = null
    var co: Float? = null

    // pollen.
    var grassIndex: Int? = null
    var grassLevel: Int? = null
    var grassDescription: String? = null
    var moldIndex: Int? = null
    var moldLevel: Int? = null
    var moldDescription: String? = null
    var ragweedIndex: Int? = null
    var ragweedLevel: Int? = null
    var ragweedDescription: String? = null
    var treeIndex: Int? = null
    var treeLevel: Int? = null
    var treeDescription: String? = null

    // uv.
    var uvIndex: Int? = null
    var uvLevel: String? = null
    var uvDescription: String? = null

    var hoursOfSun = 0f
}