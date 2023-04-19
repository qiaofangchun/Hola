package com.hola.app.music

import com.hola.arch.core.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

class MainViewModel : ViewModel<MainViewAction, MainViewState>() {
    private val mUseCase by lazy { MainUseCase() }

    override fun initUiState() {
        addUiState(MainViewState.MediaData(emptyList()))
    }

    override suspend fun onInput(intent: MainViewAction) {
        when (intent) {
            is MainViewAction.SubscribeMediaData -> getMediaData(intent.mediaId)
        }
    }

    private suspend fun getMediaData(mediaId: String) {
        mUseCase.getMediaData(mediaId)

        mUseCase.getMediaData(mediaId).flowOn(Dispatchers.IO)
            .catch { ex ->
                output(MainViewState.MediaData(emptyList()))
            }.collect {
                output(MainViewState.MediaData(it))
            }
    }
}