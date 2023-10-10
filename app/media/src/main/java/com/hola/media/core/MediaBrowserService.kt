package com.hola.media.core

import MediaProvider
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import androidx.media.MediaBrowserServiceCompat
import com.hola.common.utils.Logcat

abstract class MediaBrowserService : MediaBrowserServiceCompat() {
    companion object {
        const val TAG = "MediaBrowserService"
        private const val MEDIA_ROOT_ID = "media_root_id"
    }

    private lateinit var mMediaController: MediaControllerCompat

    abstract val channelId: String
    abstract val startForeground: Int

    override fun onCreate() {
        super.onCreate()
        Logcat.d(TAG, "[method=onCreate] onCreate")
        MediaSessionHelper.init(this, TAG)
        mMediaController = MediaControllerCompat(this, MediaSessionHelper.mediaSession.sessionToken)
        MediaNotificationHelper.init(this, startForeground, channelId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        MediaNotificationHelper.showNotification(MediaSessionHelper.mediaSession, false)
        return super.onBind(intent)
    }

    /**
     * @param pkgName 客户端包名
     * @param uid 客户端uid
     * @param rootHints
     * @return 返回客户端应要浏览/播放的媒体列表的“根”ID。
     */
    final override fun onGetRoot(pkgName: String, uid: Int, rootHints: Bundle?): BrowserRoot? {
        Logcat.d(
            TAG, "[method=onGetRoot] pkg:$pkgName, uid:$uid, rootHints:${rootHints.toString()}"
        )
        if (!allowBrowsing(pkgName, uid)) {
            Logcat.d(TAG, "[method=onGetRoot] not allow browse!")
            return null
        }
        return BrowserRoot(MEDIA_ROOT_ID, null)
    }

    private fun allowBrowsing(clientPkgName: String, clientUid: Int): Boolean {
        return true
    }

    final override fun onLoadChildren(
        parentMediaId: String, result: Result<List<MediaBrowserCompat.MediaItem>>
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
        MediaSessionHelper.uninit()
        MediaNotificationHelper.uninit()
        super.onDestroy()
    }
}