package com.hola.common.manager

import android.annotation.SuppressLint
import android.content.Context
import android.os.PowerManager
import com.hola.common.utils.AppHelper


@SuppressLint("InvalidWakeLockTag")
object WakeLockManager {
    private const val TAG = "WakeLockManager"
    private const val TIME_OUT = 5000L

    private val mLock by lazy {
        val pm = AppHelper.context.getSystemService(Context.POWER_SERVICE) as PowerManager
        pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG)
    }

    fun acquire() {
        acquire(TIME_OUT)
    }

    fun acquire(timeout: Long) {
        mLock?.takeIf { !it.isHeld }?.acquire(timeout)
    }

    fun release() {
        release(0)
    }

    fun release(flags: Int) {
        mLock?.takeIf { it.isHeld }?.release(flags)
    }
}