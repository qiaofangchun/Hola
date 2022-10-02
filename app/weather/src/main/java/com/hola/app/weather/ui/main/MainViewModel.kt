package com.hola.app.weather.ui.main

import com.hola.app.weather.repository.locale.model.PlaceTab
import com.hola.arch.ui.LoadState
import com.hola.arch.ui.MviViewAction
import com.hola.arch.ui.MviViewModel
import com.hola.arch.ui.StateFlowWarp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class MainViewModel : MviViewModel<MainViewAction, MainViewState>() {
    private val mUseCase by lazy { MainUseCase() }
    private var clickNum = 0
    private val searchPlaceState = MutableStateFlow(MainViewState.SearchPlace(LoadState.EMPTY()))
    private val textState = MutableStateFlow(MainViewState.TextState("clickNum=$clickNum"))
    private val toastState = MutableStateFlow(MainViewState.ToastState("Toast clickNum=$clickNum"))

    override fun getNeedCollectFlow(): List<StateFlowWarp<MainViewState>> {
        return listOf(
            StateFlowWarp(searchPlaceState),
            StateFlowWarp(textState),
            StateFlowWarp(toastState)
        )
    }

    override suspend fun handleInput(action: MainViewAction) {
        when (action) {
            is MainViewAction.SearchPlace -> searchPlace(action.place)
            is MainViewAction.Text -> textState.emit(MainViewState.TextState("clickNum=$clickNum"))
            is MainViewAction.Toast -> toastState.emit(MainViewState.ToastState("Toast clickNum=$clickNum"))
        }
    }

    private suspend fun searchPlace(place: String) {
        if (searchPlaceState.value.state == LoadState.LOADING()) return
        mUseCase.update(PlaceTab(isLocation = true))
            .flowOn(Dispatchers.IO)
            .onStart {
                searchPlaceState.emit(MainViewState.SearchPlace(LoadState.LOADING()))
            }.catch { ex ->
                searchPlaceState.emit(
                    MainViewState.SearchPlace(
                        LoadState.FAILURE(
                            ex.message ?: ""
                        )
                    )
                )
            }.collect {
                searchPlaceState.emit(MainViewState.SearchPlace(LoadState.SUCCESS(), "it"))
            }
    }
}