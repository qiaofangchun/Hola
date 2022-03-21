package com.hola.app.weather.repository.locale.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hola.app.weather.repository.locale.model.HourlyTab
import kotlinx.coroutines.flow.Flow

/**
 * 未来几小时天气Dao
 */
@Dao
interface HourlyDao {
    companion object {
        const val TAB_NAME = "hourly"
        const val COLUMN_LAT = "place_lat"
        const val COLUMN_LNG = "place_lng"
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHourly(hourly: List<HourlyTab>)

    @Query("DELETE FROM $TAB_NAME WHERE $COLUMN_LAT=:lat AND $COLUMN_LNG=:lng")
    suspend fun deleteHourly(lat: Double, lng: Double)

    @Query("SELECT * FROM $TAB_NAME WHERE $COLUMN_LAT=:lat AND $COLUMN_LNG=:lng")
    fun queryHourlyFlow(lat: Double, lng: Double): Flow<List<HourlyTab>>
}