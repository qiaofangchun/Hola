package com.hola.app.weather.repository.locale.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hola.app.weather.repository.locale.dao.AlertDao

@Entity(tableName = AlertDao.TAB_NAME)
data class AlertTab(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val time: Long = 0,
    val type: Int = 0,
    val level: Int = 0,
    val title: String = "",
    val description: String = "",
    val content: String = ""
)