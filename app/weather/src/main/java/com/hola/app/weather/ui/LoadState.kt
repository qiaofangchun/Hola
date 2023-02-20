package com.hola.app.weather.ui

sealed class LoadState(val message: String) {
    class LOADING(msg: String = "") : LoadState(msg)
    class SUCCESS(msg: String = "") : LoadState(msg)
    class FAILURE(msg: String = "") : LoadState(msg)
    class EMPTY(msg: String = "") : LoadState(msg)
}