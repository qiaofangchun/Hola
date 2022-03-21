package com.hola.app.weather.repository.locale.dao

import androidx.room.*
import com.hola.app.weather.repository.locale.model.RealTimeTab
import kotlinx.coroutines.flow.Flow

/**
 * 当前天气Dao
 */
@Dao
interface RealTimeDao {
    companion object {
        const val TAB_NAME = "real_time"
        const val COLUMN_LAT = "place_lat"
        const val COLUMN_LNG = "place_lng"
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRealTime(realTime: RealTimeTab)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRealTime(realTime: RealTimeTab)

    @Query("SELECT * FROM $TAB_NAME WHERE $COLUMN_LAT=:lat AND $COLUMN_LNG=:lng")
    fun queryRealTimeFlow(lat: Double, lng: Double): Flow<RealTimeTab?>

    @Query("SELECT * FROM $TAB_NAME WHERE $COLUMN_LAT=:lat AND $COLUMN_LNG=:lng")
    fun queryRealTime(lat: Double, lng: Double): RealTimeTab?
}