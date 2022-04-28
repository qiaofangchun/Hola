package com.hola.common.manager

import android.annotation.SuppressLint
import android.os.PowerManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.hola.common.utils.AppHelper


@SuppressLint("InvalidWakeLockTag")
object WakeLockManager {
    private const val TAG = "WakeLockManager"
    private const val TIME_OUT = 5000L

    private val mLock by lazy {
        ContextCompat.getSystemService(AppHelper.context, PowerManager::class.java)
            ?.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG)
    }

    fun acquire() {
        acquire(TIME_OUT)
    }

    fun acquire(timeout: Long) {
        if (mLock == null) {
            throw IllegalAccessException("The PowerManager or WakeLock is null")
        }
        mLock?.takeUnless { it.isHeld }?.acquire(timeout)
    }

    fun release() {
        release(0)
    }

    fun release(flags: Int) {
        if (mLock == null) {
            throw IllegalAccessException("The PowerManager or WakeLock is null")
        }
        mLock?.takeIf { it.isHeld }?.release(flags)
    }
}