package com.hola.app.music

import com.hola.base.application.BaseApplication
import com.hola.media.core.MediaControllerHelper

class MediaApplication : BaseApplication() {

    override fun getAppName() = "MediaPlayer"

    override fun onCreate() {
        super.onCreate()
        MediaControllerHelper.init(this, MusicService::class.java)
        MediaControllerHelper.connectService()
    }
}