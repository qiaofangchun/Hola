package com.hola.app.weather.repository.locale.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hola.app.weather.repository.locale.dao.DailyDao

@Entity(tableName = DailyDao.TAB_NAME)
data class DailyTab(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val time: String = "",
    val weatherMain: String = "", // 主要天气现象
    val weatherDay: String = "", // 主要白天天气现象
    val weatherNight: String = "", // 主要夜晚天气现象
    val tempMax: Int = 0, //白天温度
    val tempAvg: Int = 0, //主要温度
    val tempMin: Int = 0, //夜晚温度
    val visibilityMax: Int = 0, //白天可见度
    val visibilityAvg: Int = 0, //主要可见度
    val visibilityMin: Int = 0, //夜晚可见度
    val humidityMax: Double = 0.0, //最大湿度
    val humidityAvg: Double = 0.0, //平均湿度
    val humidityMin: Double = 0.0, //最小湿度
    val windSpeedMax: Int = 0, //最大风速
    val windSpeedAvg: Int = 0, //平均风速
    val windSpeedMin: Int = 0, //最小风速
    val windDirectionMax: Int = 0, //最大风速风向
    val windDirectionAvg: Int = 0, //平均风速风向
    val windDirectionMin: Int = 0, //最小风速风向
    val pressureMax: Int = 0, //最大气压
    val pressureAvg: Int = 0, //平均气压
    val pressureMin: Int = 0, //最小气压
    val precipitationMax: Double = 0.0, //最大降水量
    val precipitationAvg: Double = 0.0, //平均降水量
    val precipitationMin: Double = 0.0, //最小降水量
    val cloudRateMax: Double = 0.0, //最大云量
    val cloudRateAvg: Double = 0.0, //平均云量
    val cloudRateMin: Double = 0.0, //最小云量
    val dswrfMax: Double = 0.0, //最大辐射
    val dswrfAvg: Double = 0.0, //平均辐射
    val dswrfMin: Double = 0.0, //最小辐射
    //紫外线
    val uvLevel: Int = 0,
    val uvDesc: String = "",
    //洗车指数
    val carWashLevel: Int = 0,
    val carWashDesc: String = "",
    //舒适度
    val comfortLevel: Int = 0,
    val comfortDesc: String = "",
    //感冒指数
    val coldRiskLevel: Int = 0,
    val coldRiskDesc: String = "",
    //穿衣指数
    val dressingLevel: Int = 0,
    val dressingDesc: String = "",
)