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
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.media.app.NotificationCompat.MediaStyle
import androidx.media.session.MediaButtonReceiver
import com.hola.app.music.MainActivity
import com.hola.app.music.R
import com.hola.common.utils.AppHelper
import kotlin.properties.Delegates

@SuppressLint("StaticFieldLeak")
object MediaNotificationHelper {
    private var mService: Service? = null
    private var mStartId by Delegates.notNull<Int>()
    private var mChannelId by Delegates.notNull<String>()
    private var mNotificationManager: NotificationManager? = null

    private val actions by lazy {
        mapOf(
            PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS to createAction(
                "上一曲",
                android.R.drawable.ic_media_previous,
                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
            ),
            PlaybackStateCompat.ACTION_PLAY to createAction(
                "播放",
                android.R.drawable.ic_media_play,
                PlaybackStateCompat.ACTION_PLAY_PAUSE
            ),
            PlaybackStateCompat.ACTION_PAUSE to createAction(
                "暂停",
                android.R.drawable.ic_media_pause,
                PlaybackStateCompat.ACTION_PLAY_PAUSE
            ),
            PlaybackStateCompat.ACTION_SKIP_TO_NEXT to createAction(
                "下一曲",
                android.R.drawable.ic_media_next,
                PlaybackStateCompat.ACTION_SKIP_TO_NEXT
            ),
            PlaybackStateCompat.ACTION_SET_REPEAT_MODE to createAction(
                "播放模式",
                android.R.drawable.ic_media_next,
                PlaybackStateCompat.ACTION_SET_REPEAT_MODE
            ),
            PlaybackStateCompat.ACTION_SET_RATING to createAction(
                "收藏",
                android.R.drawable.ic_media_next,
                PlaybackStateCompat.ACTION_SET_RATING
            ),
            PlaybackStateCompat.ACTION_REWIND to createAction(
                "快退",
                android.R.drawable.ic_media_next,
                PlaybackStateCompat.ACTION_REWIND
            ),
            PlaybackStateCompat.ACTION_FAST_FORWARD to createAction(
                "快进",
                android.R.drawable.ic_media_next,
                PlaybackStateCompat.ACTION_FAST_FORWARD
            ),
            PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE to createAction(
                "随机播放",
                android.R.drawable.ic_media_next,
                PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE
            ),
        )
    }

    private fun createAction(title: String, icon: Int, action: Long) = NotificationCompat.Action(
        icon, title, MediaButtonReceiver.buildMediaButtonPendingIntent(mService, action)
    )

    fun init(service: Service, startId: Int, channelId: String) {
        mStartId = startId
        mChannelId = channelId
        mService = service
        mNotificationManager =
            (service.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val name = "Hola Music 音乐通知"
                    val descriptionText = "用来显示音频控制器通知"
                    val importance = NotificationManager.IMPORTANCE_LOW
                    val channel = NotificationChannel(mChannelId, name, importance)
                    channel.description = descriptionText
                    createNotificationChannel(channel)
                }
            }
    }

    fun uninit() {
        mService = null
        mNotificationManager = null
    }

    fun showNotification(mediaSession: MediaSessionCompat, fromLyric: Boolean) {
        val controller = mediaSession.controller
        val mediaMetadata = controller.metadata
        val description = controller.metadata?.description

        mService?.let {
            val notification = NotificationCompat.Builder(AppHelper.context, mChannelId).apply {
                setSmallIcon(R.drawable.ic_media_music)
                setLargeIcon(description?.iconBitmap)
                setContentTitle(description?.title)
                setContentText(description?.subtitle)
                setContentIntent(controller.sessionActivity)
                addAction(actions[PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS])
                if (true) {
                    addAction(actions[PlaybackStateCompat.ACTION_PLAY])
                } else {
                    addAction(actions[PlaybackStateCompat.ACTION_PLAY_PAUSE])
                }
                addAction(actions[PlaybackStateCompat.ACTION_SKIP_TO_NEXT])
                addAction(actions[PlaybackStateCompat.ACTION_SET_REPEAT_MODE])
                addAction(actions[PlaybackStateCompat.ACTION_SET_RATING])
                addAction(actions[PlaybackStateCompat.ACTION_FAST_FORWARD])
                addAction(actions[PlaybackStateCompat.ACTION_REWIND])
                addAction(actions[PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE])
                setStyle(
                    MediaStyle()
                        .setMediaSession(mediaSession.sessionToken)
                        .setShowCancelButton(true)
                )
                setOngoing(true)
                setAutoCancel(true)
                setAllowSystemGeneratedContextualActions(true)
                /*setOngoing(true)
                setAutoCancel(false)
                setAllowSystemGeneratedContextualActions(true)*/
                /*if (getCurrentLineLyricEntry()?.text != null && fromLyric && musicController.statusBarLyric) {
                    setTicker(getCurrentLineLyricEntry()?.text) // 魅族状态栏歌词的实现方法
                }*/
            }.build()
            /*notification.extras.putInt("ticker_icon", R.drawable.ic_music_launcher_foreground)
            notification.extras.putBoolean("ticker_icon_switch", false)
            notification.flags = notification.flags.or(FLAG_ALWAYS_SHOW_TICKER)
            // 是否只更新 Ticker
            if (fromLyric) {
                notification.flags = notification.flags.or(FLAG_ONLY_UPDATE_TICKER)
            }*/
            // 更新通知
            it.startForeground(mStartId, notification)
        }
    }

    /*private fun getPendingIntentActivity(vararg cls: Class<*>): PendingIntent {
        val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        val intents = cls.map { Intent(AppHelper.context, it) }.toTypedArray()
        return PendingIntent.getActivities(AppHelper.context, REQUEST_CODE, intents, flags)
    }*/
}