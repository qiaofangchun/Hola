package com.hola.app.weather.repository.remote.model

data class Wind(
    val speed: Double,
    val direction: Double,
    val datetime: String,
)