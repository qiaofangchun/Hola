package com.hola.app.weather.repository.locale.dao

import androidx.room.*
import com.hola.app.weather.repository.locale.model.PlaceTab
import kotlinx.coroutines.flow.Flow

/**
 * 位置信息Dao
 */
@Dao
interface PlaceDao {
    companion object {
        const val TAB_NAME = "place"
        const val COLUMN_LAT = "lat"
        const val COLUMN_LNG = "lng"
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlace(place: PlaceTab)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePlace(place: PlaceTab)

    @Query("SELECT * FROM $TAB_NAME WHERE $COLUMN_LAT=:lat AND $COLUMN_LNG=:lng")
    suspend fun queryPlace(lat: Double, lng: Double): PlaceTab?

    @Query("SELECT * FROM $TAB_NAME ORDER BY isLocation, updateTime ASC")
    fun queryPlacesFlow(): Flow<List<PlaceTab>>

    @Delete
    suspend fun deletePlace(place: PlaceTab)

    @Delete
    suspend fun deletePlaces(places: List<PlaceTab>)
}