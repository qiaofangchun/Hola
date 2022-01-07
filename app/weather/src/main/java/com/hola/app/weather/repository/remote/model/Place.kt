package com.hola.app.weather.repository.remote.model

data class Place(
    val id: String,
    val place_id: String,
    val name: String,
    val location: Location,
    val formatted_address: String
)