package com.hola.app.weather.ui.city.manage

import android.util.Log
import com.hola.app.weather.repository.WeatherRepository
import com.hola.app.weather.repository.WeatherUseCase
import com.hola.app.weather.repository.locale.model.PlaceTab
import kotlinx.coroutines.CoroutineScope

class ManageCityUseCase(scope: CoroutineScope) : WeatherUseCase(scope) {
    companion object {
        private const val TAG = "ManageCityUseCase"
    }

    fun getPlaces(){
        doRequest {
            WeatherRepository.getPlaces()
        }.onStart {
            // todo start UI state
            Log.d(TAG, "search onStart---->")
        }.onSuccess {
            // todo end UI state
            Log.d(TAG, "search onSuccess---->$it")
        }.onFailure {
            // todo show error msg
            Log.d(TAG, "search onFailure---->${it.message}")
        }.execute()
    }

    fun searchPlace(address:String){
        doRequest {
            WeatherRepository.searchPlace(address)
        }.onStart {
            // todo start UI state
            Log.d(TAG, "search onStart---->")
        }.onSuccess {
            // todo end UI state
            Log.d(TAG, "search onSuccess---->$it")
        }.onFailure {
            // todo show error msg
            Log.d(TAG, "search onFailure---->${it.message}")
        }.execute()
    }

    fun addPlace(place: PlaceTab) {
        doRequest {
            WeatherRepository.insertPlace(place)
        }.onStart {
            // todo start UI state
            Log.d(TAG, "add onStart---->")
        }.onSuccess {
            // todo end UI state
            Log.d(TAG, "add onSuccess---->$it")
        }.onFailure {
            // todo show error msg
            Log.d(TAG, "add onFailure---->${it.message}")
        }.execute()
    }

    fun deletePlace(places: List<PlaceTab>) {
        doRequest {
            WeatherRepository.deletePlaces(places)
        }.onStart {
            // todo start UI state
            Log.d(TAG, "delete onStart---->")
        }.onSuccess {
            // todo end UI state
            Log.d(TAG, "delete onSuccess---->$it")
        }.onFailure {
            // todo show error msg
            Log.d(TAG, "delete onFailure---->${it.message}")
        }.execute()
    }
}