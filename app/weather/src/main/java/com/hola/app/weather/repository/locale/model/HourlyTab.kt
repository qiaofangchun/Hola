package com.hola.app.weather.repository.locale.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "hourly")
class HourlyTab {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    var cityId: String? = null
    var date: Date? = null
    var time: Long = 0
    var daylight = false

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
}