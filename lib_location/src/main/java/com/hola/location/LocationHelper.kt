package com.hola.location

import android.os.Handler
import android.os.HandlerThread
import android.os.Process
import android.util.Log
import com.hola.location.annotation.ExecuteMode
import com.hola.location.annotation.LocationCode
import com.hola.location.annotation.LocationMode
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

object LocationHelper {
    private const val TAG = "LocationHelper"
    private const val T_NAME = "t_loc"

    private const val EXE_STATUS_RUNNING = 0
    private const val EXE_STATUS_STOP = 1

    private const val LOC_TIME_OUT = 5000L
    private const val LOC_INTERVAL = 5000L
    private const val MSG_WHAT_TIME_OUT = 1
    private const val MSG_WHAT_LOCATION = 0

    private var isOnce = true
    private var exeMode = ExecuteMode.SEPARATE
    private val ht = HandlerThread(T_NAME, Process.THREAD_PRIORITY_FOREGROUND).also {
        it.start()
    }
    private val handler = Handler(ht.looper) { msg ->
        when (msg.what) {
            MSG_WHAT_TIME_OUT -> {
                val location = Location(message = "Location time!")
                listener.onCallback(clients.entries.last().value, location)
                stopLocation()
            }
            MSG_WHAT_LOCATION -> startLocation()
            else -> Log.w(TAG, "not supprot message what=${msg.what}")
        }
        return@Handler true
    }

    private var allClientKeys = ArrayList<String>()
    private var runStatus = ConcurrentHashMap<String, Int>()
    private val clients = ConcurrentHashMap<String, ILocationClient>()
    private val callback = CopyOnWriteArrayList<LocationListener>()

    private val listener = object : LocationListener {
        override fun onCallback(client: ILocationClient, loc: Location) {
            val key = getClientKey(client)
            stopLocation(key)
            handleResult(key, loc)
        }
    }

    private fun getNextExeClientKey(exeClientKey: String): String? {
        val index = allClientKeys.indexOf(exeClientKey)
        if (allClientKeys.lastIndex == index) {
            return null
        }
        return allClientKeys[index + 1]
    }

    fun init(@ExecuteMode exeMode: Int, isOnce: Boolean, clients: Array<ILocationClient>) {
        this.exeMode = exeMode
        this.isOnce = isOnce
        clients.forEach { addClient(it) }
    }

    private fun addClient(client: ILocationClient) {
        val key = getClientKey(client)
        clients.takeUnless { it.contains(key) }?.let {
            configClient(client)
            allClientKeys += key
            clients[key] = client
            runStatus[key] = EXE_STATUS_STOP
        }
    }

    private fun removeClient(clz: Class<ILocationClient>) {
        val key = clz.name
        allClientKeys -= key
        runStatus.remove(key)
        clients.remove(key)
    }

    private fun getClientKey(client: ILocationClient) = client::class.java.name

    fun startLocation() {
        if (runStatus.contains(EXE_STATUS_RUNNING)) {
            Log.d(TAG, "location client running!")
            return
        }
        clients.takeIf { it.isNotEmpty() }?.forEach {
            if (exeMode == ExecuteMode.SEPARATE) {
                startLocation(it.key)
                return@forEach
            }
            startLocation(it.key)
        }
        handler.sendEmptyMessageDelayed(MSG_WHAT_TIME_OUT, LOC_INTERVAL)
    }

    private fun startLocation(key: String) {
        clients[key]?.let {
            it.startLocation()
            runStatus[key] = EXE_STATUS_RUNNING
        }
    }

    fun stopLocation() {
        if (!runStatus.contains(EXE_STATUS_RUNNING)) {
            return
        }
        runStatus.forEach {
            if (it.value == EXE_STATUS_RUNNING) {
                stopLocation(it.key)
            }
        }
    }

    private fun stopLocation(key: String) {
        clients[key]?.let {
            it.stopLocation()
            runStatus[key] = EXE_STATUS_STOP
        }
    }

    fun release() {
        clients.forEach {
            it.value.stopLocation()
            it.value.release()
        }
    }

    fun getPermissions(): Array<String> {
        var permissions = emptyArray<String>()
        clients.forEach {
            permissions += it.value.getPermissions()
        }
        return permissions.distinct().toTypedArray()
    }

    fun regLocationListener(listener: LocationListener) {
        callback += listener
    }

    fun unRegLocationListener(listener: LocationListener) {
        callback -= listener
    }

    private fun configClient(client: ILocationClient) {
        client.timeOut(LOC_TIME_OUT)
        client.needAddress(true)
        client.locationMode(LocationMode.MODE_NETWORK)
        client.setLocationListener(listener)
    }

    private fun handleResult(key: String, loc: Location) {
        when (loc.errorCode) {
            LocationCode.FAILURE -> {
                if (!needCallback(key)) return
            }
            LocationCode.SUCCESS -> {
                stopLocation()
                if (!isOnce) {
                    handler.sendEmptyMessageDelayed(MSG_WHAT_LOCATION, LOC_INTERVAL)
                }
            }
            else -> stopLocation()
        }
        clients[key]?.let { client ->
            callback.forEach {
                it.onCallback(client, loc)
            }
        }
    }

    private fun needCallback(key: String): Boolean {
        if (ExecuteMode.SEPARATE == exeMode) {
            getNextExeClientKey(key)?.let {
                startLocation(it)
                return false
            }
        } else {
            if (runStatus.contains(EXE_STATUS_RUNNING)) {
                return false
            }
            stopLocation()
        }
        return true
    }
}
