package com.hola.app.weather.repository.locale.model

import androidx.room.Entity
import com.hola.app.weather.repository.locale.dao.RealTimeDao

@Entity(tableName = RealTimeDao.TAB_NAME, primaryKeys = ["lat", "lng"])
data class RealTimeTab(
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val weatherMain: Int = 0,
    val temp: Int = 0,
    val apparentTemp: Int = 0,
    val pressure: Int = 0,
    val humidity: Double = 0.0,
    val cloudRate: Double = 0.0,
    val dswrfval: Double = 0.0,
    val visibility: Int = 0,
    val windDirection: Int = 0,
    val windSpeed: Int = 0,
    val precipitation: Double = 0.0,
    val uvLevel: Int = 0,
    val comfortLevel: Int = 0,
    val airQualityPM25: Double? = 0.0,
    val airQualityPM10: Double? = 0.0,
    val airQualityO3: Double? = 0.0,
    val airQualityNO2: Double? = 0.0,
    val airQualitySO2: Double? = 0.0,
    val airQualityCO: Double? = 0.0,
    val airQualityAQI: Double? = 0.0,
)