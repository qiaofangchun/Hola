package com.hola.app.weather.repository.locale.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hola.app.weather.repository.locale.model.DailyTab

/**
 * 未来几天天气Dao
 */
@Dao
interface DailyDao {
    companion object {
        const val TAB_NAME = "daily"
        const val COLUMN_LAT = "place_lat"
        const val COLUMN_LNG = "place_lng"
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDaily(hourly: List<DailyTab>)

    @Query("DELETE FROM $TAB_NAME WHERE place_lat=:lat AND place_lng=:lng")
    suspend fun deleteDaily(lat: Double, lng: Double)

    @Query("SELECT * FROM $TAB_NAME WHERE place_lat=:lat AND place_lng=:lng")
    suspend fun queryDaily(lat: Double, lng: Double): List<DailyTab>
}