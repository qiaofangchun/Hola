package com.hola.app.music

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.hola.common.utils.Logcat

class MusicService : Service() {
    companion object {
        private const val TAG = "MusicService"
    }

    override fun onCreate() {
        super.onCreate()
        Logcat.d(TAG, "----->onCreate()")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Logcat.d(TAG, "----->onStartCommand(), flags=$flags")
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        Logcat.d(TAG, "----->onBind()")
        return null
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Logcat.d(TAG, "----->onUnbind()")
        return true
    }

    override fun onDestroy() {
        Logcat.d(TAG, "----->onDestroy()")
        super.onDestroy()
    }
}