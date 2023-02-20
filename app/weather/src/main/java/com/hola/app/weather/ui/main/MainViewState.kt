package com.hola.app.weather.ui.main

import com.hola.app.weather.ui.LoadState
import com.hola.arch.core.ViewState

sealed class MainViewState(var state: LoadState, var data: String?) : ViewState() {
    class GetWeather(state: LoadState, data: String? = null) : MainViewState(state, data)
}