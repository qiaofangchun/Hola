package com.hola.location

import android.content.Context

abstract class BaseLocationClient(protected val context: Context) : ILocationClient {
    private val permissions = ArrayList<String>().also { addPermissions(it) }

    final override fun getPermissions() = permissions

    abstract fun addPermissions(permissions: List<String>)

}