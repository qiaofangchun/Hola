package com.hola.media.core

import android.content.Intent
import android.net.Uri
import android.os.*
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.RatingCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.view.KeyEvent
import androidx.media.MediaBrowserServiceCompat
import com.hola.common.utils.Logcat

open abstract class MediaService : MediaBrowserServiceCompat() {
    companion object {
        private const val MY_MEDIA_ROOT_ID = "media_root_id"
        private const val MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id"
    }

    private val mMediaType: Int get() = getMediaType()
    private val mTag: String get() = getTag()

    private lateinit var mMediaSession: MediaSessionCompat
    private lateinit var mMediaController: MediaControllerCompat

    protected abstract fun getMediaType(): Int
    protected abstract fun getTag(): String

    override fun onCreate() {
        super.onCreate()
        Logcat.d(mTag, "[method=onCreate] onCreate")
        mMediaSession = MediaSessionHelper.create(this, getTag()).apply {
            isActive = true
            // Set the session's token so that client activities can communicate with it.
            setSessionToken(this.sessionToken)
            // SessionCallback() has methods that handle callbacks from a media controller
            setCallback(MediaSessionCallback(), Handler(Looper.getMainLooper()))
        }
        mMediaController = MediaControllerCompat(this, mMediaSession.sessionToken)
    }

    /**
     * @param packageName 客户端包名
     * @param uid 客户端uid
     * @param rootHints
     * @return 返回客户端应要浏览/播放的媒体列表的“根”ID。
     */
    override fun onGetRoot(packageName: String, uid: Int, rootHints: Bundle?): BrowserRoot? {
        return null
    }

    override fun onLoadChildren(
        parentMediaId: String, result: Result<List<MediaBrowserCompat.MediaItem>>
    ) {

    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        Logcat.d(mTag, "[method=onTaskRemoved] onTaskRemoved")
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }

    override fun onDestroy() {
        Logcat.d(mTag, "[method=onDestroy] onDestroy")
        mMediaSession.release()
        super.onDestroy()
    }

    inner class MediaSessionCallback : MediaSessionCompat.Callback() {
        override fun onRemoveQueueItem(description: MediaDescriptionCompat?) {
            Logcat.d(mTag, "[method=onRemoveQueueItem] des=${description}")
        }

        override fun onAddQueueItem(description: MediaDescriptionCompat?) {
            Logcat.d(mTag, "[method=onAddQueueItem] des=${description}")
        }

        override fun onPrepare() {
            Logcat.d(mTag, "[method=onPrepare] onPrepare")
        }

        override fun onPlayFromUri(uri: Uri?, extras: Bundle?) {
            Logcat.d(mTag, "[method=onPlayFromUri] uri=$uri")
        }

        override fun onPrepareFromMediaId(mediaId: String?, extras: Bundle?) {
            Logcat.d(mTag, "[method=onPrepareFromMediaId] mediaId=$mediaId")
        }

        override fun onPrepareFromSearch(query: String?, extras: Bundle?) {
            Logcat.d(mTag, "[method=onPrepareFromSearch] query=$query")
        }

        override fun onPlay() {
            Logcat.d(mTag, "[method=onPlay] onPlay")
        }

        override fun onPrepareFromUri(uri: Uri?, extras: Bundle?) {
            Logcat.d(mTag, "[method=onPrepareFromUri] query=$uri")
        }

        override fun onPlayFromMediaId(mediaId: String?, extras: Bundle?) {
            Logcat.d(mTag, "[method=onPlayFromMediaId] query=$mediaId")
        }

        override fun onPlayFromSearch(query: String?, extras: Bundle?) {
            Logcat.d(mTag, "[method=onPlayFromSearch] query=$query")
        }

        override fun onSeekTo(position: Long) {
            Logcat.d(mTag, "[method=onSeekTo] position=$position")
        }

        override fun onPause() {
            Logcat.d(mTag, "[method=onPause] onPause")
        }

        override fun onStop() {
            Logcat.d(mTag, "[method=onStop] onStop")
        }

        override fun onSkipToQueueItem(id: Long) {
            Logcat.d(mTag, "[method=onSkipToQueueItem] id=$id")
        }

        override fun onMediaButtonEvent(event: Intent?): Boolean {
            val keyEvent = event?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getParcelableExtra(Intent.EXTRA_KEY_EVENT, KeyEvent::class.java)
                } else {
                    it.getParcelableExtra<KeyEvent>(Intent.EXTRA_KEY_EVENT)
                }
            }
            Logcat.d(mTag, "[method=onMediaButtonEvent] event=${keyEvent}")
            return super.onMediaButtonEvent(event)
        }

        override fun onCustomAction(action: String?, extras: Bundle?) {
            Logcat.d(mTag, "[method=onCustomAction] action=$action")
        }

        override fun onCommand(command: String?, extras: Bundle?, cb: ResultReceiver?) {
            Logcat.d(mTag, "[method=onCommand] command=$command")
        }

        override fun onSkipToNext() {
            Logcat.d(mTag, "[method=onSkipToNext] onSkipToNext")
        }

        override fun onSkipToPrevious() {
            Logcat.d(mTag, "[method=onSkipToPrevious] onSkipToPrevious")
        }

        override fun onFastForward() {
            Logcat.d(mTag, "[method=onFastForward] onFastForward")
        }

        override fun onRewind() {
            Logcat.d(mTag, "[method=onRewind] onRewind")
        }

        override fun onSetRepeatMode(repeatMode: Int) {
            Logcat.d(mTag, "[method=onSetRepeatMode] repeatMode=$repeatMode")
        }

        override fun onSetShuffleMode(shuffleMode: Int) {
            Logcat.d(mTag, "[method=onSetShuffleMode] shuffleMode=$shuffleMode")
        }

        override fun onSetRating(rating: RatingCompat?) {
            Logcat.d(mTag, "[method=onSetRating] rating=$rating")
        }

        override fun onSetPlaybackSpeed(speed: Float) {
            Logcat.d(mTag, "[method=onSetPlaybackSpeed] speed=$speed")
        }
    }
}