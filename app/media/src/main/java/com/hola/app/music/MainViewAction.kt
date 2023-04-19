package com.hola.app.music

import android.os.Bundle
import com.hola.arch.core.ViewIntent

sealed class MainViewAction : ViewIntent() {
    class SubscribeMediaData(val mediaId: String, val options: Bundle? = null) : MainViewAction()
}