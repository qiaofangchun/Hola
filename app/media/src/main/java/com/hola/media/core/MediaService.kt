package com.hola.media.core

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.RatingCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.MediaBrowserServiceCompat

open abstract class MediaService : MediaBrowserServiceCompat() {
    companion object {
        private const val MY_MEDIA_ROOT_ID = "media_root_id"
        private const val MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id"
    }

    private val mMediaType = getMediaType()
    private lateinit var mMediaSession: MediaSessionCompat

    protected abstract fun getMediaType(): Int
    protected abstract fun getTag(): String

    override fun onCreate() {
        super.onCreate()
        mMediaSession = MediaSessionHelper.create(this, getTag()).apply {
            isActive = true
            // Set the session's token so that client activities can communicate with it.
            setSessionToken(this.sessionToken)
            // SessionCallback() has methods that handle callbacks from a media controller
            setCallback(MediaSessionCallback())
        }
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopSelf()
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
        parentMediaId: String,
        result: Result<List<MediaBrowserCompat.MediaItem>>
    ) {

    }

    override fun onDestroy() {
        mMediaSession.release()
        super.onDestroy()
    }

    private inner class MediaSessionCallback : MediaSessionCompat.Callback() {
        override fun onAddQueueItem(description: MediaDescriptionCompat) {

        }

        override fun onRemoveQueueItem(description: MediaDescriptionCompat) {

        }

        override fun onSetShuffleMode(shuffleMode: Int) { //设置随机播放模式

        }

        override fun onSetRating(rating: RatingCompat) { //设置收藏

        }

        override fun onPlay() {

        }

        override fun onPause() {

        }

        override fun onStop() {

        }

        override fun onPrepare() {

        }

        override fun onSkipToNext() {

        }

        override fun onSkipToPrevious() {

        }

        override fun onSeekTo(pos: Long) {

        }

        override fun onPlayFromMediaId(mediaId: String, extras: Bundle) {

        }

        override fun onPlayFromUri(uri: Uri, extras: Bundle) {

        }

        override fun onCustomAction(action: String, extras: Bundle) {

        }
    }
}