package com.hola.app.weather.ui.main

import com.hola.arch.ui.MviViewAction

sealed class MainViewAction : MviViewAction() {
    class SearchPlace(val place: String) : MainViewAction()
}