package com.hola.app.weather.ui.main

import com.hola.arch.core.ViewIntent

sealed class MainViewAction : ViewIntent() {
    class SearchPlace(val place: String) : MainViewAction()
}