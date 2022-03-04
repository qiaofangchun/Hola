package com.hola.arch.exception

open class BaseException : Exception {
    private var errorCode = 0

    constructor() : this("")

    constructor(msg: String) : this(0, msg)

    constructor(code: Int, msg: String) : super(msg) {
        errorCode = code
    }
}