package com.hola.app.weather.repository.remote

import com.hola.app.weather.repository.remote.dao.ApiService
import com.hola.network.BaseNetwork

object WeatherNet : BaseNetwork<ApiService>(ApiService.WEATHER_BASE_URL)