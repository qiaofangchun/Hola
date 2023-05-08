package com.hola.media.core

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat.SubscriptionCallback
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.RatingCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.MediaBrowserServiceCompat
import com.hola.common.utils.AppHelper
import com.hola.common.utils.Logcat

object MediaControllerHelper {
    private const val TAG = "MediaBrowserHelper"
    private const val MAX_RETRY_NUM = 3
    private const val START_RETRY_NUM = 1

    private var client: MediaBrowserClient? = null
    private var mRetryNum = START_RETRY_NUM
    private var mController: MediaControllerCompat? = null

    private val mMediaBrowserClient
        get() = client
            ?: throw IllegalStateException("MediaControllerHelper should be initialized before get.")

    fun <T : MediaBrowserServiceCompat> init(
        context: Context,
        clazz: Class<T>,
        rootHints: Bundle? = null
    ) {
        client = MediaBrowserClient(context.applicationContext, clazz, rootHints)
    }

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

    fun subscribeMediaData(mediaId: String, callback: SubscriptionCallback) {
        Logcat.d(TAG, "[method=subscribeMediaData] isConnectedService=$isConnectedService")
        mMediaBrowserClient.subscribeMediaInfo(mediaId, null, callback)
    }

    fun subscribeMediaData(mediaId: String, options: Bundle, callback: SubscriptionCallback) {
        Logcat.d(TAG, "[method=subscribeMediaData] isConnectedService=$isConnectedService")
        mMediaBrowserClient.subscribeMediaInfo(mediaId, options, callback)
    }

    fun unsubscribeMediaData(mediaId: String, callback: SubscriptionCallback) {
        mMediaBrowserClient.unsubscribeMediaInfo(mediaId, callback)
    }

    fun prepare() = realConnectService { mController?.transportControls?.prepare() }

    fun prepareFromMediaId(mediaId: String, extras: Bundle? = null) =
        realConnectService { mController?.transportControls?.prepareFromMediaId(mediaId, extras) }

    fun prepareFromUri(url: String, extras: Bundle? = null) = realConnectService {
        mController?.transportControls?.prepareFromUri(
            Uri.parse(url), extras
        )
    }

    fun prepareFromSearch(query: String, extras: Bundle? = null) =
        realConnectService { mController?.transportControls?.prepareFromSearch(query, extras) }

    fun play() = realConnectService { mController?.transportControls?.play() }

    fun playFromMediaId(mediaId: String, extras: Bundle? = null) =
        realConnectService { mController?.transportControls?.playFromMediaId(mediaId, extras) }

    fun playFromUri(url: String, extras: Bundle? = null) =
        realConnectService { mController?.transportControls?.playFromUri(Uri.parse(url), extras) }

    fun playFromSearch(query: String, extras: Bundle? = null) =
        realConnectService { mController?.transportControls?.playFromSearch(query, extras) }

    fun pause() = realConnectService { mController?.transportControls?.pause() }

    fun stop() = realConnectService { mController?.transportControls?.stop() }

    fun skipToNext() = realConnectService { mController?.transportControls?.skipToNext() }

    fun skipToPrevious() = realConnectService { mController?.transportControls?.skipToPrevious() }

    fun seekTo(pos: Long) = realConnectService { mController?.transportControls?.seekTo(pos) }

    fun skipToQueueItem(id: Long) =
        realConnectService { mController?.transportControls?.skipToQueueItem(id) }

    fun sendCustomAction(action: String, args: Bundle) =
        realConnectService { mController?.transportControls?.sendCustomAction(action, args) }

    fun sendCustomAction(action: PlaybackStateCompat.CustomAction, args: Bundle) =
        realConnectService { mController?.transportControls?.sendCustomAction(action, args) }

    fun setRating(rating: RatingCompat, extras: Bundle? = null) = realConnectService {
        if (extras == null) {
            mController?.transportControls?.setRating(rating)
        } else {
            mController?.transportControls?.setRating(rating, extras)
        }
    }

    fun setPlaybackSpeed(speed: Float) =
        realConnectService { mController?.transportControls?.setPlaybackSpeed(speed) }

    fun setRepeatMode(@PlaybackStateCompat.RepeatMode repeatMode: Int) =
        realConnectService { mController?.transportControls?.setRepeatMode(repeatMode) }

    fun setShuffleMode(@PlaybackStateCompat.ShuffleMode shuffleMode: Int) =
        realConnectService { mController?.transportControls?.setShuffleMode(shuffleMode) }

    fun fastForward() = realConnectService { mController?.transportControls?.fastForward() }

    fun rewind() = realConnectService { mController?.transportControls?.rewind() }

    fun setCaptioningEnabled(enabled: Boolean) =
        realConnectService { mController?.transportControls?.setCaptioningEnabled(enabled) }

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