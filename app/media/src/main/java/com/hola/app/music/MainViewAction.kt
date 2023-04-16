package com.hola.app.music

import com.hola.arch.core.ViewIntent

sealed class MainViewAction : ViewIntent() {
    class SubscribeMediaData(val mediaId: String) : MainViewAction()
}