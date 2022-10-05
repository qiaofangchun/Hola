package com.hola.app.weather.ui.main

import com.hola.app.weather.repository.locale.model.PlaceTab
import com.hola.arch.ui.LoadState
import com.hola.arch.ui.MviViewModel
import com.hola.arch.ui.StateFlowCreator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class MainViewModel : MviViewModel<MainViewAction, MainViewState>() {
    private val mUseCase by lazy { MainUseCase() }
    private var clickNum = 0

    override fun configStateFlow(): List<StateFlowCreator<MainViewState>> = listOf(
        StateFlowCreator(MainViewState.SearchPlace(LoadState.EMPTY())),
        StateFlowCreator(MainViewState.TextState("clickNum=$clickNum")),
        StateFlowCreator(MainViewState.ToastState("Toast clickNum=$clickNum"))
    )

    override suspend fun handleInput(action: MainViewAction) {
        clickNum++
        when (action) {
            is MainViewAction.SearchPlace -> searchPlace(action.place)
            is MainViewAction.Text -> output(MainViewState.TextState("clickNum=$clickNum"))
            is MainViewAction.Toast -> output(MainViewState.ToastState("Toast clickNum=$clickNum"))
        }
    }

    private suspend fun searchPlace(place: String) {
        mUseCase.update(PlaceTab(isLocation = true))
            .flowOn(Dispatchers.IO)
            .onStart {
                output(MainViewState.SearchPlace(LoadState.LOADING()))
            }.catch { ex ->
                output(MainViewState.SearchPlace(LoadState.FAILURE(ex.message ?: "")))
            }.collect {
                output(MainViewState.SearchPlace(LoadState.SUCCESS(), "it"))
            }
    }
}