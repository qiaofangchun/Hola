package com.hola.app.weather.repository.locale.model

import androidx.room.*
import com.hola.app.weather.repository.locale.dao.AlertDao
import com.hola.app.weather.repository.locale.dao.PlaceDao

@Entity(
    tableName = AlertDao.TAB_NAME,
    indices = [Index(value = [AlertDao.COLUMN_LAT, AlertDao.COLUMN_LNG])],
    foreignKeys = [ForeignKey(
        entity = PlaceTab::class,
        parentColumns = [PlaceDao.COLUMN_LAT, PlaceDao.COLUMN_LNG],
        childColumns = [AlertDao.COLUMN_LAT, AlertDao.COLUMN_LNG],
        onDelete = ForeignKey.CASCADE,
    )]
)
data class AlertTab(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name = AlertDao.COLUMN_LAT)
    val lat: Double = 0.0,
    @ColumnInfo(name = AlertDao.COLUMN_LNG)
    val lng: Double = 0.0,
    val time: String = "",
    val type: Int = 0,
    val level: Int = 0,
    val title: String = "",
    val description: String = "",
)