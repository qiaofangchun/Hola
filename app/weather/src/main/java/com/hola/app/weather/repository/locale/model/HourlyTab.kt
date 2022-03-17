package com.hola.app.weather.repository.locale.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hola.app.weather.repository.locale.dao.HourlyDao
import java.util.*

@Entity(tableName = HourlyDao.TAB_NAME)
data class HourlyTab(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val lat: Double = 0.0,
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