package com.hola.media.core

import android.app.PendingIntent
import android.content.Context
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.MediaBrowserServiceCompat

object MediaSessionHelper {
    private const val DEFAULT_PLAY_POSITION = 0L
    private const val DEFAULT_PLAY_SPEED = 1f

    @Volatile
    private var mMediaSession: MediaSessionCompat? = null

    val mediaSession
        get() = mMediaSession
            ?: throw IllegalStateException("MediaSession should be initialized before get.")

    val sessionToken get() = mediaSession.sessionToken

    fun init(service: MediaBrowserServiceCompat, tag: String) {
        mMediaSession = MediaSessionCompat(service, tag).apply {
            isActive = true
            service.sessionToken = sessionToken
            setSessionActivity(getPendingIntentActivity(service, 123))
            // Enable callbacks from MediaButtons and TransportControls
            setFlags(MediaSessionCompat.FLAG_HANDLES_QUEUE_COMMANDS)
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
                    PlaybackStateCompat.ACTION_PLAY
                            or PlaybackStateCompat.ACTION_PAUSE
                            or PlaybackStateCompat.ACTION_PLAY_PAUSE
                            or PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                            or PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                            or PlaybackStateCompat.ACTION_SET_REPEAT_MODE
                            or PlaybackStateCompat.ACTION_SET_RATING
                            or PlaybackStateCompat.ACTION_FAST_FORWARD
                            or PlaybackStateCompat.ACTION_REWIND
                            or PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE
                )
                .build()
        )
    }

    private fun getPendingIntentActivity(context: Context, request: Int): PendingIntent? {
        return context.packageManager?.getLaunchIntentForPackage(context.packageName)?.let {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                PendingIntent.getActivity(context, request, it, PendingIntent.FLAG_IMMUTABLE)
            } else {
                PendingIntent.getActivity(context, request, it, PendingIntent.FLAG_ONE_SHOT);
            }
        }
    }
}