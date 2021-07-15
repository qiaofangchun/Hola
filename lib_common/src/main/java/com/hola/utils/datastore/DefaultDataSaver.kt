package com.hola.utils.datastore

import android.content.Context
import java.io.*

class DefaultDataSaver(context: Context) : IDataSaver {
    private val prefs by lazy { context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE) }

    override fun <A> readValue(key: String, defaultValue: A): A = with(prefs) {
        val res: Any? = when (defaultValue) {
            is Long -> getLong(key, defaultValue)
            is String -> getString(key, defaultValue)
            is Int -> getInt(key, defaultValue)
            is Boolean -> getBoolean(key, defaultValue)
            is Float -> getFloat(key, defaultValue)
            else -> deSerialization(getString(key, serialize(defaultValue)).toString())
        }
        return res as A
    }

    override fun <A> writeValue(key: String, value: A) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(key, value)
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            else -> putString(key, serialize(value))
        }.apply()
    }

    override fun deleteAllData() = prefs.edit().clear().apply()

    override fun deleteDataByKey(key: String) = prefs.edit().remove(key).apply()

    @Throws(IOException::class)
    private fun <A> serialize(obj: A): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(
            byteArrayOutputStream
        )
        objectOutputStream.writeObject(obj)
        var serStr = byteArrayOutputStream.toString("ISO-8859-1")
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8")
        objectOutputStream.close()
        byteArrayOutputStream.close()
        return serStr
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun <A> deSerialization(str: String): A {
        val redStr = java.net.URLDecoder.decode(str, "UTF-8")
        val byteArrayInputStream = ByteArrayInputStream(
            redStr.toByteArray(charset("ISO-8859-1"))
        )
        val objectInputStream = ObjectInputStream(
            byteArrayInputStream
        )
        val obj = objectInputStream.readObject() as A
        objectInputStream.close()
        byteArrayInputStream.close()
        return obj
    }
}