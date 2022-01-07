package com.hola.app.weather.repository.remote

import com.hola.app.weather.repository.remote.dao.ApiService
import com.hola.network.BaseNetworkApi

object WeatherNet : BaseNetworkApi<ApiService>(ApiService.WEATHER_BASE_URL)