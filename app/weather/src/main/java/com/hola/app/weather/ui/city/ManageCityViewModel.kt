package com.hola.app.weather.ui.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.hola.app.weather.repository.WeatherUseCase
import com.hola.app.weather.repository.locale.model.PlaceTab

class ManageCityViewModel : ViewModel() {
    private val mUseCase by lazy { ManageCityUseCase(viewModelScope) }

    fun searchPlace(placeName: String) {
        mUseCase.searchPlace(placeName)
    }

    fun addPlace(lat: Double, lng: Double, name: String) {
        mUseCase.addPlace(PlaceTab(lat = lat, lng = lng, name = name))
    }
}