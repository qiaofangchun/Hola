package com.hola.app.weather.repository.locale.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "alert")
class AlertTab {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var cityId: String? = null
    var alertId: Long = 0
    var date: Date? = null
    var time: Long = 0

    var description: String? = null
    var content: String? = null

    var type: String? = null
    var priority = 0
    var color = 0
}