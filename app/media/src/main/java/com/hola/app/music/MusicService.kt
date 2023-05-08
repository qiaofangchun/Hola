package com.hola.app.music

import com.hola.media.core.MediaBrowserService

class MusicService(
    override val channelId: String = "Hola_Music",
    override val startForeground: Int = -1
) : MediaBrowserService()