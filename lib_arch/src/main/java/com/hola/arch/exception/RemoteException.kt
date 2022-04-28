package com.hola.arch.exception

class RemoteException : BaseException {
    constructor(code: Int, cause: Throwable?) : super(code, cause)

    constructor(code: Int, msg: String?) : super(code, msg)
}