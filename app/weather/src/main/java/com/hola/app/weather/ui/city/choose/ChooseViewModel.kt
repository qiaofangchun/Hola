package com.hola.app.weather.ui.city.choose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.hola.app.weather.repository.WeatherUseCase

class ChooseViewModel : ViewModel() {
    private val mUseCase by lazy { WeatherUseCase(viewModelScope) }

    fun searchPlace(placeName: String, lang: String) {
        //mUseCase.searchPlace(placeName, lang)
    }
}