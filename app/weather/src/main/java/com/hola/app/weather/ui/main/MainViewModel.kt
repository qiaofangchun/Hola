package com.hola.app.weather.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.hola.app.weather.repository.locale.model.PlaceTab
import com.hola.arch.ui.LoadState
import com.hola.arch.ui.MviViewAction
import com.hola.arch.ui.MviViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class MainViewModel : MviViewModel() {
    private val mUseCase by lazy { MainUseCase() }
    private val searchPlaceState = MainViewState.SearchPlace(LoadState.EMPTY())

    override suspend fun handleAction(action: MviViewAction) {
        when (action) {
            is MainViewAction.SearchPlace -> searchPlace(action.place)
        }
    }

    private suspend fun searchPlace(place: String) {
        mUseCase.update(PlaceTab(isLocation = true))
            .flowOn(Dispatchers.IO)
            .onStart {
                sendResult(searchPlaceState.apply { state = LoadState.LOADING() })
            }
            .catch { ex ->
                sendResult(searchPlaceState.apply { state = LoadState.FAILURE(ex.toString()) })
            }.collect {
                sendResult(searchPlaceState.apply {
                    state = LoadState.SUCCESS()
                    data = "$it"
                })
            }
    }

    override fun onCleared() {
        Log.d("qfc", "onCleared----->")
        super.onCleared()
        val a = 0
        when (a) {
            0 -> ""
            1 -> "1"
            2 -> "2"
        }
    }
}