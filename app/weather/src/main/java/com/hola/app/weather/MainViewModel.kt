package com.hola.app.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hola.app.weather.repository.WeatherUseCase

class MainViewModel : ViewModel() {
    private val mUseCase by lazy { WeatherUseCase(viewModelScope) }

    fun searchPlace(
        placeName: String,
        lang: String
    ) {
        mUseCase.searchPlace(placeName, lang)
    }
}