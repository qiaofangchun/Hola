package com.hola.media.core

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import com.hola.common.utils.Logcat

class MediaBrowserClient(
    context: Context, clazz: Class<*>, rootHints: Bundle? = null
) {
    companion object {
        private const val TAG = "MediaBrowserClient"

        const val CONNECT_SUCCESS = 1
        const val CONNECT_FAILED = 0
        const val CONNECT_REFUSED = -1
    }

    private val mMediaBrowser: MediaBrowserCompat = MediaBrowserCompat(
        context, ComponentName(context, clazz), MediaConnectionCallback(), rootHints
    )

    private var mConnectCallback: ConnectStatusCallback? = null

    val isConnected get() = mMediaBrowser.isConnected

    fun connect(callback: ConnectStatusCallback) {
        this.mConnectCallback = callback
        mMediaBrowser.connect()
    }

    fun disconnect() = mMediaBrowser.disconnect()

    fun subscribeMediaInfo(
        mediaId: String, options: Bundle? = null, callback: MediaBrowserCompat.SubscriptionCallback
    ) = options?.let {
        mMediaBrowser.subscribe(mediaId, it, callback)
    } ?: mMediaBrowser.subscribe(mediaId, callback)

    fun unsubscribeMediaInfo(
        mediaId: String, callback: MediaBrowserCompat.SubscriptionCallback? = null
    ) = callback?.let {
        mMediaBrowser.unsubscribe(mediaId, callback)
    } ?: mMediaBrowser.unsubscribe(mediaId)

    interface ConnectStatusCallback {
        fun onChanged(status: Int, token: MediaSessionCompat.Token? = null)
    }

    private inner class MediaConnectionCallback : MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            Logcat.d(TAG, "[method=onConnected] connect success, root=${mMediaBrowser.root}")
            // Get the token for the MediaSession
            mConnectCallback?.onChanged(CONNECT_SUCCESS, mMediaBrowser.sessionToken)
        }

        override fun onConnectionFailed() {
            // The Service has crashed. Disable transport controls until it automatically reconnects
            Logcat.d(TAG, "[method=onConnected] MediaService connect failed")
            mConnectCallback?.onChanged(CONNECT_FAILED)
        }

        override fun onConnectionSuspended() {
            // The Service has refused our connection
            Logcat.d(TAG, "[method=onConnected] MediaService connect suspended")
            mConnectCallback?.onChanged(CONNECT_REFUSED)
        }
    }
}