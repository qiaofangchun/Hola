package com.hola.app.music.core

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaPlayer.*
import android.os.Build
import java.io.IOException

/**
 * 这个类主要是提供MediaPlayer的各种状态，内部回调
 */
class MediaPlayer(private val mMediaPlayerListener: MediaPlayerListener? = null) {
    //当前播放器状态
    private var mStatus = Status.IDLE
    private val completionListener = OnCompletionListener {
        mStatus = Status.COMPLETED
        mMediaPlayerListener?.omCompleted()
    }
    private val preparedListener = OnPreparedListener {
        mStatus = Status.PREPARED
        mMediaPlayerListener?.onPrepared()
    }
    private val errorListener = OnErrorListener { mp, what, extra ->
        mMediaPlayerListener?.onError()
        true
    }

    val mediaPlayer = MediaPlayer()

    val duration: Long get() = mediaPlayer.duration.toLong()
    val playedTime: Long get() = mediaPlayer.currentPosition.toLong()
    val status: Status get() = mStatus

    init {
        //设置播放完毕后的回调
        mediaPlayer.setOnCompletionListener(completionListener)
        //设置数据准备完毕，准备播放的回调
        mediaPlayer.setOnPreparedListener(preparedListener)
        //设置发生错误事件的回调
        mediaPlayer.setOnErrorListener(errorListener)
    }

    fun loadMedia(url: String, isAsync: Boolean) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(url)
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                .build()
        )
        if (isAsync) {
            mediaPlayer.prepareAsync()
        } else {
            mediaPlayer.prepare()
        }
    }

    //播放器重置：状态设置为IDLE
    fun reset() {
        mediaPlayer.reset()
        mStatus = Status.IDLE
    }

    //设置数据元的回调：状态设置为：INITIALIZED
    @Throws(
        IOException::class,
        IllegalArgumentException::class,
        IllegalStateException::class,
        SecurityException::class
    )
    fun setDataSource(path: String) {
        mediaPlayer.setDataSource(path)
        mStatus = Status.INITIALIZED
    }

    //开始播放状态
    @Throws(IllegalStateException::class)
    fun start() {
        mediaPlayer.start()
        mStatus = Status.STARTED
    }

    //停止播放状态
    @Throws(IllegalStateException::class)
    fun stop() {
        mediaPlayer.stop()
        mStatus = Status.STOPPED
    }

    //暂停状态
    @Throws(IllegalStateException::class)
    fun pause() {
        mediaPlayer.pause()
        mStatus = Status.PAUSED
    }

    @Throws(IllegalStateException::class)
    fun seekTo(msec: Long) {
        mediaPlayer.seekTo(msec.toInt())
    }

    @Throws(IllegalStateException::class)
    fun seekTo(msec: Long, mode: SeekModel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mediaPlayer.seekTo(msec, mode.code)
        } else {
            mediaPlayer.seekTo(msec.toInt())
        }
    }

    fun release() {
        mediaPlayer.release()
    }

    /**
     * 这个Listener用途AudioPlayer的事件回调
     */
    interface MediaPlayerListener {
        fun onPrepared()
        fun omCompleted()
        fun onError()
    }

    enum class SeekModel(val code: Int) {
        SEEK_PREVIOUS_SYNC(0),
        SEEK_NEXT_SYNC(1),
        SEEK_CLOSEST_SYNC(2),
        SEEK_CLOSEST(3)
    }

    //使用枚举类表示当前播放器的各种状态
    enum class Status {
        IDLE, INITIALIZED, PREPARED, STARTED, PAUSED, STOPPED, COMPLETED
    }
}