package com.hola.app.weather.repository.remote.model

data class Weather(
    val realtime: Now,
    val daily: Daily,
    val hourly: Hourly,
    val primary: Int,
    val alert: Alerts,
    val forecast_keypoint: String
) {
    data class Alerts(
        val content: List<Alert>,
        val status: String
    )
}

