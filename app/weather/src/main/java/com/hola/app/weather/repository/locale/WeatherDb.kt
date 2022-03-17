package com.hola.app.weather.repository.locale

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hola.app.weather.repository.locale.dao.*
import com.hola.app.weather.repository.locale.model.*

@Database(
    entities = [PlaceTab::class, RealTimeTab::class, AlertTab::class, HourlyTab::class, DailyTab::class],
    version = 1
)
abstract class WeatherDb : RoomDatabase() {

    abstract fun placeDao(): PlaceDao

    abstract fun realTimeDao(): RealTimeDao

    abstract fun hourlyTimeDao(): HourlyDao

    abstract fun dailyDao(): DailyDao

    abstract fun alertDao(): AlertDao

    companion object {
        private const val WEATHER_DB_NAME = "weather.db"

        @Volatile
        private var instance: WeatherDb? = null

        fun getInstance(context: Context): WeatherDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): WeatherDb {
            return Room.databaseBuilder(context, WeatherDb::class.java, WEATHER_DB_NAME)
                .build()
        }
    }
}