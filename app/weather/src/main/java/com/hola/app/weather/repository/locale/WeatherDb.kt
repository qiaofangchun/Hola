package com.hola.app.weather.repository.locale

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hola.app.weather.Constant
import com.hola.app.weather.repository.locale.dao.PlaceDao
import com.hola.app.weather.repository.locale.model.PlaceTab

@Database(entities = [PlaceTab::class], version = 1)
abstract class WeatherDb : RoomDatabase() {

    abstract fun placeDao(): PlaceDao

    companion object {
        @Volatile
        private var instance: WeatherDb? = null

        fun getInstance(context: Context): WeatherDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): WeatherDb {
            return Room.databaseBuilder(context, WeatherDb::class.java, Constant.WEATHER_DB_NAME)
                .build()
        }
    }
}