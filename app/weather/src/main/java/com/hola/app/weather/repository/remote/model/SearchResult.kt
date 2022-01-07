package com.hola.app.weather.repository.remote.model

data class SearchResult(val places: List<Place>) : ApiResult()