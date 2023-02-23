package com.hola.app.weather.ui.main

import com.hola.app.weather.ui.LoadState
import com.hola.arch.core.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class MainViewModel : ViewModel<MainViewAction, MainViewState>() {
    private val mUseCase by lazy { MainUseCase() }

    override fun initUiState() {
        addUiState(MainViewState.GetWeather(LoadState.EMPTY()))
    }

    override suspend fun onInput(action: MainViewAction) {
        when (action) {
            is MainViewAction.SearchPlace -> getWeather(action.place)
        }
    }

    private suspend fun getWeather(place: String) {
        mUseCase.getWeather()
            .flowOn(Dispatchers.IO)
            .onStart {
                output(MainViewState.GetWeather(LoadState.LOADING()))
            }.catch { ex ->
                output(MainViewState.GetWeather(LoadState.FAILURE(ex.message ?: "")))
            }.collect {
                output(MainViewState.GetWeather(LoadState.SUCCESS(), "Get Success"))
            }
    }
}