package com.hola.app.music

class MusicPlayer {
    fun setDataSource(path: String) {
        n_start(path)
    }

    fun start() {

    }

    fun pause() {

    }

    fun stop() {

    }

    fun release() {

    }

    private external fun n_start(path: String)

    private companion object {
        init {
            System.loadLibrary("player")
        }
    }
}