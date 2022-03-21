package com.hola.app.weather.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hola.app.weather.repository.locale.model.PlaceTab

class MainViewModel : ViewModel() {
    private val mUseCase by lazy { MainUseCase(viewModelScope) }

    fun searchPlace() {
        //mUseCase.update(PlaceTab(isLocation = true))
        mUseCase.deletePlace()
    }
}