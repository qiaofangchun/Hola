package com.hola.app.weather.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.hola.app.weather.repository.WeatherUseCase

class MainViewModel : ViewModel() {
    private val mUseCase by lazy { MainUseCase(viewModelScope) }

    fun searchPlace() {
        mUseCase.update()
    }
}