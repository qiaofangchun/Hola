package com.hola.common.manager

import android.annotation.SuppressLint
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import androidx.core.content.ContextCompat
import com.hola.common.utils.AppHelper


object WakeLockManager {
    private const val TAG = "WakeLockManager"
    private const val TIME_OUT = 5000L
    private val pm =
        ContextCompat.getSystemService(AppHelper.context, PowerManager::class.java)

    @SuppressLint("InvalidWakeLockTag")
    private val wl = pm?.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG)

    fun acquire(){
        acquire(TIME_OUT)
    }

    fun acquire(long: Long){
        wl?.acquire(long)
    }

}