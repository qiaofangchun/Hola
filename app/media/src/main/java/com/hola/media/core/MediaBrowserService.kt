package com.hola.media.core

import MediaProvider
import android.content.Intent
import android.net.Uri
import android.os.*
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.RatingCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.view.KeyEvent
import androidx.media.MediaBrowserServiceCompat
import com.hola.common.utils.Logcat

class MediaBrowserService : MediaBrowserServiceCompat() {
    companion object {
        private const val TAG = "MediaBrowserService"
        private const val MEDIA_ROOT_ID = "media_root_id"
    }

    private lateinit var mMediaSession: MediaSessionCompat
    private lateinit var mMediaController: MediaControllerCompat

    override fun onCreate() {
        super.onCreate()
        Logcat.d(TAG, "[method=onCreate] onCreate")
        mMediaSession = MediaSessionHelper.create(this, TAG).apply {
            isActive = true
            // Set the session's token so that client activities can communicate with it.
            setSessionToken(this.sessionToken)
            // SessionCallback() has methods that handle callbacks from a media controller
            setCallback(MediaSessionCallback(), Handler(Looper.getMainLooper()))
        }
        mMediaController = MediaControllerCompat(this, mMediaSession.sessionToken)
    }

    /**
     * @param pkgName 客户端包名
     * @param uid 客户端uid
     * @param rootHints
     * @return 返回客户端应要浏览/播放的媒体列表的“根”ID。
     */
    override fun onGetRoot(pkgName: String, uid: Int, rootHints: Bundle?): BrowserRoot? {
        Logcat.d(TAG, "[method=onGetRoot] pkg:$pkgName, uid:$uid, rootHints:${rootHints.toString()}")
        if (!allowBrowsing(pkgName, uid)) {
            Logcat.d(TAG, "[method=onGetRoot] not allow browse!")
            return null
        }
        return BrowserRoot(MEDIA_ROOT_ID, null)
    }

    private fun allowBrowsing(clientPkgName: String, clientUid: Int): Boolean {
        return true
    }

    override fun onLoadChildren(
        parentMediaId: String,
        result: Result<List<MediaBrowserCompat.MediaItem>>
    ) {
        Logcat.d(TAG, "[method=onLoadChildren] parentMediaId:$parentMediaId, result:${result}")
        // Assume for example that the music catalog is already loaded/cached.
        val mediaItems = emptyList<MediaBrowserCompat.MediaItem>()

        // Check if this is the root menu:
        if (MEDIA_ROOT_ID == parentMediaId) {
            // Build the MediaItem objects for the top level,
            // and put them in the mediaItems list...
        } else {
            // Examine the passed parentMediaId to see which submenu we're at,
            // and put the children of that menu in the mediaItems list...
        }

        result.sendResult(MediaProvider.mediaData)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        Logcat.d(TAG, "[method=onTaskRemoved] onTaskRemoved")
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }

    override fun onDestroy() {
        Logcat.d(TAG, "[method=onDestroy] onDestroy")
        mMediaSession.release()
        super.onDestroy()
    }

    inner class MediaSessionCallback : MediaSessionCompat.Callback() {
        override fun onRemoveQueueItem(description: MediaDescriptionCompat?) {
            Logcat.d(TAG, "[method=onRemoveQueueItem] des=${description}")
        }

        override fun onAddQueueItem(description: MediaDescriptionCompat?) {
            Logcat.d(TAG, "[method=onAddQueueItem] des=${description}")
        }

        override fun onPrepare() {
            Logcat.d(TAG, "[method=onPrepare] onPrepare")
        }

        override fun onPlayFromUri(uri: Uri?, extras: Bundle?) {
            Logcat.d(TAG, "[method=onPlayFromUri] uri=$uri")
        }

        override fun onPrepareFromMediaId(mediaId: String?, extras: Bundle?) {
            Logcat.d(TAG, "[method=onPrepareFromMediaId] mediaId=$mediaId")
        }

        override fun onPrepareFromSearch(query: String?, extras: Bundle?) {
            Logcat.d(TAG, "[method=onPrepareFromSearch] query=$query")
        }

        override fun onPlay() {
            Logcat.d(TAG, "[method=onPlay] onPlay")
        }

        override fun onPrepareFromUri(uri: Uri?, extras: Bundle?) {
            Logcat.d(TAG, "[method=onPrepareFromUri] query=$uri")
        }

        override fun onPlayFromMediaId(mediaId: String?, extras: Bundle?) {
            Logcat.d(TAG, "[method=onPlayFromMediaId] query=$mediaId")
        }

        override fun onPlayFromSearch(query: String?, extras: Bundle?) {
            Logcat.d(TAG, "[method=onPlayFromSearch] query=$query")
        }

        override fun onSeekTo(position: Long) {
            Logcat.d(TAG, "[method=onSeekTo] position=$position")
        }

        override fun onPause() {
            Logcat.d(TAG, "[method=onPause] onPause")
        }

        override fun onStop() {
            Logcat.d(TAG, "[method=onStop] onStop")
        }

        override fun onSkipToQueueItem(id: Long) {
            Logcat.d(TAG, "[method=onSkipToQueueItem] id=$id")
        }

        override fun onMediaButtonEvent(event: Intent?): Boolean {
            val keyEvent = event?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getParcelableExtra(Intent.EXTRA_KEY_EVENT, KeyEvent::class.java)
                } else {
                    it.getParcelableExtra<KeyEvent>(Intent.EXTRA_KEY_EVENT)
                }
            }
            Logcat.d(TAG, "[method=onMediaButtonEvent] event=${keyEvent}")
            return super.onMediaButtonEvent(event)
        }

        override fun onCustomAction(action: String?, extras: Bundle?) {
            Logcat.d(TAG, "[method=onCustomAction] action=$action")
        }

        override fun onCommand(command: String?, extras: Bundle?, cb: ResultReceiver?) {
            Logcat.d(TAG, "[method=onCommand] command=$command")
        }

        override fun onSkipToNext() {
            Logcat.d(TAG, "[method=onSkipToNext] onSkipToNext")
        }

        override fun onSkipToPrevious() {
            Logcat.d(TAG, "[method=onSkipToPrevious] onSkipToPrevious")
        }

        override fun onFastForward() {
            Logcat.d(TAG, "[method=onFastForward] onFastForward")
        }

        override fun onRewind() {
            Logcat.d(TAG, "[method=onRewind] onRewind")
        }

        override fun onSetRepeatMode(repeatMode: Int) {
            Logcat.d(TAG, "[method=onSetRepeatMode] repeatMode=$repeatMode")
        }

        override fun onSetShuffleMode(shuffleMode: Int) {
            Logcat.d(TAG, "[method=onSetShuffleMode] shuffleMode=$shuffleMode")
        }

        override fun onSetRating(rating: RatingCompat?) {
            Logcat.d(TAG, "[method=onSetRating] rating=$rating")
        }

        override fun onSetPlaybackSpeed(speed: Float) {
            Logcat.d(TAG, "[method=onSetPlaybackSpeed] speed=$speed")
        }
    }
}