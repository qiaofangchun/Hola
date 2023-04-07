package com.hola.media.core

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat

object MediaSessionHelper {
    fun create(context: Context, tag: String) = MediaSessionCompat(context, tag).apply {
        val packageName = context.packageName
        val packageManager = context.packageManager
        val intent = packageManager.getLaunchIntentForPackage(packageName)?.let { sessionIntent ->
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                PendingIntent.getActivity(context, 123, sessionIntent, PendingIntent.FLAG_IMMUTABLE);
            } else {
                PendingIntent.getActivity(context, 123, sessionIntent, PendingIntent.FLAG_ONE_SHOT);
            }
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