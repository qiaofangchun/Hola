package com.hola.app.weather.repository.remote.dao

import com.hola.app.weather.repository.remote.model.SearchResult
import com.hola.app.weather.repository.remote.model.WeatherResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    /**
     * 通过地方名搜索城市
     */
    @GET("v2/place?token=$WEATHER_TOKEN")
    suspend fun searchPlace(
        @Query("query") placeName: String,
        @Query("lang") lang: String
    ): SearchResult

    /**
     * 通过城市Code获取天气信息
     */
    @GET("v2.5/$WEATHER_TOKEN/weather.json?$WEATHER_STATIC_PARAM")
    suspend fun getWeatherByAdCode(
        @Query("adcode") adCode: String,
        @Query("lang") lang: String
    ): WeatherResult

    /**
     * 通过经纬度Code获取天气信息
     */
    @GET("v2.5/$WEATHER_TOKEN/{lng},{lat}/weather.json?$WEATHER_STATIC_PARAM")
    suspend fun getWeatherByLocation(
        @Path("lng") lng: String,
        @Path("lat") lat: String,
        @Query("lang") lang: String
    ): WeatherResult

    companion object {
        /**
         * 彩云天气api访问Token
         */
        const val WEATHER_TOKEN = "FYYORZSlNVBnBx20"

        /**
         * 彩云天气域名
         */
        const val WEATHER_BASE_URL = "https://api.caiyunapp.com/"

        /**
         * 未来多少天数据
         */
        private const val WEATHER_DAILY_STEP = 7

        /**
         * 未来几小时数据
         */
        private const val WEATHER_HOURLY_STEP = 24

        /**
         * 是否需要预警信息
         */
        private const val WEATHER_ALERT_INFO = true

        /**
         * 天气固定参数
         */
        const val WEATHER_STATIC_PARAM =
            "dailysteps=$WEATHER_DAILY_STEP&hourlysteps=$WEATHER_HOURLY_STEP&alert=$WEATHER_ALERT_INFO"
    }
}