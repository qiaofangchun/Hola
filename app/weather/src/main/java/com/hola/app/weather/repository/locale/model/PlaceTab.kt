package com.hola.app.weather.repository.locale.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "place")
class PlaceTab {
    @PrimaryKey
    var cityId: String? = null
    var timeZone: String? = null
    var latitude = 0f
    var longitude = 0f
    var country: String? = null
    var province: String? = null
    var city: String? = null
    var district: String? = null
    var currentPosition = false
    var residentPosition = false
}