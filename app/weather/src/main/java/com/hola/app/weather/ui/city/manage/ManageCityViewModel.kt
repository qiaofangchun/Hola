package com.hola.app.weather.ui.city.manage

import androidx.lifecycle.ViewModel
import com.hola.app.weather.repository.locale.model.PlaceTab

class ManageCityViewModel : ViewModel() {
    private val mUseCase by lazy { ManageCityUseCase() }

    fun getPlaces(){
        mUseCase.getPlaces()
    }

    fun searchPlace(placeName: String) {
        mUseCase.searchPlace(placeName)
    }

    fun addPlace(lat: Double, lng: Double, name: String) {
        mUseCase.addPlace(PlaceTab(lat = lat, lng = lng, name = name))
    }
}