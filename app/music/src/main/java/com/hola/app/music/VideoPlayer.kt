package com.hola.app.music

import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView

class VideoPlayer(private val surfaceView: SurfaceView) {
    private val surfaceHolder = surfaceView.holder
    private val callback = object : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            surfaceView.holder
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {

        }
    }

    init {
        surfaceHolder.removeCallback(callback)
        surfaceHolder.addCallback(callback)
    }

    fun start(filePath: String) {
        native_start(surfaceHolder.surface, filePath)
    }

    fun getVersion() = native_version()

    private external fun native_start(surface: Surface, filePath: String)

    private external fun native_version(): String

    private companion object {
        init {
            System.loadLibrary("player")
        }
    }
}