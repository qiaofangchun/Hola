package com.hola.app.music.core

import android.content.Context
import android.media.AudioManager

class AudioFocusManager(private val mContext: Context) : AudioManager.OnAudioFocusChangeListener {
    companion object {
        private const val TAG = "AudioFocusManager"
    }

    private val audioManager by lazy { mContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    private var mAudioFocusListener: AudioFocusListener? = null

    fun setAudioFocusListener(listener: AudioFocusListener) {
        mAudioFocusListener = listener
    }

    fun requestAudioFocus(): Boolean {
        return audioManager.requestAudioFocus(
            this, AudioManager.STREAM_MUSIC,
            AudioManager.AUDIOFOCUS_GAIN
        ) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
    }

    fun abandonAudioFocus() {
        audioManager.abandonAudioFocus(this)
    }

    override fun onAudioFocusChange(focusChange: Int) {
        when (focusChange) {
            AudioManager.AUDIOFOCUS_GAIN -> mAudioFocusListener?.audioFocusGrant()
            AudioManager.AUDIOFOCUS_LOSS -> mAudioFocusListener?.audioFocusLoss()
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> mAudioFocusListener?.audioFocusLossTransient()
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> mAudioFocusListener?.audioFocusLossDuck()
        }
    }

    /**
     * 音频焦点改变,接口回调，
     */
    interface AudioFocusListener {
        //获得焦点回调处理
        fun audioFocusGrant()

        //永久失去焦点回调处理
        fun audioFocusLoss()

        //短暂失去焦点回调处理
        fun audioFocusLossTransient()

        //瞬间失去焦点回调
        fun audioFocusLossDuck()
    }
}