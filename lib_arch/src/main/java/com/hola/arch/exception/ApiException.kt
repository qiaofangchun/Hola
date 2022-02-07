package com.hola.arch.exception

abstract class ApiException(val code: Int, private val throwable: Throwable) {
    val message: String
        get() {
            return getMessage(throwable)
        }

    protected abstract fun getMessage(throwable: Throwable): String
}