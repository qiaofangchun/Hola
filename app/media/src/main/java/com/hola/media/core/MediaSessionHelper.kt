package com.hola.media.core

import android.app.PendingIntent
import android.content.Context
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat

object MediaSessionHelper {
    fun create(context: Context, tag: String) = MediaSessionCompat(context, tag).apply {
        val packageName = context.packageName
        val packageManager = context.packageManager
        val intent = packageManager.getLaunchIntentForPackage(packageName)?.let { sessionIntent ->
            PendingIntent.getActivity(context, 0, sessionIntent, 0)
        }
        setSessionActivity(intent)
        // Enable callbacks from MediaButtons and TransportControls
        setFlags(MediaSessionCompat.FLAG_HANDLES_QUEUE_COMMANDS)
    }

    fun release(mediaSession: MediaSessionCompat) {
        mediaSession.run {
            isActive = false
            release()
        }
    }

    private fun getPlaybackState(): PlaybackStateCompat {
        return PlaybackStateCompat.Builder()
            .setState(PlaybackStateCompat.STATE_NONE, 0, 1f)
            .setActions(
                PlaybackStateCompat.ACTION_PLAY
                        or PlaybackStateCompat.ACTION_PLAY_PAUSE
                        or PlaybackStateCompat.ACTION_PLAY_PAUSE
            ).build()
    }


}