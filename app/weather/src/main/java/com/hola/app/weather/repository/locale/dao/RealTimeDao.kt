package com.hola.app.weather.repository.locale.dao

import androidx.room.*
import com.hola.app.weather.repository.locale.model.RealTimeTab

/**
 * 当前天气Dao
 */
@Dao
interface RealTimeDao {
    companion object {
        const val TAB_NAME = "real_time"
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRealTime(realTime: RealTimeTab)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRealTime(realTime: RealTimeTab)

    @Query("SELECT * FROM $TAB_NAME WHERE place_lat=:lat AND place_lng=:lng")
    suspend fun queryRealTime(lat: Double, lng: Double): RealTimeTab?
}