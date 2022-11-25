package com.hola.app.music

class MusicPlayer {
    init {
        native_init()
    }

    fun prepare() {
        native_prepare()
    }

    fun setDataSource(path: String) {
        native_set_path(path)
    }

    fun play() {
        native_play()
    }

    fun pause() {
        native_pause()
    }

    fun stop() {
        native_stop()
    }

    fun seekTo(msec: Long) {
        native_seek(msec)
    }

    fun release() {
        native_deinit()
    }

    private external fun native_init()

    private external fun native_prepare()

    private external fun native_set_path(path: String)

    private external fun native_play()

    private external fun native_stop()

    private external fun native_pause()

    private external fun native_seek(msec: Long)

    private external fun native_deinit()

    private companion object {
        init {
            System.loadLibrary("player")
        }
    }
}