package com.hola.app.weather.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class MainViewModel : ViewModel() {
    private val mUseCase by lazy { MainUseCase(viewModelScope) }

    fun searchPlace() {
        mUseCase.update()
    }

    fun getLocation(){
        mUseCase.location()
    }
}