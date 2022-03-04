package com.hola.app.weather.location

data class Location(
    val lat: Double,
    val lng: Double,
    val province: String,
    val city: String,
    val district: String,
    val street: String,
    val address: String
)