package com.hola.app.weather.widget.weather

import androidx.annotation.IntDef

@IntDef(
    WeatherType.DEFAULT,
    WeatherType.CLEAR_D,
    WeatherType.CLEAR_N,
    WeatherType.RAIN_D,
    WeatherType.RAIN_N,
    WeatherType.SNOW_D,
    WeatherType.SNOW_N,
    WeatherType.CLOUDY_D,
    WeatherType.CLOUDY_N,
    WeatherType.OVERCAST_D,
    WeatherType.OVERCAST_N,
    WeatherType.FOG_D,
    WeatherType.FOG_N,
    WeatherType.HAZE_D,
    WeatherType.HAZE_N,
    WeatherType.SAND_D,
    WeatherType.SAND_N,
    WeatherType.WIND_D,
    WeatherType.WIND_N,
    WeatherType.RAIN_SNOW_D,
    WeatherType.RAIN_SNOW_N,
    WeatherType.UNKNOWN_D,
    WeatherType.UNKNOWN_N
)
@Retention(AnnotationRetention.SOURCE)
annotation class WeatherType{
    companion object{
        const val DEFAULT = 0
        const val CLEAR_D = 1
        const val CLEAR_N = -1
        const val RAIN_D = 2
        const val RAIN_N = -2
        const val SNOW_D = 3
        const val SNOW_N = -3
        const val CLOUDY_D = 4
        const val CLOUDY_N = -4
        const val OVERCAST_D = 5
        const val OVERCAST_N = -5
        const val FOG_D = 6
        const val FOG_N = -6
        const val HAZE_D = 7
        const val HAZE_N = -7
        const val SAND_D = 8
        const val SAND_N = -8
        const val WIND_D = 9
        const val WIND_N = -9
        const val RAIN_SNOW_D = 10
        const val RAIN_SNOW_N = -10
        const val UNKNOWN_D = 99
        const val UNKNOWN_N = -99
    }
}