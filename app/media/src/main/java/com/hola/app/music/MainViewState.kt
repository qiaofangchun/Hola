package com.hola.app.music

import android.support.v4.media.MediaBrowserCompat
import com.hola.arch.core.ViewState

sealed class MainViewState(var state: String?, var data: String?) : ViewState() {
    class MediaData(val mediaData: List<MediaBrowserCompat.MediaItem>) : MainViewState(null, null)
}