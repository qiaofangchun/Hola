package com.hola.app.weather.repository.locale.dao

import androidx.room.*
import com.hola.app.weather.repository.locale.model.PlaceTab
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {
    companion object {
        const val TAB_NAME = "place"
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlace(place: PlaceTab)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePlace(place: PlaceTab)

    @Query("SELECT * FROM $TAB_NAME WHERE lat=:lat AND lng=:lng")
    suspend fun queryPlace(lat: Double, lng: Double): PlaceTab?

    @Query("SELECT * FROM $TAB_NAME")
    suspend fun queryPlaces(): List<PlaceTab>
}