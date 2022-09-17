package com.hola.app.weather.ui.main

import com.hola.arch.ui.MviViewState
import com.hola.arch.ui.LoadState

sealed class MainViewState(state: LoadState, data: String?) : MviViewState<String>(state, data) {
    class SearchPlace(state: LoadState, data: String? = null) : MainViewState(state, data)
}