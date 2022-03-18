package com.hola.app.weather.repository.locale.model

import androidx.room.*
import com.hola.app.weather.repository.locale.dao.AlertDao

@Entity(
    tableName = AlertDao.TAB_NAME,
    indices = [Index(value = ["place_lat", "place_lng"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = PlaceTab::class,
        parentColumns = ["lat", "lng"],
        childColumns = ["place_lat", "place_lng"],
        onDelete = ForeignKey.CASCADE,
    )]
)
data class AlertTab(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "place_lat")
    val lat: Double = 0.0,
    @ColumnInfo(name = "place_lng")
    val lng: Double = 0.0,
    val time: String = "",
    val type: Int = 0,
    val level: Int = 0,
    val title: String = "",
    val description: String = "",
)