package com.hola.common.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.annotation.StringRes
import com.hola.common.annotation.Importance

object NotificationUtil {

    fun createNotificationChannel(
        context: Context,
        id: String?,
        @StringRes nameResId: Int,
        @StringRes descResId: Int,
        @Importance importance: Int
    ) {
        if (Build.VERSION.SDK_INT < VERSION_CODES.O) return
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).apply {
            val channel = NotificationChannel(id, context.getString(nameResId), importance)
            if (descResId != 0) {
                channel.description = context.getString(descResId)
            }
            createNotificationChannel(channel)
        }
    }

    fun setNotification(context: Context, id: Int, notification: Notification?) {
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).apply {
            if (notification != null) {
                notify(id, notification)
            } else {
                cancel(id)
            }
        }
    }
}