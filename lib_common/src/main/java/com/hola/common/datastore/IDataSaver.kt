package com.hola.common.datastore

interface IDataSaver {
    fun <A> writeValue(key: String, value: A)

    fun <A> readValue(key: String, defaultValue: A): A

    fun deleteAllData()

    fun deleteDataByKey(key: String)
}