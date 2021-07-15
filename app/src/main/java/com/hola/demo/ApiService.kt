package com.hola.demo

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("https://www.baidu.com")
    suspend fun movies(): String

    companion object {
        private const val PARAM_MOVIE_ID = "movieId"
        private const val MOVIES = "movies.json"
        private const val MOVIE_DETAILS = "movie_0{$PARAM_MOVIE_ID}.json"
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture-Kotlin/")
                .client(OkHttpClient.Builder().build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        private val apiService by lazy { retrofit.create(ApiService::class.java) }

        fun get(): ApiService = apiService
    }
}