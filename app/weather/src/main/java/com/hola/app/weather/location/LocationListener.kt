package com.hola.app.weather.location

interface LocationListener {
    fun onSuccess(loc: Location)
    fun onFailure(exception: Exception)
}