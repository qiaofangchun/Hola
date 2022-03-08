package com.hola.app.weather.repository.locale.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "minutely")
class MinutelyTab {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    var cityId: String? = null
    var date: Date? = null
    var time: Long = 0
    var daylight = false

    var weatherText: String? = null

    var weatherCode: String? = null

    var minuteInterval = 0
    var dbz: Int? = null
    var cloudCover: Int? = null
}