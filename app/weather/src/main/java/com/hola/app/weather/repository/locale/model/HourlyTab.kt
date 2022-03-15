package com.hola.app.weather.repository.locale.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hola.app.weather.repository.locale.dao.HourlyDao
import java.util.*

@Entity(tableName = HourlyDao.TAB_NAME)
data class HourlyTab(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val weatherMain: Int = 0,
    val temp: Double = 0.0,
    val pressure: Double = 0.0,
    val humidity: Double = 0.0,
    val cloudRate: Double = 0.0,
    val dswrfval: Double = 0.0,
    val visibility: Double = 0.0,
    val windDirection: Int = 0,
    val windSpeed: Int = 0,
    val precipitation: Double = 0.0,
)