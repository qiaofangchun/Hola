package com.hola.datastore

interface IDataSaver {
    fun <A> writeValue(key: String, value: A)

    fun <A> readValue(key: String, defaultValue: A): A

    fun deleteData(key: String)

    fun deleteAllData()
}