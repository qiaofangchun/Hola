package com.hola.app.weather.repository.locale.model

import androidx.room.*
import com.hola.app.weather.repository.locale.dao.HourlyDao
import com.hola.app.weather.repository.locale.dao.PlaceDao

@Entity(
    tableName = HourlyDao.TAB_NAME,
    indices = [Index(value = [HourlyDao.COLUMN_LAT, HourlyDao.COLUMN_LNG])],
    foreignKeys = [ForeignKey(
        entity = PlaceTab::class,
        parentColumns = [PlaceDao.COLUMN_LAT, PlaceDao.COLUMN_LNG],
        childColumns = [HourlyDao.COLUMN_LAT, HourlyDao.COLUMN_LNG],
        onDelete = ForeignKey.CASCADE
    )]
)
data class HourlyTab(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name = HourlyDao.COLUMN_LAT)
    val lat: Double = 0.0,
    @ColumnInfo(name = HourlyDao.COLUMN_LNG)
    val lng: Double = 0.0,
    val time: String = "",
    val weatherMain: String = "", // 主要天气现象
    val temp: Int = 0, // 温度
    val visibility: Int = 0, // 可见度
    val humidity: Double = 0.0, // 湿度
    val windDirection: Int = 0, // 风向
    val windSpeed: Int = 0, // 风速
    val pressure: Int = 0, // 气压
    val cloudRate: Double = 0.0, // 云量
    val dswrfval: Double = 0.0, // 辐射
    val precipitation: Double = 0.0, // 降水量
)