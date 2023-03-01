package com.hola.media.music

import com.hola.media.core.MediaService

class MusicService : MediaService() {
    companion object {
        private const val TAG = "MusicService"
    }

    protected override fun getTag() = TAG

    protected override fun getMediaType() = 1

}