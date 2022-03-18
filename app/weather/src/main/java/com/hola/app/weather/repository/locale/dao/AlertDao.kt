package com.hola.app.weather.repository.locale.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hola.app.weather.repository.locale.model.AlertTab

@Dao
interface AlertDao {
    companion object {
        const val TAB_NAME = "alert"
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlert(hourly: List<AlertTab>)

    @Query("DELETE FROM $TAB_NAME WHERE place_lat=:lat AND place_lng=:lng")
    suspend fun deleteAlert(lat: Double, lng: Double)

    @Query("SELECT * FROM $TAB_NAME WHERE place_lat=:lat AND place_lng=:lng")
    suspend fun queryAlert(lat: Double, lng: Double): List<AlertTab>
}