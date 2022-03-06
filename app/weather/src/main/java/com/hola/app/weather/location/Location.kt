package com.hola.app.weather.location

data class Location(
    val lat: Double,
    val lng: Double,
    val province: String? = null,
    val city: String? = null,
    val district: String? = null,
    val street: String? = null,
    val address: String? = null
)