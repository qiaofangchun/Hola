package com.hola.location

import android.os.Handler
import android.os.HandlerThread
import android.os.Process
import android.util.Log
import com.hola.common.utils.Logcat
import com.hola.common.utils.ThreadPoolUtils
import com.hola.location.annotation.ExecuteMode
import com.hola.location.annotation.LocationCode
import com.hola.location.annotation.LocationMode
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

object LocationHelper {
    private const val TAG = "LocationHelper"

    private const val EXE_STATUS_RUNNING = 0
    private const val EXE_STATUS_STOP = 1

    private const val LOC_TIME_OUT = 5000L
    private const val LOC_INTERVAL = 5000L
    private const val MSG_WHAT_LOCATION = 0

    private var isOnce = true
    private var exeMode = ExecuteMode.SEPARATE

    private val handler = Handler(ThreadPoolUtils.handlerThread.looper) { msg ->
        when (msg.what) {
            MSG_WHAT_LOCATION -> realStartLocation()
            else -> Logcat.w(TAG, "not supprot message what=${msg.what}")
        }
        return@Handler true
    }

    private var allClientKeys = ArrayList<String>()
    private var runStatus = ConcurrentHashMap<String, Int>()
    private val clients = ConcurrentHashMap<String, ILocationClient>()
    private val callback = CopyOnWriteArrayList<LocationListener>()

    private val listener = object : LocationListener {
        override fun onCallback(client: ILocationClient, loc: Location) {
            handleResult(getClientKey(client), loc)
        }
    }

    private fun getNextClient(exeClientKey: String): ILocationClient? {
        val index = allClientKeys.indexOf(exeClientKey)
        if (allClientKeys.lastIndex == index) {
            return null
        }
        return clients[allClientKeys[index + 1]]
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
        handler.sendEmptyMessage(MSG_WHAT_LOCATION)
    }

    private fun realStartLocation() {
        if (runStatus.contains(EXE_STATUS_RUNNING)) {
            Logcat.d(TAG, "location client running!")
            return
        }
        allClientKeys.takeIf { it.isNotEmpty() }?.forEach {
            if (exeMode == ExecuteMode.SEPARATE) {
                startLocation(it)
                return
            }
            startLocation(it)
        }
    }

    fun stopLocation() {
        runStatus.forEach {
            if (it.value == EXE_STATUS_RUNNING) {
                stopLocation(it.key)
            }
        }
    }

    private fun startLocation(key: String) {
        clients[key]?.let {
            it.startLocation()
            runStatus[key] = EXE_STATUS_RUNNING
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
        val permissions = emptyList<String>()
        clients.forEach {
            permissions.plus(it.value.getPermissions())
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
        Logcat.d(TAG, "$key,result=$loc")
        when (loc.errorCode) {
            LocationCode.FAILURE -> {
                if (ExecuteMode.SEPARATE == exeMode) {
                    getNextClient(key)?.also { it.startLocation() }.let { return }
                } else {
                    if (runStatus.contains(EXE_STATUS_RUNNING)) return
                    stopLocation()
                }
            }
            LocationCode.SUCCESS -> stopLocation()
            else -> {
                stopLocation()
                return
            }
        }
        clients[key]?.let { client ->
            callback.forEach {
                it.onCallback(client, loc)
            }
        }
        if (!isOnce) {
            handler.sendEmptyMessageDelayed(MSG_WHAT_LOCATION, LOC_INTERVAL)
        }
    }
}
