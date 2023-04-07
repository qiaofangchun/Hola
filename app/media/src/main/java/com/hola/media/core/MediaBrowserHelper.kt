package com.hola.media.core

import android.content.ComponentName
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.hola.common.utils.AppHelper
import com.hola.common.utils.Logcat

object MediaBrowserHelper {
    private const val TAG = "MediaBrowserHelper"

    private var mController: MediaControllerCompat? = null
    private val mContext = AppHelper.context
    private val mMediaBrowser = MediaBrowserCompat(
        mContext,
        ComponentName(mContext, MediaBrowserService::class.java),
        MediaConnectionCallback(), null
    )

    val mediaController: MediaControllerCompat? get() = mController

    fun connect() = mMediaBrowser.connect()

    fun disconnect() = mMediaBrowser.disconnect()

    private class MediaConnectionCallback : MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            Logcat.d(TAG, "[method=onConnected] MediaService connect success")
            // Get the token for the MediaSession
            mController = MediaControllerCompat(mContext, mMediaBrowser.sessionToken).apply {
                registerCallback(MediaControllerCallback())
            }
        }

        override fun onConnectionFailed() {
            // The Service has crashed. Disable transport controls until it automatically reconnects
            Logcat.d(TAG, "[method=onConnected] MediaService connect failed")
        }

        override fun onConnectionSuspended() {
            // The Service has refused our connection
            Logcat.d(TAG, "[method=onConnected] MediaService connect suspended")
        }
    }

    private class MediaControllerCallback : MediaControllerCompat.Callback() {

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            Logcat.d(TAG, "[method=onMetadataChanged] Metadata changed")
        }

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            Logcat.d(TAG, "[method=onPlaybackStateChanged] Play state changed")
        }
    }
}