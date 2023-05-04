package com.hola.media.core

import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.media.app.NotificationCompat.MediaStyle
import androidx.media.session.MediaButtonReceiver
import com.hola.app.music.R
import com.hola.common.utils.AppHelper

@SuppressLint("StaticFieldLeak")
object MediaNotificationHelper {
    private const val CHANNEL_ID = "Hola_Media_Channel"
    private const val REQUEST_CODE = 1
    private const val START_FOREGROUND_ID = -1

    private var mService: Service? = null
    private var mNotificationManager: NotificationManager? = null

    fun init(service: Service) {
        mService = service
        mNotificationManager =
            (service.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val name = "Dso Music 音乐通知"
                        val descriptionText = "用来显示音频控制器通知"
                        val importance = NotificationManager.IMPORTANCE_LOW
                        val channel = NotificationChannel(CHANNEL_ID, name, importance)
                        channel.description = descriptionText
                        createNotificationChannel(channel)
                    }
                }
    }

    fun uninit() {
        mService = null
        mNotificationManager = null
    }

    fun showNotification(mediaSession: MediaSessionCompat,fromLyric: Boolean) {
        val controller = mediaSession.controller
        val mediaMetadata = controller.metadata
        val description = mediaMetadata.description

        mService?.let {
            val notification = NotificationCompat.Builder(AppHelper.context, CHANNEL_ID).apply {
                setSmallIcon(R.drawable.ic_media_music)
                setLargeIcon(description.iconBitmap)
                setContentTitle(description.title)
                setContentText(description.subtitle)
                setContentIntent(getPendingIntentActivity())

                addAction(R.drawable.ic_round_skip_previous_24,
                    "Previous",
                    getPendingIntentPrevious()
                )
                addAction(getPlayIcon(), "play", getPendingIntentPlay())
                addAction(R.drawable.ic_round_skip_next_24, "next", getPendingIntentNext())
                addAction(R.drawable.ic_stop, "quit", getPendingIntentQuit())
                setStyle(MediaStyle()
                        .setMediaSession(mediaSession.sessionToken)
                        .setShowActionsInCompactView(0, 1, 2)
                )
                setOngoing(true)
                if (getCurrentLineLyricEntry()?.text != null && fromLyric && musicController.statusBarLyric) {
                    setTicker(getCurrentLineLyricEntry()?.text) // 魅族状态栏歌词的实现方法
                }
                // .setAutoCancel(true)
            }.build()

            /*notification.extras.putInt("ticker_icon", R.drawable.ic_music_launcher_foreground)
            notification.extras.putBoolean("ticker_icon_switch", false)
            notification.flags = notification.flags.or(FLAG_ALWAYS_SHOW_TICKER)
            // 是否只更新 Ticker
            if (fromLyric) {
                notification.flags = notification.flags.or(FLAG_ONLY_UPDATE_TICKER)
            }*/
            // 更新通知
            it.startForeground(START_FOREGROUND_ID, notification)
        }
    }

    private fun getPendingIntentActivity(vararg cls: Class<Activity>): PendingIntent {
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val intents = cls.map { Intent(AppHelper.context, it) }.toTypedArray()
        return PendingIntent.getActivities(AppHelper.context, REQUEST_CODE, intents, flags)
    }

    fun create(context: Context, mediaSession: MediaSessionCompat, channelId: String) {
        // Given a media session and its context (usually the component containing the session)
        // Create a NotificationCompat.Builder

        // Get the session's metadata
        val controller = mediaSession.controller
        val mediaMetadata = controller.metadata
        val description = mediaMetadata.description

        val builder = NotificationCompat.Builder(context, channelId).apply {
            // Add the metadata for the currently playing track
            setContentTitle(description.title)
            setContentText(description.subtitle)
            setSubText(description.description)
            setLargeIcon(description.iconBitmap)

            // Enable launching the player by clicking the notification
            setContentIntent(controller.sessionActivity)

            // Stop the service when the notification is swiped away
            setDeleteIntent(
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    context,
                    PlaybackStateCompat.ACTION_STOP
                )
            )

            // Make the transport controls visible on the lockscreen
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

            // Add an app icon and set its accent color
            // Be careful about the color
            setSmallIcon(android.R.drawable.sym_def_app_icon)
            color = Color.CYAN

            // Add a pause button
            addAction(
                NotificationCompat.Action(
                    android.R.drawable.ic_media_pause,
                    "暂停",
                    MediaButtonReceiver.buildMediaButtonPendingIntent(
                        context,
                        PlaybackStateCompat.ACTION_PLAY_PAUSE
                    )
                )
            )

            addAction(
                NotificationCompat.Action(
                    android.R.drawable.ic_media_next,
                    "暂停",
                    MediaButtonReceiver.buildMediaButtonPendingIntent(
                        context,
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                    )
                )
            )

            addAction(
                NotificationCompat.Action(
                    android.R.drawable.ic_media_previous,
                    "暂停",
                    MediaButtonReceiver.buildMediaButtonPendingIntent(
                        context,
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                    )
                )
            )

            // Take advantage of MediaStyle features
            setStyle(
                MediaStyle()
                    .setMediaSession(mediaSession.sessionToken)
                    .setShowActionsInCompactView(0)

                    // Add a cancel button
                    .setShowCancelButton(true)
                    .setCancelButtonIntent(
                        MediaButtonReceiver.buildMediaButtonPendingIntent(
                            context,
                            PlaybackStateCompat.ACTION_STOP
                        )
                    )
            )
        }

        // Display the notification and place the service in the foreground
        (context as Service).startForeground(999, builder.build())
    }

}