package com.hola.app.weather.repository.locale.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hola.app.weather.repository.locale.model.HourlyTab

/**
 * 未来几小时天气Dao
 */
@Dao
interface HourlyDao {
    companion object {
        const val TAB_NAME = "hourly"
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHourly(hourly: List<HourlyTab>)

    @Query("DELETE FROM $TAB_NAME WHERE lat=:lat AND lng=:lng")
    suspend fun deleteHourly(lat: Double, lng: Double)

    @Query("SELECT * FROM $TAB_NAME WHERE lat=:lat AND lng=:lng")
    suspend fun queryHourly(lat: Double, lng: Double): List<HourlyTab>
}