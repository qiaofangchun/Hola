package com.hola.media.core

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.SubscriptionCallback
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.MediaBrowserServiceCompat
import com.hola.common.utils.AppHelper
import com.hola.common.utils.Logcat
import kotlinx.coroutines.*

object MediaControllerHelper {
    private const val TAG = "MediaBrowserHelper"
    private const val MAX_RETRY_NUM = 3
    private const val START_RETRY_NUM = 1

    private var mRetryNum = START_RETRY_NUM
    private var mController: MediaControllerCompat? = null
    private val mMediaBrowserClient = MediaBrowserClient(AppHelper.context)

    fun connectService() = realConnectService {}

    private fun realConnectService(callback: () -> Unit) {
        if (isConnectedService) {
            callback.invoke()
            return
        }
        mMediaBrowserClient.connect(object : MediaBrowserClient.ConnectStatusCallback {
            override fun onChanged(status: Int, token: MediaSessionCompat.Token?) {
                if (status != MediaBrowserClient.CONNECT_SUCCESS) {
                    if (mRetryNum <= MAX_RETRY_NUM) {
                        Logcat.d(TAG, "[method=connectService] retry number = $mRetryNum")
                        mRetryNum++
                        realConnectService(callback)
                    } else {
                        mRetryNum = START_RETRY_NUM
                    }
                    return
                }
                mRetryNum = START_RETRY_NUM
                mController = token?.let { MediaControllerCompat(AppHelper.context, it) }
                mController?.registerCallback(MediaControllerCallback())
                callback.invoke()
            }
        })
    }

    val playState get() = mController?.playbackState?.state ?: PlaybackStateCompat.STATE_NONE

    val isConnectedService get() = mMediaBrowserClient.isConnected

    fun disconnectService() = mMediaBrowserClient.disconnect()

    fun subscribeMediaData(
        mediaId: String,
        options: Bundle? = null,
        callback: SubscriptionCallback
    ) {
        Logcat.d(TAG,"[method=subscribeMediaData] isConnectedService=$isConnectedService")
        mMediaBrowserClient.subscribeMediaInfo(mediaId, options, callback)
    }

    fun unsubscribeMediaData(mediaId: String, callback: SubscriptionCallback) {
        mMediaBrowserClient.unsubscribeMediaInfo(mediaId, callback)
    }

    fun play() = realConnectService { mController?.transportControls?.play() }

    fun pause() = realConnectService { mController?.transportControls?.pause() }

    fun stop() = realConnectService { mController?.transportControls?.stop() }

    fun skipToNext() = realConnectService { mController?.transportControls?.skipToNext() }

    fun skipToPrevious() = realConnectService { mController?.transportControls?.skipToPrevious() }

    fun seekTo(pos: Long) = realConnectService { mController?.transportControls?.seekTo(pos) }

    fun playFromSearch(query: String, extras: Bundle? = null) =
        realConnectService { mController?.transportControls?.playFromSearch(query, extras) }

    fun playFromMediaId(mediaId: String, extras: Bundle? = null) =
        realConnectService { mController?.transportControls?.playFromMediaId(mediaId, extras) }

    private class MediaConnectStatusCallback(callback: (Boolean) -> Unit) :
        MediaBrowserClient.ConnectStatusCallback {
        override fun onChanged(status: Int, token: MediaSessionCompat.Token?) {

        }
    }

    private class MediaControllerCallback : MediaControllerCompat.Callback() {
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {

        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {

        }

        override fun onQueueChanged(queue: MutableList<MediaSessionCompat.QueueItem>?) {
        }

        override fun onSessionEvent(event: String?, extras: Bundle?) {

        }

        override fun onSessionDestroyed() {
            mMediaBrowserClient.disconnect()
        }
    }
}