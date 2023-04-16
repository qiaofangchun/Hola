package com.hola.app.music

import android.support.v4.media.MediaBrowserCompat
import com.hola.arch.core.ViewModel
import com.hola.media.core.MediaControllerHelper
import com.hola.media.core.MediaDataSubscribeCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart


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
        mUseCase.getMediaData(mediaId).flowOn(Dispatchers.IO)
            .catch { ex ->
                output(MainViewState.MediaData(emptyList()))
            }.collect {
                output(MainViewState.MediaData(it))
            }
    }
}