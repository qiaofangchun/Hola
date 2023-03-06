package com.hola.common.annotation

import android.annotation.SuppressLint
import android.app.NotificationManager
import androidx.annotation.IntDef

@SuppressLint("InlinedApi")
@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
@IntDef(
    Importance.UNSPECIFIED,
    Importance.NONE,
    Importance.MIN,
    Importance.LOW,
    Importance.DEFAULT,
    Importance.HIGH
)
annotation class Importance {
    companion object {
        const val UNSPECIFIED = NotificationManager.IMPORTANCE_UNSPECIFIED
        const val NONE = NotificationManager.IMPORTANCE_NONE
        const val MIN = NotificationManager.IMPORTANCE_MIN
        const val LOW = NotificationManager.IMPORTANCE_LOW
        const val DEFAULT = NotificationManager.IMPORTANCE_DEFAULT
        const val HIGH = NotificationManager.IMPORTANCE_HIGH
    }
}
