package com.hola.common.utils

import android.util.Log

object Logcat {
    private var appName = ""

    fun init(appName: String) {
        this.appName = appName
    }

    fun i(tag: String, log: String) {
        Log.i(formatTag(tag), formatLog(log))
    }

    fun i(tag: String, log: String, tr: Throwable) {
        Log.i(formatTag(tag), formatLog(log), tr)
    }

    fun d(tag: String, log: String) {
        Log.d(formatTag(tag), formatLog(log))
    }

    fun d(tag: String, log: String, tr: Throwable) {
        Log.d(formatTag(tag), formatLog(log), tr)
    }

    fun v(tag: String, log: String) {
        Log.v(formatTag(tag), formatLog(log))
    }

    fun v(tag: String, log: String, tr: Throwable) {
        Log.v(formatTag(tag), formatLog(log), tr)
    }

    fun w(tag: String, log: String) {
        Log.w(formatTag(tag), formatLog(log))
    }

    fun w(tag: String, log: String, tr: Throwable) {
        Log.w(formatTag(tag), formatLog(log), tr)
    }

    fun e(tag: String, log: String) {
        Log.e(formatTag(tag), formatLog(log))
    }

    fun e(tag: String, log: String, tr: Throwable) {
        Log.e(formatTag(tag), formatLog(log), tr)
    }

    private fun formatTag(tag: String): String {
        if (appName.isBlank()) {
            throw IllegalArgumentException("Please call init")
        }
        return "$appName:$tag"
    }

    private fun formatLog(log: String) = "Thread_Name:${Thread.currentThread().name}\n$log"
}