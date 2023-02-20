package com.hola.app.weather.ui.main

import com.hola.app.weather.location.LocationException
import com.hola.app.weather.repository.WeatherRepository
import com.hola.app.weather.repository.WeatherUseCase
import com.hola.app.weather.repository.locale.model.PlaceTab
import com.hola.app.weather.repository.safe
import com.hola.common.utils.Logcat
import com.hola.location.annotation.LocationCode
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf

class MainUseCase : WeatherUseCase() {
    companion object {
        private const val TAG = "MainUseCase"
    }

    fun getWeather() = WeatherRepository.getLocation().flatMapConcat { it ->
        Logcat.d(TAG, "location---->$it")
        StringBuilder().append(it.province.safe())
            .append(it.city.safe())
            .append(it.district.safe())
            .toString().takeIf { it.isNotEmpty() }?.let {
                flowOf(it)
            }
            ?: throw LocationException(LocationCode.FAILURE, "city code parser failure")
    }.flatMapConcat {
        Logcat.d(TAG, "flatMapConcat---->$it")
        WeatherRepository.searchPlace(it)
    }.flatMapConcat {
        Logcat.d(TAG, "flatMapConcat2---->$it")
        val result = it.places[0]
        val loc = result.location
        WeatherRepository.updateWeatherByPlace(
            PlaceTab(loc.lat, loc.lng, result.name, isLocation = true)
        )
        flowOf(true)
    }

}