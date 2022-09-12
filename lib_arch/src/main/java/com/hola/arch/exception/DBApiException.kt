package com.hola.arch.exception

class DBApiException(
    code: Int = -1,
    message: String? = null,
    cause: Throwable? = null
) : BaseException(code, message, cause)