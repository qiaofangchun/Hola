package com.hola.app.weather.repository.locale.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.hola.app.weather.repository.locale.dao.PlaceDao
import com.hola.app.weather.repository.locale.dao.RealTimeDao

@Entity(
    tableName = RealTimeDao.TAB_NAME,
    primaryKeys = [RealTimeDao.COLUMN_LAT, RealTimeDao.COLUMN_LNG],
    indices = [Index(value = [RealTimeDao.COLUMN_LAT, RealTimeDao.COLUMN_LNG])],
    foreignKeys = [ForeignKey(
        entity = PlaceTab::class,
        parentColumns = [PlaceDao.COLUMN_LAT, PlaceDao.COLUMN_LNG],
        childColumns = [RealTimeDao.COLUMN_LAT, RealTimeDao.COLUMN_LNG],
        onDelete = ForeignKey.CASCADE
    )]
)
data class RealTimeTab(
    @ColumnInfo(name = RealTimeDao.COLUMN_LAT)
    val lat: Double = 0.0,
    @ColumnInfo(name = RealTimeDao.COLUMN_LNG)
    val lng: Double = 0.0,
    val time: String = "",
    val weatherMain: String = "", // 主要天气现象
    val temp: Int = 0, // 温度
    val apparentTemp: Int = 0, // 体感温度
    val pressure: Int = 0, // 气压
    val humidity: Double = 0.0, // 湿度
    val cloudRate: Double = 0.0, // 云量
    val dswrfval: Double = 0.0, // 辐射
    val visibility: Int = 0, // 可见度
    val windDirection: Int = 0, // 风向
    val windSpeed: Int = 0, // 风速
    val precipitation: Double = 0.0, // 降雨量
    val uvLevel: Int = 0, // 紫外线等级
    val comfortLevel: Int = 0, // 舒适度
    val airQualityPM25: Int? = 0, // pm2.5
    val airQualityPM10: Int? = 0, // pm10
    val airQualityO3: Int? = 0, // o3
    val airQualityNO2: Int? = 0, // no2
    val airQualitySO2: Int? = 0, // so2
    val airQualityCO: Double? = 0.0, // co
    val airQualityAQI: Int? = 0, // aqi
)