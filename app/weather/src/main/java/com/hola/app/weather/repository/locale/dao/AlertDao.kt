package com.hola.app.weather.repository.locale.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hola.app.weather.repository.locale.model.AlertTab
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertDao {
    companion object {
        const val TAB_NAME = "alert"
        const val COLUMN_LAT = "place_lat"
        const val COLUMN_LNG = "place_lng"
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlert(hourly: List<AlertTab>)

    @Query("DELETE FROM $TAB_NAME WHERE $COLUMN_LAT=:lat AND $COLUMN_LNG=:lng")
    suspend fun deleteAlert(lat: Double, lng: Double)

    @Query("SELECT * FROM $TAB_NAME WHERE $COLUMN_LAT=:lat AND $COLUMN_LNG=:lng")
    fun queryAlertFlow(lat: Double, lng: Double): Flow<List<AlertTab>>
}