package com.hola.media.music

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class NotificationHelper(private val context: Context) {
    companion object {
        private const val TAG = "NotificationHelper"
        private const val CHANNEL_ID = "media_notify_channel_id"
        private const val CHANNEL_NAME = "media_controller"
    }

    private lateinit var mNotificationManager: NotificationManager

    init {
        mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.apply {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return@apply
            createNotificationChannel(
                NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_LOW
                ).apply {
                    setShowBadge(false)
                })
        }
    }
}