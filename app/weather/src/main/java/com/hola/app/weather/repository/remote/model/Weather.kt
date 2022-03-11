package com.hola.app.weather.repository.remote.model

data class Weather(
    val realtime: Realtime,
    val daily: Daily,
    val hourly: Hourly,
    val alert: Alerts,
) {
    data class Alerts(
        val content: List<Alert>,
        val status: String
    )
}

