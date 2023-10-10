package com.hola.media.core

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.RatingCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.view.KeyEvent
import androidx.media.MediaBrowserServiceCompat
import com.hola.app.music.R
import com.hola.common.utils.Logcat

object MediaSessionHelper {
    private const val DEFAULT_PLAY_POSITION = 13000L
    private const val DEFAULT_PLAY_SPEED = 1f

    @Volatile
    private var mMediaSession: MediaSessionCompat? = null

    val mediaSession
        get() = mMediaSession
            ?: throw IllegalStateException("MediaSession should be initialized before get.")

    fun init(service: MediaBrowserServiceCompat, tag: String) {
        mMediaSession = MediaSessionCompat(service, tag).apply {
            isActive = true
            service.sessionToken = sessionToken
            setSessionActivity(getPendingIntentActivity(service))
            // Enable callbacks from MediaButtons and TransportControls
            setFlags(MediaSessionCompat.FLAG_HANDLES_QUEUE_COMMANDS)
            // set callback
            setCallback(MediaSessionCallback(), Handler(Looper.getMainLooper()))
        }
        setPlaybackState()
    }

    fun uninit() {
        mMediaSession?.run {
            isActive = true
            release()
        }
    }

    fun setPlaybackState(
        state: Int = PlaybackStateCompat.STATE_NONE,
        position: Long = DEFAULT_PLAY_POSITION,
        speed: Float = DEFAULT_PLAY_SPEED
    ) {
        mediaSession.setPlaybackState(
            PlaybackStateCompat.Builder()
                .setState(state, position, speed)
                .setActions(
                    PlaybackStateCompat.ACTION_PLAY_PAUSE
                            or PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                            or PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                            or PlaybackStateCompat.ACTION_SET_RATING
                            or PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED
                )
                .build()
        )
    }

    private fun getPendingIntentActivity(context: Context): PendingIntent? {
        return context.packageManager?.getLaunchIntentForPackage(context.packageName)?.let {
            val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
            PendingIntent.getActivity(context, 0, it, flag)
        }
    }

    class MediaSessionCallback : MediaSessionCompat.Callback() {
        companion object{
            private const val TAG = "MediaSessionHelper"
        }

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
            setPlaybackState(PlaybackStateCompat.STATE_PLAYING)
            MediaNotificationHelper.showNotification(mediaSession, false)
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
            setPlaybackState(PlaybackStateCompat.STATE_PAUSED)
            MediaNotificationHelper.showNotification(mediaSession, false)
        }

        override fun onStop() {
            Logcat.d(TAG, "[method=onStop] onStop")
            setPlaybackState(PlaybackStateCompat.STATE_STOPPED)
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
            setPlaybackState(PlaybackStateCompat.STATE_SKIPPING_TO_NEXT)
            MediaNotificationHelper.showNotification(mediaSession, false)
        }

        override fun onSkipToPrevious() {
            Logcat.d(TAG, "[method=onSkipToPrevious] onSkipToPrevious")
            setPlaybackState(PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS)
            MediaNotificationHelper.showNotification(mediaSession, false)
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