package com.hola.arch.exception

class RemoteException(
    code: Int = 0,
    message: String? = null,
    cause: Throwable? = null
) : BaseException(code, message, cause)