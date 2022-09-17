package com.hola.app.weather.ui.main

import android.util.Log
import com.hola.app.weather.location.LocationException
import com.hola.app.weather.repository.WeatherRepository
import com.hola.app.weather.repository.WeatherUseCase
import com.hola.app.weather.repository.locale.model.PlaceTab
import com.hola.app.weather.repository.safe
import com.hola.location.annotation.LocationCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainUseCase : WeatherUseCase() {
    companion object {
        private const val TAG = "MainUseCase"
    }

    fun getWeather() {

    }

    fun update(placeTab: PlaceTab) = WeatherRepository.getLocation().flatMapConcat { it ->
        Log.d(TAG, "location---->$it")
        StringBuilder().append(it.province.safe())
            .append(it.city.safe())
            .append(it.district.safe())
            .toString().takeIf { it.isNotEmpty() }?.let {
                flowOf("北京")
            }
            ?: throw LocationException(LocationCode.FAILURE, "city code parser failure")
    }.flatMapConcat {
        Log.d(TAG, "flatMapConcat---->$it")
        WeatherRepository.searchPlace(it)
    }.flatMapConcat {
        Log.d(TAG, "flatMapConcat2---->$it")
        val result = it.places[0]
        val loc = result.location
        WeatherRepository.updateWeatherByPlace(
            PlaceTab(
                loc.lat,
                loc.lng,
                result.name,
                isLocation = true
            )
        )
        flowOf(true)
    }

}