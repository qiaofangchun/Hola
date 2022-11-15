/*
package com.hola.app.music

import android.graphics.PixelFormat
import android.view.Surface
import android.view.SurfaceView

class VideoPlayer(private val surfaceView: SurfaceView) {
    fun start(filePath: String) {
        //子线程进行视频渲染
        val holder = surfaceView.holder
        holder.setFormat(PixelFormat.RGBA_8888)
        native_start(surfaceView.holder.surface, filePath)
    }

    fun getVersion() = native_version()

    private external fun native_start(surface: Surface, filePath: String)

    private external fun native_version(): String

    private companion object {
        init {
            System.loadLibrary("player")
        }
    }
}*/
