package com.hola.app.weather.repository.locale.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "place")
data class PlaceTab(
    @PrimaryKey
    val cityId: String,
    val timeZone: String,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val country: String? = null,
    val province: String? = null,
    val city: String? = null,
    val district: String? = null,
    val isLocation: Boolean = false
)