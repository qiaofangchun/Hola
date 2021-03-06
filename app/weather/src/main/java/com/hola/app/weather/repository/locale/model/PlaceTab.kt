package com.hola.app.weather.repository.locale.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import com.hola.app.weather.repository.locale.dao.PlaceDao

@Entity(
    tableName = PlaceDao.TAB_NAME,
    primaryKeys = [PlaceDao.COLUMN_LAT, PlaceDao.COLUMN_LNG],
    indices = [Index(value = [PlaceDao.COLUMN_LAT, PlaceDao.COLUMN_LNG], unique = true)],
)
data class PlaceTab(
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val name: String? = null,
    val timeZone: String? = null,
    val tzshift: Int? = null,
    val isLocation: Boolean = false,
    val updateTime: String = ""
) {
    @Ignore
    constructor() : this(0.0, 0.0)
}