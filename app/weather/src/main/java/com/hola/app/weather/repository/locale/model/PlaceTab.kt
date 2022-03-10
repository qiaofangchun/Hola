package com.hola.app.weather.repository.locale.model

import androidx.room.Entity
import androidx.room.Ignore
import com.hola.app.weather.repository.locale.dao.PlaceDao

@Entity(tableName = PlaceDao.TAB_NAME, primaryKeys = ["lat", "lng"])
data class PlaceTab(
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val name: String? = null,
    val timeZone: String? = null,
    val isLocation: Boolean = false,
){
    @Ignore
    constructor() : this(0.0, 0.0)
}