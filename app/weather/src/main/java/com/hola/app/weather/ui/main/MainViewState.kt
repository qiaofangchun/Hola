package com.hola.app.weather.ui.main

import com.hola.arch.ui.MviViewState
import com.hola.arch.ui.LoadState

sealed class MainViewState(state: LoadState, data: String?) : MviViewState<String>(state, data) {
    class SearchPlace(state: LoadState, data: String? = null) : MainViewState(state, data)
    class TextState(data: String) : MainViewState(LoadState.EMPTY(), data)
    class ToastState(data: String) : MainViewState(LoadState.EMPTY(), data)
}