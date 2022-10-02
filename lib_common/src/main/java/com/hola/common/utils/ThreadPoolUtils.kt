package com.hola.common.utils

import android.os.HandlerThread
import android.os.Process

object ThreadPoolUtils {
    val handlerThread by lazy {
        HandlerThread("H_T", Process.THREAD_PRIORITY_BACKGROUND).also { it.start() }
    }
}