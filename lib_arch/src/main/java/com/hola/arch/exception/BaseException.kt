package com.hola.arch.exception

open class BaseException(
    val errorCode: Int = Int.MIN_VALUE,
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause)