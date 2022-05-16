package com.hola.app.music

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MusicService : Service() {
    companion object {
        private const val TAG = "MusicService"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "----->onCreate()")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "----->onStartCommand(), flags=$flags")
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "----->onBind()")
        return null
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "----->onUnbind()")
        return true
    }

    override fun onDestroy() {
        Log.d(TAG, "----->onDestroy()")
        super.onDestroy()
    }
}