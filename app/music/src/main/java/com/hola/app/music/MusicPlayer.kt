package com.hola.app.music

import com.hola.common.utils.Logcat

class MusicPlayer {

    private var url: String = ""

    init {
        native_init()
    }

    // called from jni
    private fun onError(code: Int, msg: String) {
        Logcat.w(TAG, "errorCode=$code,msg=$msg")
    }

    // called from jni
    private fun onPrepared() {
        Logcat.w(TAG, "onPrepared")
        play()
    }

    // Called from jni
    private fun onLoading(loading: Boolean) {
    }

    // Called from jni
    private fun onProgress(current: Int, total: Int) {
    }

    // Called from jni
    private fun onComplete() {
    }

    // Called from jni
    private fun onRenderYUV420P(width: Int, height: Int, y: ByteArray, u: ByteArray, v: ByteArray) {
    }

    // Called from jni
    private fun decodePacket(dataSize: Int, data: ByteArray) {

    }

    private fun isSupportStiffCodec(codeName: String): Boolean {
        return false
    }

    private fun initMediaCodec(
        codecName: String,
        width: Int,
        height: Int,
        csd0: ByteArray,
        csd1: ByteArray
    ) {

    }

    fun prepare() {
        native_prepare(url)
    }

    fun setDataSource(path: String) {
        this.url = path
    }

    fun play() = native_play()

    fun pause() = native_pause()

    fun stop() = native_stop()

    fun seekTo(msec: Long) = native_seek(msec)

    fun release() = native_deinit()

    private external fun native_init()

    private external fun native_prepare(path: String)

    private external fun native_play()

    private external fun native_stop()

    private external fun native_pause()

    private external fun native_seek(msec: Long)

    private external fun native_deinit()

    private companion object {
        private const val TAG = "MusicPlayer"

        init {
            System.loadLibrary("MediaPlayer")
        }
    }
}