package com.hola.app.weather.repository.remote.model

data class Alert(
    val adcode: String,
    val alertId: String,
    val city: String,
    val code: String,
    val county: String,
    val description: String,
    val latlon: List<Double>,
    val location: String,
    val province: String,
    val pubtimestamp: Int,
    val regionId: String,
    val request_status: String,
    val source: String,
    val status: String,
    val title: String
)