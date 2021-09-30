package com.hola.common.datastore

import android.content.Context
import kotlin.reflect.KProperty

object DataStore {
    private lateinit var dataSaver: IDataSaver

    fun init(context: Context, dataSaver: IDataSaver = DefaultDataSaver(context)) {
        this.dataSaver = dataSaver
    }

    private fun <A> writeValue(key: String, value: A) = dataSaver.writeValue(key, value)

    private fun <A> readValue(key: String, defaultValue: A): A = dataSaver.readValue(key, defaultValue)

    fun setDataOperator(dataSaver: IDataSaver) {
        this.dataSaver = dataSaver
    }

    fun deleteAllData() = dataSaver.deleteAllData()

    fun deleteDataByKey(key: String) = dataSaver.deleteDataByKey(key)

    class Get<T>(private val key: String, private val defaultValue: T) {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): T =
            readValue(key, defaultValue)

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) =
            writeValue(key, value)
    }
}