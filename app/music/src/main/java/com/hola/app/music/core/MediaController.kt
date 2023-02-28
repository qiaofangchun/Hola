package com.hola.app.music.core

import android.content.Context
import android.net.wifi.WifiManager
import java.io.IOException
import java.util.*

class MediaController(private val mContext: Context, private val mMediaProvider: MediaProvider) {
    companion object {
        private const val TAG = "MediaController"
        private const val OPERATE_CODE_NEXT = 1
        private const val OPERATE_CODE_PREV = -1
    }

    private var mQueueIndex = 0
    private val mMediaPlayer = MediaPlayer(object : MediaPlayer.MediaPlayerListener {
        override fun onPrepared() {
            play()
        }

        override fun omCompleted() {
            next()
        }

        override fun onError() {

        }
    })
    private var mAudioFocusManager: AudioFocusManager? = null
    private val mWifiLock by lazy {
        val context = mContext.applicationContext
        val manager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        //设置WifiLock，可以让在后台也可以运行wifi下载加载数据
        manager.createWifiLock(WifiManager.WIFI_MODE_FULL_HIGH_PERF, TAG)
    }

    var playMode = PlayMode.LOOP
    val duration: Long get() = mMediaPlayer.duration
    val playedTime: Long get() = mMediaPlayer.playedTime
    val playMedia: MediaMetaData? get() = mMediaProvider.getMediaMetaData(mQueueIndex)

    fun getMediaPlayer() = mMediaPlayer

    fun setAudioFocusManager(audioFocusManager: AudioFocusManager) {
        this.mAudioFocusManager = audioFocusManager
    }

    fun play() {
        when (mMediaPlayer.status) {
            MediaPlayer.Status.IDLE,
            MediaPlayer.Status.INITIALIZED -> getPlayMedia(mQueueIndex)?.let { loadMedia(it) }
            else -> {
                mMediaPlayer.start()
                mWifiLock.acquire()
            }
        }
    }

    fun seekTo(msec: Long, mode: MediaPlayer.SeekModel = MediaPlayer.SeekModel.SEEK_PREVIOUS_SYNC) {
        mMediaPlayer.seekTo(msec, mode)
    }

    fun pause() {
        val status = mMediaPlayer.status
        if (MediaPlayer.Status.STARTED != status) return
        mMediaPlayer.pause()
        //关注wifi锁
        if (mWifiLock.isHeld) {
            mWifiLock.release()
        }
        mAudioFocusManager?.abandonAudioFocus()
    }

    fun stop() {
        val status = mMediaPlayer.status
        if ((MediaPlayer.Status.STARTED != status) && (MediaPlayer.Status.PAUSED != status)) return
        mMediaPlayer.stop()
        if (mWifiLock.isHeld) {
            mWifiLock.release()
        }
        mAudioFocusManager?.abandonAudioFocus()
    }

    fun release() {
        mMediaPlayer.release()
        // 关闭wifi锁
        if (mWifiLock.isHeld) {
            mWifiLock.release()
        }
        mAudioFocusManager?.abandonAudioFocus()
    }

    fun next() {
        mQueueIndex = getPlayIndex(OPERATE_CODE_NEXT)
        getPlayMedia(mQueueIndex)?.let { loadMedia(it) }
    }

    fun prev() {
        mQueueIndex = getPlayIndex(OPERATE_CODE_PREV)
        getPlayMedia(mQueueIndex)?.let { loadMedia(it) }
    }

    private fun getPlayIndex(code: Int): Int {
        val queueSize = mMediaProvider.dataSize
        return when (playMode) {
            PlayMode.LOOP -> (mQueueIndex + queueSize + code) % queueSize
            PlayMode.RANDOM -> Random().nextInt(queueSize) % queueSize
            PlayMode.REPEAT -> mQueueIndex
        }
    }

    private fun getPlayMedia(index: Int) = mMediaProvider.getMediaMetaData(index)

    /**
     * 对外提供的加载音频的方法
     */
    private fun loadMedia(media: MediaMetaData) {
        try {
            mMediaPlayer.loadMedia(media.url, true)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    enum class PlayMode {
        /**
         * 列表循环
         */
        LOOP,
        /**
         * 随机
         */
        RANDOM,
        /**
         * 单曲循环
         */
        REPEAT
    }
}